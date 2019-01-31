package com.wxmimperio.springcloud.controller;

import com.wxmimperio.springcloud.event.ConfigEvent;
import com.wxmimperio.springcloud.listener.ConfigEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class UpdateConfigController {

    @Value("${name}")
    private String name;
    private ApplicationContext context;

    @Autowired
    public UpdateConfigController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping("newName")
    public String getNewName() {
        String result = "new name = " + name;
        System.out.println(result);
        return result;
    }

    @PostMapping("/msg/{message}")
    public String publishMyMessage(@PathVariable String message) {
        final String myUniqueId = context.getId();
        ConfigEvent configEvent = new ConfigEvent(this, myUniqueId, "**", message);
        context.publishEvent(configEvent);
        return configEvent.toString();
    }
}
