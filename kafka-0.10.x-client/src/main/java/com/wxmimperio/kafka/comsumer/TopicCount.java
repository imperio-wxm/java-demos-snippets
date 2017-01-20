package com.wxmimperio.kafka.comsumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2017/1/19.
 */
public class TopicCount {

    private Map<String, Long> topicCountMap = new ConcurrentHashMap<>();

    public synchronized void addCount(String topic, long countNum) {
        long temp = topicCountMap.get(topic);
        topicCountMap.put(topic, (temp + countNum));
    }

    public Map<String, Long> getTopicCountMap() {
        return topicCountMap;
    }
}
