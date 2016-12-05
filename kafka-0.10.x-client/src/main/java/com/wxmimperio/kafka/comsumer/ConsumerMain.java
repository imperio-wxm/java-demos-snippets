package com.wxmimperio.kafka.comsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerMain {
    public static void main(String args[]) {
        List<String> topicList = new ArrayList<String>();
        topicList.add("test_1");
        KafkaNewConsumer producer = new KafkaNewConsumer(topicList);
        producer.execute();
    }
}
