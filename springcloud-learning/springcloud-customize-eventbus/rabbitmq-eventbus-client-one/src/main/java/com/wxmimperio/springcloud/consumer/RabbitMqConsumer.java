package com.wxmimperio.springcloud.consumer;

import com.wxmimperio.springcloud.beans.SchemaInfo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "wxm_queue")
public class RabbitMqConsumer {

    @RabbitHandler
    public void process(SchemaInfo object) {
        System.out.println(object.getClass().getName());
        System.out.println(object);
    }
}
