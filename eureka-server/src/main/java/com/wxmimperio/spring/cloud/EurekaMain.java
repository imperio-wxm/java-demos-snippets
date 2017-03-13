package com.wxmimperio.spring.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by weiximing.imperio on 2017/3/13.
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaMain {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaMain.class).web(true).run(args);
    }
}
