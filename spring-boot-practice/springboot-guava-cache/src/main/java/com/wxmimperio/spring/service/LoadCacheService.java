package com.wxmimperio.spring.service;

import com.google.common.collect.Maps;
import com.wxmimperio.spring.bean.User;
import com.wxmimperio.spring.config.GuavaCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class LoadCacheService {

    private GuavaCacheConfig guavaCacheConfig;


    @Autowired
    public LoadCacheService(GuavaCacheConfig guavaCacheConfig) {
        this.guavaCacheConfig = guavaCacheConfig;
    }

    public List<User> cacheUsers(String cacheKey, List<User> users) {
        users.forEach(user -> get(cacheKey).put(user.getName(), user));
        return users;
    }

    public List<User> getUsersByAge(String cacheKey, int age) {
        return get(cacheKey).values().stream().filter(user -> user.getAge() == age).collect(Collectors.toList());
    }

    public User getUserByName(String cacheKey, String userName) {
        return get(cacheKey).get(userName);
    }

    private Map<String, User> get(String cacheKey) {
        try {
            return guavaCacheConfig.loadingCache().get(cacheKey, () -> {
                Map<String, User> userMap = Maps.newHashMap();
                if (StringUtils.isEmpty(cacheKey)) {
                    return userMap;
                } else {
                    userMap.put(cacheKey, new User("newWxm", 25));
                    return userMap;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Maps.newHashMap();
    }
}
