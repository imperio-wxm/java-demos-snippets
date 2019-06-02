package com.wxmimperio.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;

@SpringBootApplication
@RemoteApplicationEventScan(basePackages = "com.wxmimperio")
public class RabbitMqEventBusClientOne {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqEventBusClientOne.class);
    }
}
