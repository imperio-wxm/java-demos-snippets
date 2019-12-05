package com.wxmimperio.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    private RestTemplate restTemplate;

    @Autowired
    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String get(String url) {
        return restTemplate.getForObject(url, String.class);
    }
}
