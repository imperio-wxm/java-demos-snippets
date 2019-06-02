package com.wxmimperio.spring.service;

import com.wxmimperio.spring.bean.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserOpsService {

    @Cacheable(value = "userCache", key = "#userName")
    public User getUserByName(String userName) {
        System.out.println("缓存中没有，请添加");
        return new User();
    }

    @CachePut(value = "userCache", keyGenerator = "userCacheKeyGenerator")
    public List<User> putUserInCache(List<User> users) {
        System.out.println(String.format("User = %s， 加入到缓存", users));
        return users;
    }

    @CacheEvict(value = "userCache", key = "#userName")
    public void removeUserCacheByName(String userName) {
        System.out.println(String.format("userName = %s 的缓存被清除", userName));
    }
}
