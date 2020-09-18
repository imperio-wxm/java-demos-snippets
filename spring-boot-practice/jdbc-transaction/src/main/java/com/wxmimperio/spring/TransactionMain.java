package com.wxmimperio.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TransactionMain {

    public static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(TransactionMain.class, args);
    }

}
