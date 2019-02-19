package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.beans.TimeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class StringController {
    private static final Logger logger = LoggerFactory.getLogger(StringController.class);


    @GetMapping("/get")
    public String getName() throws Exception {
        logger.info("HDFS file size = " + UUID.randomUUID().hashCode());
        Thread.sleep(3000);
        return "wxmimperio";
    }

    @PostMapping("/time")
    public TimeBean postDateTime(@RequestBody TimeBean timeBean) throws Exception {
        return timeBean;
    }
}
