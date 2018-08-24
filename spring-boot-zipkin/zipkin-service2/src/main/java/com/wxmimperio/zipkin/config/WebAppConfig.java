package com.wxmimperio.zipkin.config;

import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TracingClientHttpRequestInterceptor clientInterceptor;

    @Autowired
    private SpanCustomizingAsyncHandlerInterceptor serverInterceptor;

    /**
     * adds tracing to the application-defined rest template
     */
    @PostConstruct
    public void init() {
        List<ClientHttpRequestInterceptor> interceptors =
                new ArrayList<>(restTemplate.getInterceptors());
        interceptors.add(clientInterceptor);
        restTemplate.setInterceptors(interceptors);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * SpanCustomizingAsyncHandlerInterceptor 拦截器
         */
        InterceptorRegistration spanIr = registry.addInterceptor(serverInterceptor);
        // 配置拦截的路径
        spanIr.addPathPatterns("/**");
        // 配置不拦截的路径
        spanIr.excludePathPatterns("/**.html",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security"
        );

        super.addInterceptors(registry);
    }
}
