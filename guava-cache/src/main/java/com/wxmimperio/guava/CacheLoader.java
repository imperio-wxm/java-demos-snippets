package com.wxmimperio.guava;


import com.google.common.cache.*;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by wxmimperio on 2017/9/12.
 */
public class CacheLoader {

    public static Object get(Object key) throws ExecutionException {
        Object var = cache.get(key, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("如果没有值,就执行其他方式去获取值");
                String var = "Google.com.sg";
                return var;
            }
        });
        return var;
    }

    public static void put(Object key, Object value) {
        cache.put(key, value);
    }

    private static RemovalListener<Object, Object> removalListener = new RemovalListener<Object, Object>() {
        public void onRemoval(RemovalNotification<Object, Object> notification) {
            System.out.println(notification.getKey() + "被移除");
            //可以在监听器中获取key,value,和删除原因
            notification.getValue();
            notification.getCause();//EXPLICIT、REPLACED、COLLECTED、EXPIRED、SIZE
        }
    };

    private static Cache<Object, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .expireAfterAccess(2, TimeUnit.SECONDS)
            .removalListener(removalListener)
            .build();

    public static void main(String[] args) throws Exception {
        System.out.println(CacheLoader.get("man"));
        List<String> buffer = Lists.newArrayList();
        buffer.add("abc");
        buffer.add("123");
        CacheLoader.put("mds_topic", buffer);

        System.out.println(CacheLoader.get("mds_topic"));

        Thread.sleep(1000 * 30);

        System.out.println(CacheLoader.get("mds_topic"));
    }
}
