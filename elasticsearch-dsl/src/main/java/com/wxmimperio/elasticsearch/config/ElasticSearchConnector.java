package com.wxmimperio.elasticsearch.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className ElasticSearchConnector.java
 * @description This is the description of ElasticSearchConnector.java
 * @createTime 2020-09-09 21:37:00
 */
@Configuration
public class ElasticSearchConnector implements DisposableBean {

    private final static Logger LOG = LoggerFactory.getLogger(ElasticSearchConnector.class);
    private final ElasticSearchConfig config;
    private TransportClient client;

    @Autowired
    public ElasticSearchConnector(ElasticSearchConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void init() throws Exception {
        LOG.info("Connect elasticsearch cluster....Config = {}", config);
        Settings settings = Settings.builder()
                .put("client.transport.sniff", config.getTransportSniff())
                .put("cluster.name", config.getClusterName())
                .put("client.transport.ping_timeout", config.getPingTimeout())
                .put("client.transport.nodes_sampler_interval", config.getNodesSamplerInterval())
                .put("transport.tcp.connect_timeout", config.getTcpTimeout())
                .build();
        this.client = builtTransportClient(settings);
        LOG.info("Success connect to elasticsearch cluster!");
    }

    private PreBuiltTransportClient builtTransportClient(Settings settings) throws UnknownHostException {
        PreBuiltTransportClient transportClient = new PreBuiltTransportClient(settings);
        for (String hostUrl : config.getUrl()) {
            String[] host = hostUrl.split(":", -1);
            if (host.length == 2) {
                transportClient.addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(host[0]), Integer.parseInt(host[1]))
                );
            }
        }
        return transportClient;
    }

    @Override
    public void destroy() throws Exception {
        if (null != client) {
            client.close();
            LOG.info("Elasticsearch transport client closed!");
        } else {
            LOG.warn("Elasticsearch transport client is null, can not close.");
        }
    }

    public TransportClient getClient() {
        if (null == client) {
            throw new RuntimeException("Can not get elasticsearch client.");
        }
        return client;
    }
}
