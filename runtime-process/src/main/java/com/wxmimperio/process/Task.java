package com.wxmimperio.process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by weiximing.imperio on 2017/8/29.
 */
public class Task implements Runnable {
    BlockingQueue<String> basket = new LinkedBlockingQueue<String>(50000);

    public Task(BlockingQueue<String> basket) {
        this.basket = basket;
    }

    @Override
    public void run() {
        try {
            while (!basket.isEmpty()) {
                String msg = basket.take();
                Thread.sleep(1000);
                System.out.println(msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
