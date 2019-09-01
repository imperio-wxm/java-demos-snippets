package com.wxmimperio.spring.kafka.controller;

import com.google.common.collect.Lists;
import com.wxmimperio.spring.kafka.bean.KafkaMessage;
import com.wxmimperio.spring.kafka.service.ConsumerService;
import com.wxmimperio.spring.kafka.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("kafka")
public class KafkaController {

    private ProducerService producerService;
    private ConsumerService consumerService;

    @Autowired
    public KafkaController(ProducerService producerService, ConsumerService consumerService) {
        this.producerService = producerService;
        this.consumerService = consumerService;
    }

    @PutMapping("send")
    public void sendMsg() {
        String topic = "springboot_kafka";
        List<String> data = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            data.add("wxmimperio_" + i);
        }
        producerService.sendMsg(new KafkaMessage(topic, data));
    }
}
