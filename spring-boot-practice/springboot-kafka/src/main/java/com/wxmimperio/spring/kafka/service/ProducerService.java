package com.wxmimperio.spring.kafka.service;

import com.wxmimperio.spring.kafka.bean.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    public ProducerService(KafkaTemplate<Integer, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMsg(final KafkaMessage kafkaMessage) {
        kafkaMessage.getData().forEach(data -> kafkaTemplate.send(kafkaMessage.getTopic(), data));
    }
}
