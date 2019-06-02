package com.imperio.jetty.source;


import com.imperio.jetty.bean.JsonEvent;
import com.imperio.jetty.commons.JsonSyslogUtils;
import com.imperio.jetty.commons.SyslogSourceConfigurationConstants;
import com.imperio.jetty.exception.FlumeBaseException;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FlumeNettySyslogTcp {

    private static final Logger LOG = LoggerFactory.getLogger(FlumeNettySyslogTcp.class);
    private int port;
    private String host = null;
    private Channel nettyChannel;
    private Integer eventSize;
    private Map<String, String> formaterProp;
    private Set<String> keepFields;
    private String clientIPHeader;
    private String clientHostnameHeader;

    public static void main(String[] args) {
        FlumeNettySyslogTcp syslogTcp = new FlumeNettySyslogTcp();
        syslogTcp.initProps();
        syslogTcp.start();
    }

    private void initProps() {
        port = 5240;
        host = "10.246.40.80";
        eventSize = JsonSyslogUtils.DEFAULT_SIZE;
        formaterProp = new HashMap<>();
        keepFields =  JsonSyslogUtils.chooseFieldsToKeep(SyslogSourceConfigurationConstants.DEFAULT_KEEP_FIELDS);
        clientIPHeader = "ClientIp";
        clientHostnameHeader = "ClientHostName";
    }

    private void stop() {
        LOG.info("Syslog TCP Source stopping...");
        if (nettyChannel != null) {
            nettyChannel.close();
            try {
                nettyChannel.getCloseFuture().await(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOG.warn("netty server stop interrupted", e);
            } finally {
                nettyChannel = null;
            }
        }
    }

    private void start() {
        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        ServerBootstrap serverBootstrap = new ServerBootstrap(factory);
        serverBootstrap.setPipelineFactory(new PipelineFactory(
                eventSize, formaterProp, keepFields, clientIPHeader, clientHostnameHeader
        ));
        LOG.info("Syslog TCP Source starting...");
        if (host == null) {
            nettyChannel = serverBootstrap.bind(new InetSocketAddress(port));
        } else {
            nettyChannel = serverBootstrap.bind(new InetSocketAddress(host, port));
        }
    }

    private class PipelineFactory implements ChannelPipelineFactory {
        private final Integer eventSize;
        private final Map<String, String> formaterProp;
        private final Set<String> keepFields;
        private String clientIPHeader;
        private String clientHostnameHeader;

        public PipelineFactory(Integer eventSize, Map<String, String> formaterProp,
                               Set<String> keepFields, String clientIPHeader, String clientHostnameHeader) {
            this.eventSize = eventSize;
            this.formaterProp = formaterProp;
            this.keepFields = keepFields;
            this.clientIPHeader = clientIPHeader;
            this.clientHostnameHeader = clientHostnameHeader;
        }

        @Override
        public ChannelPipeline getPipeline() {
            syslogTcpHandler handler = new syslogTcpHandler();
            handler.setEventSize(eventSize);
            handler.setFormater(formaterProp);
            handler.setKeepFields(keepFields);
            handler.setClientIPHeader(clientIPHeader);
            handler.setClientHostnameHeader(clientHostnameHeader);
            return Channels.pipeline(handler);
        }
    }

    public class syslogTcpHandler extends SimpleChannelHandler {
        private JsonSyslogUtils syslogUtils = new JsonSyslogUtils();
        private String clientIPHeader;
        private String clientHostnameHeader;

        public void setEventSize(int eventSize) {
            syslogUtils.setEventSize(eventSize);
        }

        public void setKeepFields(Set<String> keepFields) {
            syslogUtils.setKeepFields(keepFields);
        }

        public void setFormater(Map<String, String> prop) {
            syslogUtils.addFormats(prop);
        }

        public void setClientIPHeader(String clientIPHeader) {
            this.clientIPHeader = clientIPHeader;
        }

        public void setClientHostnameHeader(String clientHostnameHeader) {
            this.clientHostnameHeader = clientHostnameHeader;
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent mEvent) {
            ChannelBuffer buff = (ChannelBuffer) mEvent.getMessage();
            while (buff.readable()) {
                JsonEvent event = syslogUtils.extractEvent(buff);
                if (event == null) {
                    LOG.debug("Parsed partial event, event will be generated when rest of the event is received.");
                    continue;
                }
                if (clientIPHeader != null) {
                    event.getHeaders().put(clientIPHeader, JsonSyslogUtils.getIP(ctx.getChannel().getRemoteAddress()));
                }

                if (clientHostnameHeader != null) {
                    event.getHeaders().put(clientHostnameHeader, JsonSyslogUtils.getHostname(ctx.getChannel().getRemoteAddress()));
                }
                try {
                    LOG.info(String.format("Event body = %s, header = %s", new String(event.getBody()), event.getHeaders()));
                } catch (FlumeBaseException e) {
                    LOG.error("Can not get event", e);
                }
            }
        }
    }
}
