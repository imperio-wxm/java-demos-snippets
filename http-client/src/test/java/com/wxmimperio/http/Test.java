package com.wxmimperio.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void jsonTest() {
        Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();

        Map<String, String> testMap = new HashMap<String, String>();
        testMap.put("name", "wxm");
        testMap.put("age", "5");

        System.out.println(gson2.toJson(testMap).toString());
    }
}
