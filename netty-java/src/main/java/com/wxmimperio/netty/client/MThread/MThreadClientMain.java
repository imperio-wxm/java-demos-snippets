package com.wxmimperio.netty.client.MThread;

import com.wxmimperio.netty.client.base.EchoClient;
import com.wxmimperio.netty.pojo.TopicCount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weiximing.imperio on 2017/1/4.
 */
public class MThreadClientMain {

    public static void main(String args[]) {
        //线程池
        ExecutorService executor = Executors.newCachedThreadPool();


        for (int i = 0; i < 100000; i++) {
            TopicCount topicCount = new TopicCount("group_" + i, "topic_" + i, Long.valueOf(i), Long.valueOf(i * 100), System.currentTimeMillis());

            executor.submit(new EchoClient("127.0.0.1", 65535, topicCount));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

/*        for (int i = 0; i < 10; i++) {
            executor.submit(new EchoClient("127.0.0.1", 65535, "topic_" + i + "\tgroup" + i + "\t" + i));
        }*/
    }
}
