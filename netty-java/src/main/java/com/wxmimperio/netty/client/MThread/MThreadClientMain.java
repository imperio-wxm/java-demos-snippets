package com.wxmimperio.netty.client.MThread;

import com.wxmimperio.netty.client.base.EchoClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiximing.imperio on 2017/1/4.
 */
public class MThreadClientMain {

    public static void main(String args[]) {
        //线程池
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            executor.submit(new EchoClient("127.0.0.1", 65535));
        }
    }
}
