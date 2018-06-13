package com.wxmimperio.springcloud;

import com.wxmimperio.springcloud.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class ApiGatwayApplication {

    /**
     * 启动该应用，一个默认的服务网关就构建完毕了
     * 由于Spring Cloud Zuul在整合了Eureka之后，具备默认的服务路由功能
     * 即：当我们这里构建的api-gateway应用启动并注册到eureka之后，服务网关会发现上面我们启动的两个服务eureka-client和eureka-consumer
     * 这时候Zuul就会创建两个路由规则。每个路由规则都包含两部分，一部分是外部请求的匹配规则，另一部分是路由的服务ID
     * http://api-gateway/client-name/路由
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatwayApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
}
