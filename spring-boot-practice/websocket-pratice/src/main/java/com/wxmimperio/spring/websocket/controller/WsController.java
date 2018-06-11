package com.wxmimperio.spring.websocket.controller;

import com.wxmimperio.spring.websocket.bean.Message;
import com.wxmimperio.spring.websocket.service.ResponseService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    @MessageMapping("/welcome") // 当浏览器发请过来时映射地址
    @SendTo("/topic/getResponse") // 服务端会对订阅sentTo地址的浏览器发送消息
    public ResponseService say(Message message){
        System.out.println(message.getMsg());
        return new ResponseService("This is " + message.getMsg());
    }
}
