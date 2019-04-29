package com.wxmimperio.springcloud.listener;

import com.wxmimperio.springcloud.events.RabbitMqEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SchemaEventListener implements ApplicationListener<RabbitMqEvent> {

    @Override
    public void onApplicationEvent(RabbitMqEvent rabbitMqEvent) {
        System.out.println("receive = " + rabbitMqEvent.getSchemaInfo());
    }
}
