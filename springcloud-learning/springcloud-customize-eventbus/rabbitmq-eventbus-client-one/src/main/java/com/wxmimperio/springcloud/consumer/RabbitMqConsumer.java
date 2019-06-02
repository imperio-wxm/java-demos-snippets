package com.wxmimperio.springcloud.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
//@RabbitListener(queues = "wxm_queue")
public class RabbitMqConsumer {

    //@RabbitHandler
    public void process(Object object) {
        System.out.println(object);
    }
}
