package com.wxmimperio.kafka.comsumer;


/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerMain {
    public static void main(String args[]) {
        KafkaNewConsumer consumer = new KafkaNewConsumer("test_1");
        try {
            consumer.execute(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
