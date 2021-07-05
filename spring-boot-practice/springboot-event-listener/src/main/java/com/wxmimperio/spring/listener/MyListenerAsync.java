package com.wxmimperio.spring.listener;

import com.wxmimperio.spring.bean.MyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className MyListenerAsync.java
 * @description This is the description of MyListenerAsync.java
 * @createTime 2021-03-03 16:07:00
 */
@Component
public class MyListenerAsync {

    private final BlockingQueue<MyEvent> cacheQueue;

    public MyListenerAsync() {
        this.cacheQueue = new LinkedBlockingQueue<>(10);
    }

    @Async
    @EventListener
    public void listenerEvent(MyEvent myEvent) throws Exception {
        cacheQueue.offer(myEvent);
        System.out.println("收到消息 = " + myEvent.getMsg());

        while (true) {
            System.out.println("轮序");
            if (!cacheQueue.isEmpty()) {
                System.out.println(cacheQueue.take().getMsg());
            }
            Thread.sleep(1000);
        }
    }
}
