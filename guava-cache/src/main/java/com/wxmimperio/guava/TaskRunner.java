package com.wxmimperio.guava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wxmimperio on 2017/9/29.
 */
public class TaskRunner {

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    public void start(String key) {
        executorService.submit(new Task(key));
    }

    public void close() throws InterruptedException {
        System.out.println("=======wait shut down task...");
        executorService.shutdown();

        while (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("task runner 线程池没有关闭");
        }

        Thread.sleep(5 * 1000);

        System.out.println("=======Task runner close.");
    }
}
