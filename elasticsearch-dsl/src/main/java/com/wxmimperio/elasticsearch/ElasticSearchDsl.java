package com.wxmimperio.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className ElasticSearchDsl.java
 * @description This is the description of ElasticSearchDsl.java
 * @createTime 2020-09-16 17:33:00
 */
@SpringBootApplication
public class ElasticSearchDsl {
    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(ElasticSearchDsl.class, args);
    }
}
