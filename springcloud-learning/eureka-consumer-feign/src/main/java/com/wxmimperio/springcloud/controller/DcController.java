package com.wxmimperio.springcloud.controller;

import com.wxmimperio.springcloud.feign.client.DcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {

    private DcClient dcClient;

    @Autowired
    public DcController(DcClient dcClient) {
        this.dcClient = dcClient;
    }

    @GetMapping("/consumer")
    public String dc() {
        return dcClient.consumer();
    }
}
