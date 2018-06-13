package com.wxmimperio.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClientClient {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientClient.class, args);
    }
}
