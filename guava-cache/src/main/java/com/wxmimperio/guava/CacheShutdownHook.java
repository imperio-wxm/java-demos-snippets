package com.wxmimperio.guava;

import com.google.common.cache.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wxmimperio on 2017/9/28.
 */
public class CacheShutdownHook {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static RemovalListener<String, String> async = RemovalListeners.asynchronous(
            new CacheRemovelListener(),
            executorService
    );

    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .removalListener(async)
            .recordStats()
            .build();

    static class CleanWorkThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("clean some work.");

                Thread.sleep(10 * 1000);//sleep 10s

                cache.invalidateAll();
                executorService.awaitTermination(10, TimeUnit.SECONDS);
                System.out.println("Wait to remove cache.");

                executorService.shutdown();
                System.out.println("Finish remove cache.");

                //Thread.sleep(10 * 1000);//sleep 10s
                System.out.println("Close project!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class CacheRemovelListener implements RemovalListener<String, String> {
        @Override
        public void onRemoval(RemovalNotification<String, String> notification) {
            String value = notification.getValue();
            String key = notification.getKey();
            RemovalCause cause = notification.getCause();
            System.out.println("remove = " + key + " value = " + value + " Cause = " + cause.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        //将hook线程添加到运行时环境中去
        Runtime.getRuntime().addShutdownHook(new CleanWorkThread());

        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    //System.out.println("Thread Running " + i);
                    try {
                        String key = String.valueOf(i);
                        String value = String.valueOf("value_" + i);
                        cache.put(key, value);
                        System.out.println("put key = " + key + " value = " + value);
                        //Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        Thread.sleep(5000);
        System.exit(0);
    }
}
