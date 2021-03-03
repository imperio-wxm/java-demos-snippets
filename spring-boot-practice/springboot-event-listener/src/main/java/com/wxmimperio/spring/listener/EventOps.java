package com.wxmimperio.spring.listener;

import com.wxmimperio.spring.bean.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className EventPublisher.java
 * @description This is the description of EventPublisher.java
 * @createTime 2021-03-03 14:47:00
 */
@Component
public class EventOps {
    private final ApplicationContext applicationContext;

    @Autowired
    public EventOps(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void publish(String msg) {
        applicationContext.publishEvent(new MyEvent(this, msg));
    }
}
