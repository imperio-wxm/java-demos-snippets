package com.wxmimperio.elasticsearch.bean;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className IndexBase.java
 * @description This is the description of IndexBase.java
 * @createTime 2020-09-10 21:36:00
 */
public class IndexBase {
    private Timestamp timestamp;

    public IndexBase() {
    }

    @JsonGetter("@timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @JsonSetter("@timestamp")
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
