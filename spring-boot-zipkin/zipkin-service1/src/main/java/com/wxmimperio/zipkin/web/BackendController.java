package com.wxmimperio.zipkin.web;

import brave.Tracing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;

import java.util.Date;

@Configuration
@EnableWebMvc
@RestController
public class BackendController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Tracing tracing;

    @GetMapping("/api/{username}")
    public String printDate(@PathVariable("username") String username) {
        System.out.println(username);

        brave.Span span = tracing.tracer().newChild(tracing.tracer().currentSpan().context());
        //brave.Span span = tracing.tracer().newTrace();
        //System.out.println(System.currentTimeMillis());
        span.name("test-span").start();
        span.tag("test-key", "test-value");
        span.tag("name", username);
        long parentId = span.context().parentId();
        long spanId = tracing.tracer().currentSpan().context().spanId();
        long traceId = tracing.tracer().currentSpan().context().traceId();
        long traceIdHigh = tracing.tracer().currentSpan().context().traceIdHigh();
        String traceIdString = tracing.tracer().currentSpan().context().traceIdString();
        span.tag("parentId", String.valueOf(parentId));
        span.tag("spanId", String.valueOf(spanId));
        span.tag("traceId", String.valueOf(traceId));
        span.tag("traceIdHigh", String.valueOf(traceIdHigh));
        span.tag("traceIdString", String.valueOf(traceIdString));
        span.tag("mvc.controller.class", "BackendController");
        span.tag("mvc.controller.method", "printlnTest");
        span.annotate("Method In");
        printlnTest();
        span.finish();

        brave.Span span2 = tracing.tracer().newChild(span.context());
        span2.name("new-span").start();
        span2.tag("new","new");
        span2.annotate("Method In");

        printlnTest();

        span2.finish();

        tracing.close();

        if (username != null) {
            return new Date().toString() + " " + username;
        }
        System.out.println();
        restTemplate.getForObject("http://localhost:8082/api", String.class);
        return new Date().toString();
    }

    private void printlnTest() {
        System.out.println("fasdfasdfasdf");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
