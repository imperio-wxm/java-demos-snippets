package com.wxmimperio.springcloud.listener;

import com.wxmimperio.springcloud.beans.SchemaInfo;
import com.wxmimperio.springcloud.events.RabbitMqEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SchemaEventListener implements ApplicationListener<RabbitMqEvent> {

    @Override
    public void onApplicationEvent(RabbitMqEvent rabbitMqEvent) {
        SchemaInfo schemaInfo = rabbitMqEvent.getSchemaInfo();
        System.out.println("receive = " + schemaInfo);
    }
}
