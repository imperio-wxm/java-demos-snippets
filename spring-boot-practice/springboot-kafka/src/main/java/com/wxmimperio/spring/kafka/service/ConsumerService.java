package com.wxmimperio.spring.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ConsumerService {

    @KafkaListener(topics = "springboot_kafka")
    public void listen(ConsumerRecord<byte[], String> consumerRecord) {
        String value = consumerRecord.value();
        int partition = consumerRecord.partition();
        String topic = consumerRecord.topic();
        String key = new String(consumerRecord.key() == null ? "".getBytes() : consumerRecord.key(), StandardCharsets.UTF_8);
        System.out.println(String.format(
                "Topic = %s, partition = %s, key = %s, value = %s",
                topic, partition, key, value
        ));
    }
}
