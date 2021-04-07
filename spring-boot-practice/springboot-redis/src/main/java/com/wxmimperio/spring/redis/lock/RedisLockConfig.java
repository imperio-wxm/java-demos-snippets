package com.wxmimperio.spring.redis.lock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className RedisLockConfig.java
 * @description This is the description of RedisLockConfig.java
 * @createTime 2021-04-07 15:01:00
 */
@Configuration
public class RedisLockConfig {
    private static final Integer EXPIRE_TIME = 60 * 1000;

    /**
     * RedisLockRegistry相当于一个锁的管理仓库，所有的锁都可以从该仓库获取，所有锁的键名为：registryKey:LOCK_NAME，默认时间为60s
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        // 注意这里的时间单位是毫秒
        return new RedisLockRegistry(redisConnectionFactory, "redisLock", EXPIRE_TIME);
    }
}
