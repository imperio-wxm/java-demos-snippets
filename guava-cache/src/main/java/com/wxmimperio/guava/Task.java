package com.wxmimperio.guava;

/**
 * Created by wxmimperio on 2017/9/29.
 */
public class Task implements Runnable {

    private String key;

    public Task(String key) {
        this.key = key;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("run task! key = " + this.key);
    }
}
