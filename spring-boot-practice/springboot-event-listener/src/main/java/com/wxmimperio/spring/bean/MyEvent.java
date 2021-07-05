package com.wxmimperio.spring.bean;

import org.springframework.context.ApplicationEvent;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className MyEvent.java
 * @description This is the description of MyEvent.java
 * @createTime 2021-03-03 14:41:00
 */
public class MyEvent extends ApplicationEvent {
    private String msg;

    public MyEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
