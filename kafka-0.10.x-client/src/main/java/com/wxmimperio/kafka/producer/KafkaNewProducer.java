package com.wxmimperio.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class KafkaNewProducer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaNewProducer.class);

    private final Producer<String, String> producer;
    private final String topic;

    public KafkaNewProducer(String topic) {
        this.producer = new KafkaProducer<String, String>(createProducerConfig());
        this.topic = topic;
    }

    //Init conf
    private static Properties createProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.110:9092");
        props.put("acks", "all"); //ack方式，all，会等所有的commit最慢的方式
        props.put("retries", 0); //失败是否重试，设置会有可能产生重复数据
        props.put("batch.size", 16384); //对于每个partition的batch buffer大小
        props.put("linger.ms", 1);  //等多久，如果buffer没满，比如设为1，即消息发送会多1ms的延迟，如果buffer没满
        props.put("buffer.memory", 33554432); //整个producer可以用于buffer的内存大小
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

    public void execute(int numThreads) {

        //ThreadPool
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        //KafkaNewProducer Message
        int threadNumber = 0;
        for (int i = 0; i < numThreads; i++) {
            //Send Message
            executor.submit(new ProducerHandle(producer, threadNumber, topic));
            threadNumber++;
        }
    }
}
