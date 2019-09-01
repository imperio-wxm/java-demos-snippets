package com.wxmimperio.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableKafka
@EnableSwagger2
public class KafkaApplication {

    //https://stackoverflow.com/questions/47912241/java-lang-nosuchmethoderror-on-kafka-consumer-with-spring-kafka-2-1-0-and-spring
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }
}
