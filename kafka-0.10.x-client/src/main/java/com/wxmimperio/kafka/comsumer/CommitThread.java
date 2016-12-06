package com.wxmimperio.kafka.comsumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by wxmimperio on 2016/12/6.
 */
public class CommitThread implements Runnable {

    private final KafkaConsumer<String, String> consumer;

    public CommitThread(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
        System.out.println("22222222222222222222");
    }

    @Override
    public void run() {

        while (true) {
            synchronized (consumer) {
                consumer.commitSync();
                System.out.println("commitSync");
            }
            System.out.println("1111111111111111");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
