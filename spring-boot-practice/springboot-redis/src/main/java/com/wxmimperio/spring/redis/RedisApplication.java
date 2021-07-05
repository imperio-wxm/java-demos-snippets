package com.wxmimperio.spring.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className RedisApplication.java
 * @description This is the description of RedisApplication.java
 * @createTime 2021-02-05 17:49:00
 */
@SpringBootApplication
@EnableSwagger2
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class);
    }
}
