package com.wxmimperio.process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by weiximing.imperio on 2017/8/29.
 */
public class TaskMain {

    public static void main(String[] args) {
        BlockingQueue<String> basket = new LinkedBlockingQueue<String>(50000);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        cachedThreadPool.submit(new TaskProduce(basket));
        cachedThreadPool.submit(new Task(basket));
    }
}
