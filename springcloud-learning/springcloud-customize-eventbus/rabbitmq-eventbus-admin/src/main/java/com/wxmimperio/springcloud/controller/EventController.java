package com.wxmimperio.springcloud.controller;

import com.wxmimperio.springcloud.beans.SchemaInfo;
import com.wxmimperio.springcloud.events.RabbitMqEvent;
import com.wxmimperio.springcloud.producer.RabbitMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("event")
public class EventController {

    private ApplicationContext context;
    private RabbitMqProducer rabbitMqProducer;

    @Autowired
    public EventController(ApplicationContext context, RabbitMqProducer rabbitMqProducer) {
        this.context = context;
        this.rabbitMqProducer = rabbitMqProducer;
    }

    @PostMapping("schema/public")
    public void publishSchemaEvent(@RequestParam(value = "destination", required = false, defaultValue = "**") String destination,
                                   @RequestBody SchemaInfo schemaInfo) {
        final RabbitMqEvent rabbitMqEvent = new RabbitMqEvent(this, context.getId(), destination, schemaInfo);
        context.publishEvent(rabbitMqEvent);
        System.out.println(schemaInfo);

        //rabbitMqProducer.sendMsg(schemaInfo);
    }
}
