package com.wxmimperio.holtwinters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TestMain {

    public static void main(String[] args) throws Exception {
        inputData();
    }

    public static void inputData() throws Exception {
        List<Double> point = new ArrayList<>();
        String fileName = "E:\\code\\java-demos-snippets\\holt-winters-java\\src\\main\\resources\\19_20.csv";
        String line = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                String[] ll = line.split("\\|", -1);
                String json = ll[1].replaceAll("\"\"", "\"");
                json = json.substring(1, json.length() - 1);
                String time = ll[2].replaceAll("\"", "");
                JSONArray jsonArray = JSON.parseArray(json);
                double sum = 0;
                int count = 0;
                for (Object jsonNode : jsonArray) {
                    JSONObject node = ((JSONObject) jsonNode);
                    if (node.containsKey("usage")) {
                        sum += node.getDouble("usage");
                        count++;
                    }
                }
                sum = new BigDecimal(sum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                point.add(sum);
            }
        }
        System.out.println(point);
    }
}
