package com.wxmimperio.spring.cloud;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by weiximing.imperio on 2017/3/13.
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableAutoConfiguration
public class EurekaMain {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaMain.class).web(true).run(args);
    }
}
