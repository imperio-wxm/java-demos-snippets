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
    private static RemovalListener<String, TaskRunner> async = RemovalListeners.asynchronous(
            new CacheRemoveListener(),
            executorService
    );

    // 同步 remove
  /*  private static RemovalListener<String, TaskRunner> removalListener = new RemovalListener<String, TaskRunner>() {
        @Override
        public void onRemoval(RemovalNotification<String, TaskRunner> notification) {
            TaskRunner value = notification.getValue();
            String key = notification.getKey();
            RemovalCause cause = notification.getCause();
            try {
                value.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("remove = " + key + " value = " + value + " Cause = " + cause.toString());
        }
    };*/

    private static Cache<String, TaskRunner> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(20, TimeUnit.SECONDS)
            .removalListener(async)
            .recordStats()
            .build();


    static class CleanWorkThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("clean some work.");

                cache.invalidateAll();
                System.out.println("Wait to remove cache.");
                //executorService.awaitTermination(5, TimeUnit.SECONDS);

                executorService.shutdown();
                while (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                    System.out.println("线程池没有关闭");
                }
                System.out.println("Finish remove cache.");

                //Thread.sleep(10 * 1000);//sleep 10s
                //Thread.sleep(5 * 1000);//sleep 10s
                System.out.println("Close project!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 异步 remove
    private static class CacheRemoveListener implements RemovalListener<String, TaskRunner> {
        @Override
        public void onRemoval(RemovalNotification<String, TaskRunner> notification) {
            TaskRunner value = notification.getValue();
            String key = notification.getKey();
            RemovalCause cause = notification.getCause();
            try {
                value.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                        TaskRunner taskRunner = new TaskRunner();

                        String key = String.valueOf(i);
                        String value = String.valueOf("value_" + i);
                        taskRunner.start(key);
                        cache.put(key, taskRunner);
                        System.out.println("put key = " + key + " value = " + value);
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        Thread.sleep(5 * 1000);
        System.exit(0);
    }
}
