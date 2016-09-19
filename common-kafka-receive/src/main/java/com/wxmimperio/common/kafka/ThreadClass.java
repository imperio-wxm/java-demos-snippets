package com.wxmimperio.common.kafka;

/**
 * Created by weiximing.imperio on 2016/9/14.
 */

public class ThreadClass implements Runnable {
    private int i = 0;

    public void run() {

        while (i < 100) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
