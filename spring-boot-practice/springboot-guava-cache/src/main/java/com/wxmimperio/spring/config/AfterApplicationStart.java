package com.wxmimperio.spring.config;

import com.google.common.collect.Lists;
import com.wxmimperio.spring.bean.User;
import com.wxmimperio.spring.service.LoadCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wxmimperio.spring.config.GuavaCacheConfig.USER_CACHE_KEY;

@Component
@Order(value = 1)
public class AfterApplicationStart implements ApplicationRunner {

    private LoadCacheService loadCacheService;

    @Autowired
    public AfterApplicationStart(LoadCacheService loadCacheService) {
        this.loadCacheService = loadCacheService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        List<User> users = Lists.newArrayList();
        users.add(new User("init01", 1));
        users.add(new User("init02", 2));
        loadCacheService.cacheUsers(USER_CACHE_KEY, users);
        System.out.println("初始化...");
    }
}
