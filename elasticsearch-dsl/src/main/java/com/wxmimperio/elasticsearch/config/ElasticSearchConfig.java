package com.wxmimperio.elasticsearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className ElasticSearchConfig.java
 * @description This is the description of ElasticSearchConfig.java
 * @createTime 2020-09-10 18:45:00
 */

/**
 * cluster-name: elasticsearch
 * transport-sniff: false
 * ping-timeout: 10s
 * nodes-sampler-interval: 10s
 * tcp-timeout: 60s
 */
@Configuration
public class ElasticSearchConfig {
    @NotNull
    @NotEmpty
    @Value("#{'${elasticsearch.connector.url}'.split(',', -1)}")
    private List<String> url;
    @Value("${elasticsearch.connector.cluster-name}")
    private String clusterName;
    @Value("${elasticsearch.connector.ping-timeout}")
    private String pingTimeout;
    @Value("${elasticsearch.connector.tcp-timeout}")
    private String tcpTimeout;
    @Value("${elasticsearch.connector.transport-sniff}")
    private Boolean transportSniff;
    @Value("${elasticsearch.connector.nodes-sampler-interval}")
    private String nodesSamplerInterval;
    @Value("${elasticsearch.connector.indexName}")
    private String indexName;

    public ElasticSearchConfig() {
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(String pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public String getTcpTimeout() {
        return tcpTimeout;
    }

    public void setTcpTimeout(String tcpTimeout) {
        this.tcpTimeout = tcpTimeout;
    }

    public Boolean getTransportSniff() {
        return transportSniff;
    }

    public void setTransportSniff(Boolean transportSniff) {
        this.transportSniff = transportSniff;
    }

    public String getNodesSamplerInterval() {
        return nodesSamplerInterval;
    }

    public void setNodesSamplerInterval(String nodesSamplerInterval) {
        this.nodesSamplerInterval = nodesSamplerInterval;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
