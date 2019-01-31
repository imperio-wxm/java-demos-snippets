package com.wxmimperio.springcloud.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class ConfigEvent extends RemoteApplicationEvent {
    private String message;

    public ConfigEvent() {
    }

    public ConfigEvent(Object source, String originService, String message) {
        super(source, originService);
        this.message = message;
    }

    public ConfigEvent(Object source, String originService, String destinationService, String message) {
        super(source, originService, destinationService);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ConfigEvent{" +
                "message='" + message + '\'' +
                '}';
    }
}
