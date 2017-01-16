package com.wxmimperio.kafka.comsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerHandle implements Runnable {

    private KafkaConsumer<String, String> consumer;
    private String topic;
    private static final int minBatchSize = 10;

    public ConsumerHandle(KafkaConsumer<String, String> consumer, String topic) {
        this.consumer = consumer;
        this.topic = topic;
        this.consumer.subscribe(Collections.singletonList(this.topic));
    }

    @Override
    public void run() {
        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Integer.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
                System.out.println("Thread=" + Thread.currentThread().getName() +
                        " value=" + record.value() + " partition=" + record.partition() +
                        " topic" + record.topic() + " offset" + record.offset() + " time=" + record.timestamp());
            }
            if (buffer.size() >= minBatchSize) {
                consumer.commitSync(); //批量完成写入后，手工sync offset
                buffer.clear();
            }
        }
    }
}
