package com.wxmimperio.springcloud.events;


import com.wxmimperio.springcloud.beans.SchemaInfo;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class RabbitMqEvent extends RemoteApplicationEvent {

    private static final long serialVersionUID = 7743257560365940687L;
    private SchemaInfo schemaInfo;

    public RabbitMqEvent(Object source, String originService, String destinationService, SchemaInfo schemaInfo) {
        super(source, originService, destinationService);
        this.schemaInfo = schemaInfo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SchemaInfo getSchemaInfo() {
        return schemaInfo;
    }

    public void setSchemaInfo(SchemaInfo schemaInfo) {
        this.schemaInfo = schemaInfo;
    }
}
