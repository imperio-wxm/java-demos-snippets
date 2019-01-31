package com.wxmimperio.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Value("${name}")
    private String name;

    @GetMapping("config")
    public String getConfigName() {
        String result = "name = " + name;
        System.out.println(result);
        return result;
    }
}
