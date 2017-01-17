package com.wxmimperio.kafka.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2016/11/15.
 */
public class NewBrokerInfo {
    //broker的全局id
    private String brokerName;
    //主机名或ip
    private String host;
    //端口
    private String port;
    //默认版本号
    private String version;
    //jmx端口号
    private String jmx_port;
    //初始启动时间戳
    private String createdTimestamp;
    //选举修改时间戳
    private String modifyTimestamp;
    //broker启动时间
    private String timestamp;
    //是否为leader
    private boolean isController;

    private List<String> endpoints;

    public NewBrokerInfo() {
        this.brokerName = "";
        this.host = "";
        this.port = "";
        this.version = "";
        this.jmx_port = "";
        this.createdTimestamp = "";
        this.modifyTimestamp = "";
        this.timestamp = "";
        this.isController = false;
        this.endpoints = new ArrayList<>();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJmx_port() {
        return jmx_port;
    }

    public void setJmx_port(String jmx_port) {
        this.jmx_port = jmx_port;
    }

    public boolean isController() {
        return isController;
    }

    public void setController(boolean controller) {
        isController = controller;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getModifyTimestamp() {
        return modifyTimestamp;
    }

    public void setModifyTimestamp(String modifyTimestamp) {
        this.modifyTimestamp = modifyTimestamp;
    }

    public List<String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public String toString() {
        return "[brokerId=" + this.brokerName +
                " host=" + this.host +
                " port=" + this.port +
                " version=" + this.version +
                " jmx_port=" + this.jmx_port +
                " createdTimestamp=" + this.createdTimestamp +
                " modifyTimestamp=" + this.modifyTimestamp +
                " timestamp=" + this.timestamp +
                " isController=" + this.isController +
                " endpoints=" + this.endpoints +
                "]";
    }
}
