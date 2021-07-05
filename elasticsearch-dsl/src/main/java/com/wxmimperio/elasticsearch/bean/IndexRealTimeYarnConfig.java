package com.wxmimperio.elasticsearch.bean;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className RealTimeYarnConfig.java
 * @description This is the description of RealTimeYarnConfig.java
 * @createTime 2020-09-10 18:28:00
 */
public class IndexRealTimeYarnConfig extends IndexBase {
    private String appId;
    private String applicationId;
    private String containerId;
    private String fieldsFile;
    private String fieldsHostname;
    private String log;

    public IndexRealTimeYarnConfig() {
    }

    @JsonGetter("app_id")
    public String getAppId() {
        return appId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getContainerId() {
        return containerId;
    }

    @JsonGetter("fields.file")
    public String getFieldsFile() {
        return fieldsFile;
    }

    @JsonGetter("fields.hostname")
    public String getFieldsHostname() {
        return fieldsHostname;
    }

    public String getLog() {
        return log;
    }

    @JsonSetter("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    @JsonSetter("fields.file")
    public void setFieldsFile(String fieldsFile) {
        this.fieldsFile = fieldsFile;
    }

    @JsonSetter("fields.hostname")
    public void setFieldsHostname(String fieldsHostname) {
        this.fieldsHostname = fieldsHostname;
    }

    public void setLog(String log) {
        this.log = log;
    }


    @Override
    public String toString() {
        return "IndexRealTimeYarnConfig{" +
                "appId='" + appId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", containerId='" + containerId + '\'' +
                ", fieldsFile='" + fieldsFile + '\'' +
                ", fieldsHostname='" + fieldsHostname + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
