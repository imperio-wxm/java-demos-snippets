package com.wxmimperio.springcloud.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client") //注解来指定这个接口所要调用的服务名称
public interface DcClient {
    @GetMapping("/dc")
    String consumer();
}
