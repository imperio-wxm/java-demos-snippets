package com.wxmimperio.holtwinters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxmimperio.holtwinters.elasticsearch.HoltWintersModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className HoltWintersMain.java
 * @description This is the description of HoltWintersMain.java
 * @createTime 2021-01-11 21:07:00
 */
public class HoltWintersMain {
    public static void main(String[] args) throws Exception {
        long[] aa = new long[100];

        for (int i = 1; i < 100; i++) {
            aa[i] = i * 10L;
        }

        long[] y = {362, 385, 432, 341, 382, 409, 498, 387, 473, 513, 582, 474, 544, 582, 681, 557, 628, 707, 773, 592, 627, 725, 854, 661};

       /* Double[] dd = {362D, 385D, 432D, 341D, 382D, 409D, 498D, 387D, 473D, 513D,
                582D, 474D, 544D, 582D, 681D, 557D, 628D, 707D, 773D, 592D, 627D, 725D, 854D, 661D};*/

        Double[] dd = {362D, 385D, 432D, 341D, 382D, 409D, 498D, 387D, 473D, 513D,
                582D, 474D, 544D, 582D, 681D, 557D, 628D, 707D, 773D, 592D, 627D, 725D, 854D, 661D};


        double[] bb = HoltWintersJava.forecast(dd, 0.2, 0, 0, 3, 3, true);
        System.out.println(y.length);
        System.out.println(bb.length);
        System.out.println(Arrays.toString(bb));

        // double alpha, double beta, double gamma, int period, SeasonalityType seasonalityType, boolean pad
        HoltWintersModel holtWintersModel =
                new HoltWintersModel(0.95, 0, 0, 288, HoltWintersModel.SeasonalityType.MULTIPLICATIVE, false);
        // System.out.println(Arrays.toString(holtWintersModel.doPredict(Arrays.asList(dd), 3)));

        Map<String, Double> data = inputData();
        System.out.println(Arrays.toString(holtWintersModel.doPredict(data.values(), 288)));

/*        double[] ss = HoltWintersJava.forecast(data.values().toArray(new Double[]{}), 0.6, 0.2, 0, 288, 288, false);
        System.out.println(Arrays.toString(ss));*/
    }

    public static Map<String, Double> inputData() throws Exception {
        Map<String, Double> result = new TreeMap<>();
        List<Double> point = new ArrayList<>();
        String fileName = "/Users/weiximing/code/github/java-demos-snippets/holt-winters-java/src/main/resources/test.csv";
        String line = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                String[] ll = line.split("\\|", -1);
                System.out.println(Arrays.toString(ll));
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
                System.out.println(count);
                sum = new BigDecimal(sum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                point.add(sum);
                result.put(time, sum);
            }
        }
        System.out.println(point);
        return result;
    }
}
