package com.wxmimperio.kafka.comsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerHandle implements Runnable {

    private final KafkaConsumer<String, String> consumer;
    private String topic;
    private static final int minBatchSize = 3000;

    private List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();

    private final ReentrantLock lock = new ReentrantLock();

    public ConsumerHandle(String topic) {
        this.consumer = new KafkaConsumer<String, String>(createProducerConfig());
        this.topic = topic;
        synchronized (this.consumer) {
            this.consumer.subscribe(Collections.singletonList(this.topic));
        }
    }

    //Init conf
    private static Properties createProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.110:9092");
        props.put("group.id", "group_1");
        props.put("enable.auto.commit", "false"); //关闭自动commit
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> records;
            synchronized (this.consumer) {
                records = consumer.poll(100);
            }
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);

               /* System.out.println(System.currentTimeMillis());

                System.out.println("Thread=" + Thread.currentThread().getName() +
                        " value=" + record.value() + " partition=" + record.partition() +
                        " topic" + record.topic() + " offset" + record.offset() + " time=" + record.timestamp());*/
            }
            synchronized (consumer) {
                if (buffer.size() >= minBatchSize) {
                    consumer.commitAsync(); //批量完成写入后，手工sync offset
                    buffer.clear();
                    System.out.println("commit!!!!!!");
                }
                /*try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }
    }

    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }

    public List<ConsumerRecord<String, String>> getBuffer() {
        return buffer;
    }
}
