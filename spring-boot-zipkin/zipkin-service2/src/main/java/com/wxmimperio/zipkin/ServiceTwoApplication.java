package com.wxmimperio.zipkin;


import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ServletComponentScan
@Import({
        TracingClientHttpRequestInterceptor.class,
        SpanCustomizingAsyncHandlerInterceptor.class
})
public class ServiceTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTwoApplication.class, args);
    }
}
