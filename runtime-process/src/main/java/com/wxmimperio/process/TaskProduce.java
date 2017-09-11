package com.wxmimperio.process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by weiximing.imperio on 2017/8/29.
 */
public class TaskProduce implements Runnable {
    BlockingQueue<String> basket = new LinkedBlockingQueue<String>(50000);

    public TaskProduce(BlockingQueue<String> basket) {
        this.basket = basket;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            basket.offer("This is " + i);
            System.out.println("This is " + i + " offer");
        }
    }
}
