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
        System.out.println("run task! key = " + this.key);
    }
}
