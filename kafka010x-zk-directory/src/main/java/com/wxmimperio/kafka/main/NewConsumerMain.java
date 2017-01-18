package com.wxmimperio.kafka.main;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Properties;

/**
 * Created by weiximing.imperio on 2017/1/18.
 */
public class NewConsumerMain {

    //Init conf
    private static Properties createProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.18.74:9092");
        props.put("group.id", "group_1");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    public static void main(String args[]) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(createProducerConfig());
        List<PartitionInfo> partitionInfos = consumer.partitionsFor("test_1");
        System.out.println(partitionInfos);
        consumer.close();
    }
}
