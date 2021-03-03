package com.wxmimperio.spring.listener;

import com.wxmimperio.spring.bean.MyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className MyListener.java
 * @description This is the description of MyListener.java
 * @createTime 2021-03-03 14:43:00
 */
@Component
public class MyListener implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        System.out.println("收到消息 = " + myEvent.getMsg());
    }
}
