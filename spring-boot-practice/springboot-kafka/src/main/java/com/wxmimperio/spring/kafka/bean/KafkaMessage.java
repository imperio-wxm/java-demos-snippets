package com.wxmimperio.spring.kafka.bean;

import java.util.List;

public class KafkaMessage {
    private String topic;
    private List<String> data;

    public KafkaMessage() {
    }

    public KafkaMessage(String topic, List<String> data) {
        this.topic = topic;
        this.data = data;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "topic='" + topic + '\'' +
                ", data=" + data +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
