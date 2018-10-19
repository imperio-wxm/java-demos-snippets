package com.wxmimperio.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@EnableCaching // 开启缓存
public class DataJpaApplication {

    public static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(DataJpaApplication.class, args);
    }
}
