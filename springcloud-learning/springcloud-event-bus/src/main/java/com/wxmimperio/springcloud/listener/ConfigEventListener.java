package com.wxmimperio.springcloud.listener;

import com.wxmimperio.springcloud.event.ConfigEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ConfigEventListener implements ApplicationListener<ConfigEvent> {

    @Override
    public void onApplicationEvent(ConfigEvent configEvent) {
        System.out.println("my message = " + configEvent.getMessage());
    }
}
