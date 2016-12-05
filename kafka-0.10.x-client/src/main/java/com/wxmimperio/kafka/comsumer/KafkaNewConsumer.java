package com.wxmimperio.kafka.comsumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class KafkaNewConsumer {

    private List<String> topicList;

    public KafkaNewConsumer(List<String> topicList) {
        this.topicList = topicList;
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

    public void execute() {
        //ThreadPool
        ExecutorService executor = Executors.newFixedThreadPool(20);

        //KafkaNewProducer Message
        for (int i = 0; i < topicList.size(); i++) {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(createProducerConfig());
            //Send Message
            executor.submit(new ConsumerHandle(consumer, topicList.get(i)));
        }
    }
}
