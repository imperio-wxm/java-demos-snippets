package com.wxmimperio.kafka.flume;

import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2016/12/29.
 */
public class FlumeRpcClient {
    private static final Logger LOG = LoggerFactory.getLogger(FlumeRpcClient.class);

    private RpcClient client;
    private String[] flumeURLs;

    public FlumeRpcClient() {
    }

    private String getFailoverString() {
        StringBuilder failoverString = new StringBuilder();

        for (int i = 1; i <= this.flumeURLs.length; i++) {
            failoverString.append("h" + i).append(" ");
        }
        return failoverString.toString().substring(0, failoverString.toString().length() - 1);
    }

    private Map<String, String> getHostsMap() {
        Map<String, String> hostsMap = new ConcurrentHashMap<>();
        String[] failoverList = getFailoverString().split(" ");

        for (int i = 0, j = 0; i < failoverList.length && j < this.flumeURLs.length; i++, j++) {
            hostsMap.put(("hosts." + failoverList[i]), this.flumeURLs[j]);
        }
        return hostsMap;
    }

    private Properties initProps() {
        Properties props = new Properties();
        //props.put("client.type", "default_failover");
        props.put("hosts", getFailoverString());

        for (String key : getHostsMap().keySet()) {
            props.put(key, getHostsMap().get(key));
        }

        //props.put("client.type", "default_loadbalance");
        //props.put("host-selector", "random"); // For random host selection
        //props.put("host-selector", "round_robin"); // For round-robin host
        //props.put("backoff", "true"); // Disabled by default.
        //props.put("maxBackoff", "10000"); // Defaults 0, which effectively
        return props;
    }

    public void initClient(String flumeURLString) {
        this.flumeURLs = flumeURLString.split(",");
        Properties props = initProps();
        LOG.info(props.toString());
        this.client = RpcClientFactory.getInstance(props);
    }

    public void sendDataToFlume(String data, Map<String, String> headers) {
        Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"), headers);
        try {
            this.client.append(event);
        } catch (EventDeliveryException e) {
            this.client.close();
            this.client = null;
            Properties props = initProps();
            this.client = RpcClientFactory.getInstance(props);
            LOG.error(e.getMessage());

        }
    }

    /**
     * send by batch
     *
     * @param event
     */
    public void sendDataToFlume(List<Event> event) {
        try {
            this.client.appendBatch(event);
        } catch (EventDeliveryException e) {
            this.client.close();
            this.client = null;
            Properties props = initProps();
            this.client = RpcClientFactory.getInstance(props);
            LOG.error(e.getMessage());
        }
    }

    public void cleanUp() {
        this.client.close();
    }
}
