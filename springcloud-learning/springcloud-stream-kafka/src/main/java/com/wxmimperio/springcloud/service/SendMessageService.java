package com.wxmimperio.springcloud.service;

import com.wxmimperio.springcloud.source.SimpleSourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {
    private SimpleSourceBean simpleSourceBean;

    @Autowired
    public SendMessageService(SimpleSourceBean simpleSourceBean) {
        this.simpleSourceBean = simpleSourceBean;
    }

    public void sendMessage(String action, String messageId) {
        simpleSourceBean.publishChange(action, messageId);
    }
}
