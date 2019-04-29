package com.wxmimperio.springcloud.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqProducer {

    private AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitMqProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMsg(Object msg) {
        amqpTemplate.convertSendAndReceive("wxm_queue", msg);
    }
}
