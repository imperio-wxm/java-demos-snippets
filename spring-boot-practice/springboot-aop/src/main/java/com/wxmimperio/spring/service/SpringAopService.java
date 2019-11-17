package com.wxmimperio.spring.service;

import org.springframework.stereotype.Service;

@Service
public class SpringAopService {

    public void aopTransfer(String arg) {
        System.out.println("aopTransfer...." + arg);
    }
}
