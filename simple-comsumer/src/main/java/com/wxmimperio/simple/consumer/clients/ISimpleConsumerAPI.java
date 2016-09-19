package com.wxmimperio.simple.consumer.clients;

import kafka.javaapi.consumer.SimpleConsumer;

import java.util.List;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2016/9/19.
 */
public interface ISimpleConsumerAPI {
    Map<String, Integer> getBrokers(List<String> brokerAddress);

    SimpleConsumer leaderSearcher(String broker, String clientId);

    SimpleConsumer leaderSearcher(String broker, int port, String clientId);

    long fetchOffset();

    long fetchOffset(String groupId);

    String getLeaderBrokerName();

    List<Integer> getPartitionList();

    List<Integer> getPartitionList(String topic, Map<String, Integer> brokers);
}
