package com.wxmimperio.gson.deserializer.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wxmimperio.gson.deserializer.beans.BaseColumn;
import com.wxmimperio.gson.deserializer.beans.OrderColumn;

public class JacksonSerialization {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BaseColumn.class, new BaseColumnSerialization.Deserializer());
        mapper.registerModule(module);

        String jsonStr = "{\"name\":\"wxm\",\"comment\":\"comment\",\"dataType\":\"STRING\",\"orderType\":\"DESC\"}";
        OrderColumn baseColumn = mapper.readValue(jsonStr, OrderColumn.class);

        System.out.println(baseColumn);
        System.out.println("name = " + baseColumn.getName());
        System.out.println("comment = " + baseColumn.getComment());
        System.out.println("dataType = " + baseColumn.getDataType());
        System.out.println("orderType = " + baseColumn.getOrderType());
    }
}
