package com.wxmimperio.zipkin.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;

@Configuration
@EnableWebMvc
@RestController
public class BackendController {

    @GetMapping("/api")
    public String printDate(@RequestHeader(name = "user-name", required = false) String username) {
        if (username != null) {
            return new Date().toString() + " " + username;
        }
        return new Date().toString();
    }
}
