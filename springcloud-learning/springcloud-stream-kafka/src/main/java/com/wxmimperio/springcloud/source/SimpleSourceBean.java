package com.wxmimperio.springcloud.source;

import com.wxmimperio.springcloud.beans.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {
    private Source source;

    @Autowired
    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void publishChange(String action, String messageId) {
        source.output().send(MessageBuilder.withPayload(new Message(action, messageId)).build());
    }
}
