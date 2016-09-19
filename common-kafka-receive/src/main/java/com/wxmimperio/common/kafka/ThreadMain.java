package com.wxmimperio.common.kafka;

/**
 * Created by weiximing.imperio on 2016/9/14.
 */
public class ThreadMain {
    public static void main(String args[]) {
        ThreadManager threadManager = new ThreadManager();
        threadManager.start(20);
    }
}
