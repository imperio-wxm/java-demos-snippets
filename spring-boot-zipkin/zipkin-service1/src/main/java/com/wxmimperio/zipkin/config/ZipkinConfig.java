package com.wxmimperio.zipkin.config;

import brave.Tracing;
import brave.context.log4j2.ThreadContextCurrentTraceContext;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;


@Configuration
public class ZipkinConfig extends WebMvcConfigurerAdapter {

    /**
     * Configuration for how to send spans to Zipkin
     */
    @Bean
    Sender sender(@Value("zipkin-service1") String serverName) {
        return OkHttpSender.create(serverName);
    }

    /**
     * Configuration for how to buffer spans into messages for Zipkin
     */
    @Bean
    AsyncReporter<Span> spanReporter(@Value("zipkin-service1") String serverName) {
        return AsyncReporter.create(sender(serverName));
    }

    /**
     * Controls aspects of tracing such as the name that shows up in the UI
     */
    @Bean
    Tracing tracing(@Value("zipkin-service1") String serviceName, @Value("http://127.0.0.1:9411/api/v2/spans") String serverName) {
        return Tracing.newBuilder()
                .localServiceName(serviceName)
                .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, "user-name"))
                .currentTraceContext(ThreadContextCurrentTraceContext.create()) // puts trace IDs into logs
                .spanReporter(spanReporter(serverName)).build();
    }

    // decides how to name and tag spans. By default they are named the same as the http method.
    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }
}
