package com.wxmimperio.spring.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className RedisController.java
 * @description This is the description of RedisController.java
 * @createTime 2021-02-05 17:59:00
 */
@RestController("redis")
public class RedisController {

    private final RedisTemplate redisTemplate;

    @Autowired
    public RedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PutMapping("redisLeftPush/{name}")
    public Long redisLeftPush(@PathVariable("name") String name) {
        return redisTemplate.opsForList().leftPush(name, name);
    }

    @GetMapping("redisRightPop/{keyName}")
    public String redisRightPop(@PathVariable("keyName") String keyName) {
        return (String) redisTemplate.opsForList().rightPop(keyName, 500, TimeUnit.MICROSECONDS);
    }

    @GetMapping("redisListSize/{keyName}")
    public Long redisListSize(@PathVariable("keyName") String keyName) {
        return redisTemplate.opsForList().size(keyName);
    }
}
