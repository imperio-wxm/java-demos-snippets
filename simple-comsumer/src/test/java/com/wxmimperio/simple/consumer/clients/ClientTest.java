package com.wxmimperio.simple.consumer.clients;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2016/9/19.
 */
public class ClientTest {

    @Test
    public void consumerTest() {
        /*List<String> brokerAddrs = new ArrayList<String>();
        brokerAddrs.add("192.168.18.35:9092");
        *//*brokerAddrs.add("10.1.8.206:9092");
        brokerAddrs.add("10.1.8.208:9092");*//*

        ISimpleConsumerAPI simpleConsumerAPI = new SimpleConsumerAPI("topic_001", 0, -1, "group_1", brokerAddrs);
        simpleConsumerAPI.open();
        simpleConsumerAPI.getFetchResponse(100);*/
    }
}
