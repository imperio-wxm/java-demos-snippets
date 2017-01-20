package com.wxmimperio.netty.pojo;

import java.io.Serializable;

/**
 * Created by weiximing.imperio on 2017/1/19.
 */
public class TopicCount implements Serializable {

    private static final long serialVersionUID = -4984721370227929766L;
    private String group;
    private String topic;
    private long countNum;
    private long lastOffset;
    private long timestamp;

    public TopicCount(String group, String topic, long countNum, long lastOffset, long timestamp) {
        this.group = group;
        this.topic = topic;
        this.countNum = countNum;
        this.lastOffset = lastOffset;
        this.timestamp = timestamp;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getCountNum() {
        return countNum;
    }

    public void setCountNum(long countNum) {
        this.countNum = countNum;
    }

    public long getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(long lastOffset) {
        this.lastOffset = lastOffset;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TopicCount{" +
                "group='" + group + '\'' +
                ", topic='" + topic + '\'' +
                ", countNum=" + countNum +
                ", lastOffset=" + lastOffset +
                ", timestamp=" + timestamp +
                '}';
    }
}
