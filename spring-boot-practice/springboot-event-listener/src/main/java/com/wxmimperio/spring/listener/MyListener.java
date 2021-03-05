package com.wxmimperio.spring.listener;

import com.wxmimperio.spring.bean.MyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className MyListener.java
 * @description This is the description of MyListener.java
 * @createTime 2021-03-03 14:43:00
 */
//@Component
public class MyListener implements ApplicationListener<MyEvent> {

    private final BlockingQueue<MyEvent> cacheQueue;

    public MyListener() {
        this.cacheQueue = new LinkedBlockingQueue<>(10);
    }

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        cacheQueue.offer(myEvent);
        System.out.println("收到消息 = " + myEvent.getMsg());

        try {
            while (true) {
                System.out.println("轮序");
                if (!cacheQueue.isEmpty()) {
                    System.out.println(cacheQueue.take().getMsg());
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
