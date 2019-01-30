package com.wxmimperio.springcloud;

import feign.Client;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(Client.class)
@EnableFeignClients("com.wxmimperio.springcloud")
@EnableDiscoveryClient
@SpringBootApplication
public class FeignConsumerApplication {

    @Bean
    public Encoder feignEncoder() {
        return new SpringFormEncoder();
    }


    /**
     * 由于Feign是基于Ribbon实现的，所以它自带了客户端负载均衡功能，也可以通过Ribbon的IRule进行策略扩展
     * Feign还整合的Hystrix来实现服务的容错保护
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApplication.class, args);
    }
}
