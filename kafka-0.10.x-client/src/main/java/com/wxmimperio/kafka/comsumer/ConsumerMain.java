package com.wxmimperio.kafka.comsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerMain {
    public static void main(String args[]) {
        List<String> topicList = new ArrayList<>();
        topicList.add("test_001");
        topicList.add("test_2");
        KafkaNewConsumer consumer = new KafkaNewConsumer(topicList, "group_1");
        consumer.execute(3);

        KafkaNewConsumer consumer1 = new KafkaNewConsumer(topicList, "group_2");
        consumer1.execute(3);

    }
}
