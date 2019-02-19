package com.wxmimperio.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wxmimperio.spring.beans.DefaultTimeBean;
import com.wxmimperio.spring.beans.TimeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @PostMapping("/formatJava8Time")
    public TimeBean postDateTime(@RequestBody TimeBean timeBean) {
        System.out.println(timeBean.getLocalDate());
        System.out.println(timeBean.getLocalDateTime());
        System.out.println(timeBean.getLocalTime());
        return timeBean;
    }

    @GetMapping("/getFormatJava8Time")
    public TimeBean getFormatJava8Time() {
        return new TimeBean(LocalDate.now(), LocalDateTime.now(), LocalTime.now());
    }

    @GetMapping("/getDefaultJava8Time")
    public DefaultTimeBean getDefaultJava8Time() {
        return new DefaultTimeBean(LocalDate.now(), LocalDateTime.now(), LocalTime.now());
    }
}
