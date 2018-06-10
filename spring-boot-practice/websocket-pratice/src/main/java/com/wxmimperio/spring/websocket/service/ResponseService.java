package com.wxmimperio.spring.websocket.service;

public class ResponseService {

    private String respMsg;

    public ResponseService(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespMsg() {
        return respMsg;
    }
}
