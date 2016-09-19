package com.wxmimperio.common.kafka;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiximing.imperio on 2016/9/14.
 */
public class ThreadManager {

    public void start(int numThreads) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 3; i++) {
            executor.submit(new ThreadClass());
        }
        //executor.submit(new ThreadClass());
    }
}
