package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.listener.EventOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className ListenerController.java
 * @description This is the description of ListenerController.java
 * @createTime 2021-03-03 14:46:00
 */
@RestController
@RequestMapping("listener")
public class ListenerController {

    private final EventOps eventPublisher;

    @Autowired
    public ListenerController(EventOps eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PutMapping("eventPublisher/{msg}")
    public void eventPublisher(@PathVariable("msg") String msg) {
        eventPublisher.publish(msg);
    }
}
