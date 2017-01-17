package com.wxmimperio.kafka.comsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerMain {
    public static void main(String args[]) {
        KafkaNewConsumer producer = new KafkaNewConsumer("test_2");
        producer.execute(10);
    }
}
