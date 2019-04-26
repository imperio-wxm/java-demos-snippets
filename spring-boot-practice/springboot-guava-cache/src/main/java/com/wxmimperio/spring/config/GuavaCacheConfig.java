package com.wxmimperio.spring.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.wxmimperio.spring.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@Configuration
public class GuavaCacheConfig {

    public static final String USER_CACHE_KEY = "userCacheKey";

    @Bean
    public LoadingCache<String, Map<String, User>> loadingCache() {
        return CacheBuilder.newBuilder().maximumSize(1000).build(new CacheLoader<String, Map<String, User>>() {
            @Override
            @ParametersAreNonnullByDefault
            public Map<String, User> load(String key) throws Exception {
                Map<String, User> userMap = Maps.newHashMap();
                if (StringUtils.isEmpty(key)) {
                    return userMap;
                }
                userMap.put(key, new User("newWxm", 25));
                return userMap;
            }
        });
    }
}
