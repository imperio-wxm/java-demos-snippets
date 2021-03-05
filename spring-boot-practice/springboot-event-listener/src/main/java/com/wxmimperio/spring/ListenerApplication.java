package com.wxmimperio.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className ListenerApplication.java
 * @description This is the description of ListenerApplication.java
 * @createTime 2021-03-03 14:39:00
 */
@SpringBootApplication
@EnableSwagger2
@EnableCaching
@EnableAsync
public class ListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListenerApplication.class);
    }
}
