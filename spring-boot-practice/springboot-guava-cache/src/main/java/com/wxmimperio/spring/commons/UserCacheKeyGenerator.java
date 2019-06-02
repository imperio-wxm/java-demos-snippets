package com.wxmimperio.spring.commons;

import com.wxmimperio.spring.bean.User;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object o, Method method, Object... objects) {
        StringBuilder key = new StringBuilder();
        System.out.println(o.getClass().getName());
        System.out.println(method.getName());
        Object object = objects[0];
        if (object instanceof ArrayList) {
            List<User> userList =  ((ArrayList<User>) object);
            ((ArrayList<User>) object).forEach(user -> {
                key.append(user.getName());
            });
        }
        System.out.println(objects[0].getClass().getName());
        System.out.println(key);
        return key.toString();
    }
}
