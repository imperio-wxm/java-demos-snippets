package com.wxmimperio.springcloud.feign.client;

import feign.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

@FeignClient("eureka-client") //注解来指定这个接口所要调用的服务名称
public interface DcClient {
    @GetMapping("/dc")
    String consumer();

    @GetMapping("read/{fileName}")
    byte[] readFile(@PathVariable("fileName") String fileName);
}
