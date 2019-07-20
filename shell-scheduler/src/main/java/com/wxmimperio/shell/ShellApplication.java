package com.wxmimperio.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShellApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShellApplication.class);
    }
}
