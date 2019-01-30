package com.wxmimperio.gson.deserializer.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.wxmimperio.gson.deserializer.beans.BaseColumn;
import com.wxmimperio.gson.deserializer.beans.OrderColumn;

public class GsonDeserializer {
    public static final JsonDeserializer<BaseColumn> getOrderColumnDeserializer = (jsonElement, type, jsonDeserializationContext) -> new GsonBuilder().create().fromJson(jsonElement, OrderColumn.class);

    public static void main(String[] args) {
        String jsonStr = "{\"name\":\"wxm\",\"orderType\":\"ASC\",\"comment\":\"comment\",\"dataType\":\"STRING\"}";
        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<OrderColumn>() {
        }.getType(), getOrderColumnDeserializer).create();

        OrderColumn orderColumn = gson.fromJson(jsonStr, OrderColumn.class);

        System.out.println(gson.toJsonTree(orderColumn));

        System.out.println(orderColumn);
        System.out.println("name = " + orderColumn.getName());
        System.out.println("comment = " + orderColumn.getComment());
        System.out.println("dataType = " + orderColumn.getDataType());
        System.out.println("orderType = " + orderColumn.getOrderType());
    }
}
