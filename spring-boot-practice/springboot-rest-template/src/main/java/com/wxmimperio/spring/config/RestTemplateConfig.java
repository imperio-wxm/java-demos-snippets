package com.wxmimperio.spring.config;

import com.wxmimperio.spring.interceptors.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    private LoggingInterceptor loggingInterceptor;

    @Autowired
    public RestTemplateConfig(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.customizers((RestTemplate restTemplate) -> {
            restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(restTemplate.getRequestFactory()));
            restTemplate.getInterceptors().add(loggingInterceptor);
        }).build();
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);
        return factory;
    }
}
