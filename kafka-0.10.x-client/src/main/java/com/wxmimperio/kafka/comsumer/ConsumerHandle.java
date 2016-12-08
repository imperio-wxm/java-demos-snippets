package com.wxmimperio.kafka.comsumer;

import com.wxmimperio.kafka.quartz.QuartzNewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerHandle implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerHandle.class);
    private final KafkaConsumer<String, String> consumer;
    private String topic;
    private static final int minBatchSize = 3000;

    private final List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();

    private final ReentrantLock lock = new ReentrantLock();

    public ConsumerHandle(String topic) {
        this.consumer = new KafkaConsumer<String, String>(createProducerConfig());
        this.topic = topic;
        this.consumer.subscribe(Collections.singletonList(this.topic));
    }

    //Init conf
    private static Properties createProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.110:9092");
        props.put("group.id", "group_1");
        props.put("enable.auto.commit", "false"); //关闭自动commit
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    synchronized public void addBuffer(ConsumerRecord<String, String> record) {
        this.buffer.add(record);
    }

    synchronized public void clearBufferOver() {
        if (this.buffer.size() >= 3000) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer.clear();
        notifyAll();
    }

    synchronized public void clearBufferLower() {
        if (this.buffer.size() < 3000) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer.clear();
        notifyAll();
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> records;

            synchronized (this.consumer) {
                records = consumer.poll(100);
            }

            System.out.println(Thread.currentThread().getState() + "consumer");

            //System.out.println(Thread.currentThread().getState() + "consumer");

            for (ConsumerRecord<String, String> record : records) {
                addBuffer(record);
                if (buffer.size() % minBatchSize == 0) {
                    synchronized (this.consumer) {
                        this.consumer.commitSync();
                    }
                    this.buffer.clear();
                    System.out.println("commit!!!!!!");
                }

                LOG.error("Thread=" + Thread.currentThread().getName() +
                        " value=" + record.value() + " partition=" + record.partition() +
                        " topic" + record.topic() + " offset" + record.offset() + " time=" + record.timestamp() + "from consumer");
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
