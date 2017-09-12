package com.wxmimperio.guava;


import com.google.common.cache.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by wxmimperio on 2017/9/12.
 */
public class CacheLoader {

    public Object get(Object key) throws ExecutionException {
        Object var = cache.get(key, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("如果没有值,就执行其他方式去获取值");
                return "";
            }
        });
        return var;
    }

    public void put(Object key, Object value) {
        cache.put(key, value);
    }

   /* private RemovalListener<Object, Object> removalListener = new RemovalListener<Object, Object>() {
        public void onRemoval(RemovalNotification<Object, Object> notification) {
            System.out.println(notification.getKey() + "被移除");
            //可以在监听器中获取key,value,和删除原因
            notification.getValue();
            System.out.println(notification.getCause());
        }
    };*/

    private class MyRemovalListener implements RemovalListener<Object, Object> {

        public void onRemoval(RemovalNotification<Object, Object> notification) {
            //System.out.println(notification.getKey() + "被移除");
            //可以在监听器中获取key,value,和删除原因
            notification.getValue();
            notification.getCause();
        }
    }

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    RemovalListener<Object, Object> async = RemovalListeners.asynchronous(new MyRemovalListener(), executorService);


    private Cache<Object, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(11, TimeUnit.SECONDS)
            .removalListener(async)
            .recordStats()
            .build();

    public static void main(String[] args) throws Exception {
        CacheLoader cacheLoader = new CacheLoader();
        //System.out.println(cacheLoader.get("man"));
        List<String> buffer = Lists.newArrayList();
        buffer.add("abc");
        buffer.add("123");
        cacheLoader.put("mds_topic", buffer);

        //System.out.println(cacheLoader.get("mds_topic"));

        for (int i = 0; i < 1000000; i++) {
            if (!cacheLoader.cache.asMap().containsKey("mds_topic" + i)) {
                cacheLoader.put("mds_topic" + i, buffer);
            }
        }

        long startTime = System.currentTimeMillis();

        ConcurrentMap<Object, Object> concurrentMap = cacheLoader.cache.asMap();
        for (Map.Entry object : concurrentMap.entrySet()) {
            object.getValue();
        }

        System.out.println(" cost = " + (System.currentTimeMillis() - startTime));

        /*for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);

            System.out.println("====================");

            if (cacheLoader.cache.asMap().containsKey("mds_topic" + i)) {
                cacheLoader.put("mds_topic" + i, buffer);
            }

            if (i == 10) {
                System.out.println("+++++++++" + cacheLoader.get("mds_topic"));
            }

            //System.out.println(cacheLoader.cache.asMap());
            ConcurrentMap<Object, Object> concurrentMap = cacheLoader.cache.asMap();
            for (Map.Entry object : concurrentMap.entrySet()) {
                //System.out.println(object.getValue());

                System.out.println(object.getKey());
                if (object.getValue() == null) {
                    System.out.println("this is null");
                }
                if (i == 9) {
                    if (object.getKey().toString().equalsIgnoreCase("mds_topic")) {
                        System.out.println("+++++++++" + cacheLoader.get(object.getKey()));
                    }
                }
            }
        }
        System.out.println(cacheLoader.cache.asMap());*/

        cacheLoader.executorService.shutdown();

        //System.out.println(cacheLoader.get("mds_topic"));
    }
}
