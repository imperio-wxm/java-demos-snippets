package com.wxmimperio.spring.redis.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

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
    private final RedisLockRegistry redisLockRegistry;
    private final RedissonClient redisson;

    @Autowired
    public RedisController(RedisTemplate redisTemplate, RedisLockRegistry redisLockRegistry, RedissonClient redisson) {
        this.redisTemplate = redisTemplate;
        this.redisLockRegistry = redisLockRegistry;
        this.redisson = redisson;
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

    @PutMapping("redisListRemoveAll/{keyName}")
    public void redisListRemoveAll(@PathVariable("keyName") String keyName) {
        redisTemplate.opsForList().trim(keyName, 1, 0);
        //redisTemplate.opsForList().remove(keyName, -1, null);
    }

    @GetMapping("lockTest/{key}")
    public void lockTest(@PathVariable("key") String key) {
        Lock lock = null;
        try {
            lock = redisLockRegistry.obtain(key);
            boolean isLock = lock.tryLock(500, TimeUnit.MILLISECONDS);
            System.out.println("进入线程 = " + Thread.currentThread().getName() + ", isLocked = " + isLock);
            if (isLock) {
                System.out.println("Get redis lock do something....");
                TimeUnit.SECONDS.sleep(10);
            } else {
                System.out.println("Can not get redis lock.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock != null) {
                try {
                    lock.unlock();
                } catch (Exception e) {
                }
            }
        }
    }

    @GetMapping("lockTestRedisson/{key}")
    public void lockTestRedisson(@PathVariable("key") String key) {
        RLock lock = null;
        try {
            lock = redisson.getLock(key);
            boolean isLock = lock.tryLock(60, 1, TimeUnit.SECONDS);
            System.out.println("进入线程 = " + Thread.currentThread().getName() + ", isLocked = " + isLock);
            if (isLock) {
                System.out.println("Get redis lock do something....");
                TimeUnit.SECONDS.sleep(5);
            } else {
                System.out.println("Can not get redis lock.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock != null) {
                try {
                    lock.unlock();
                    System.out.println("unlock...");
                } catch (Exception e) {
                }
            }
        }
    }
}
