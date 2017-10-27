package com.wxmimperio.kafka;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

/**
 * Created by weiximing.imperio on 2017/10/27.
 */
public class Test {

    @org.junit.Test
    public void jsonTest() {
        String jsonStr = "{\"topic\":\"streams-test1\",\"message\":\"message=1795\",\"eventTime\":\"2017-10-27 14:41:32\"}";
        JsonObject jsonValue = new JsonParser().parse(jsonStr).getAsJsonObject();
        StringBuilder line = new StringBuilder();
        for (Map.Entry<String, JsonElement> entry : jsonValue.entrySet()) {
            line.append(entry.getValue().getAsString()).append("\t");
        }

        System.out.println(line.toString().substring(0, line.toString().length() - 1));

    }
}
