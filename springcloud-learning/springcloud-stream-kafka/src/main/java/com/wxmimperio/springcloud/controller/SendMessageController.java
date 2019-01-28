package com.wxmimperio.springcloud.controller;

import com.wxmimperio.springcloud.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SendMessageController {

    private SendMessageService sendMessageService;

    @Autowired
    public SendMessageController(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @GetMapping("send/{actionName}")
    public void sendMessage(@PathVariable String actionName) {
        sendMessageService.sendMessage(actionName, UUID.randomUUID().toString());
    }
}
