package com.wxmimperio.zipkin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;

@Configuration
@EnableWebMvc
@RestController
public class BackendController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/api/{username}")
    public String printDate(@PathVariable("username") String username) {
        System.out.println(username);
        if (username != null) {
            return new Date().toString() + " " + username;
        }
        restTemplate.getForObject("http://localhost:8082/api", String.class);
        return new Date().toString();
    }
}
