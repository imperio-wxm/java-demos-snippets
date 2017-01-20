package com.wxmimperio.kafka.pojo;

import java.io.Serializable;

/**
 * Created by weiximing.imperio on 2017/1/19.
 */
public class TopicCount implements Serializable {

    private static final long serialVersionUID = -4984721370227929767L;
    private String group;
    private String topic;
    private long countNum;
    private long lastOffset;
    private long timestamp;
    private int partition;

    public TopicCount(String group, String topic, int partition, long countNum, long lastOffset, long timestamp) {
        this.group = group;
        this.topic = topic;
        this.countNum = countNum;
        this.lastOffset = lastOffset;
        this.timestamp = timestamp;
        this.partition = partition;
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

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    @Override
    public String toString() {
        return "TopicCount{" +
                "group='" + group + '\'' +
                ", topic='" + topic + '\'' +
                ", countNum=" + countNum +
                ", lastOffset=" + lastOffset +
                ", timestamp=" + timestamp +
                ", partition=" + partition +
                '}';
    }
}
