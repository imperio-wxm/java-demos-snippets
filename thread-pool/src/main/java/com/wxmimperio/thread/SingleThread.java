package com.wxmimperio.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThread {

    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            final int index = i;

            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ", index=" + index);
                }
            });
        }
    }
}
