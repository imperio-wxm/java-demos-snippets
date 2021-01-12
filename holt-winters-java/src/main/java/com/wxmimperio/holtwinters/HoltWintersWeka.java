package com.wxmimperio.holtwinters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import weka.classifiers.timeseries.HoltWinters;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className HoltWintersWeka.java
 * @description This is the description of HoltWintersWeka.java
 * @createTime 2021-01-12 17:54:00
 */
public class HoltWintersWeka {
    private Instances trainingDataSet;
    //private final Attribute attrTime = new Attribute("time", true);
    private final Attribute attrVal = new Attribute("value");

    public HoltWintersWeka() {
        prepareAttributes();
    }

    private HoltWinters initialHoltWinters(double alpha, double gamma, int cycleLength) throws Exception {
        HoltWinters holtWinters = new HoltWinters();
        //设置禁止趋势计算
        holtWinters.setExcludeTrendCorrection(true);
        //set Alpha param
        holtWinters.setValueSmoothingFactor(alpha);
        //set Gamma param
        holtWinters.setSeasonalSmoothingFactor(gamma);
        //set Season Cycle
        holtWinters.setSeasonCycleLength(cycleLength);
        holtWinters.buildClassifier(trainingDataSet);
        return holtWinters;
    }

    public void prepareAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        //attributes.add(attrTime);
        attributes.add(attrVal);
        trainingDataSet = new Instances("timeseries", attributes, 0);
        trainingDataSet.setClass(attrVal);
    }

    public void prepareTrainingData() throws Exception {
        List<Double> data = inputData();
        Optional<Double> max = data.stream().max(Double::compareTo);
        Optional<Double> min = data.stream().min(Comparator.comparing(Function.identity()));
        max.ifPresent(data::remove);
        min.ifPresent(data::remove);
        data.forEach(value -> {
            Instance instance = new DenseInstance(2);
            instance.setDataset(trainingDataSet);
            //instance.setValue(attrTime, key);
            instance.setValue(attrVal, value);
            trainingDataSet.add(instance);
        });
    }

    public Instances getTrainingDataSet() {
        return trainingDataSet;
    }

    public static List<Double> inputData() throws Exception {
        List<Double> point = new ArrayList<>();
        String fileName = "/Users/weiximing/code/github/java-demos-snippets/holt-winters-java/src/main/resources/test.csv";
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
        return point;
    }

    public List<Double> forecast(double alpha, double gamma, int cycleLength, int foreCastNum) throws Exception {
        List<Double> res = new ArrayList<>();
        HoltWinters holtWinters = initialHoltWinters(alpha, gamma, cycleLength);
        for (int i = 0; i < foreCastNum; i++) {
            double rs = holtWinters.forecast();
            rs = new BigDecimal(rs).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            holtWinters.updateForecaster(rs);
            res.add(rs);
        }
        return res;
    }

    private String getTime(String time) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(time));
        calendar.add(Calendar.MINUTE, 5);
        return dateFormat.format(calendar.getTime());
    }

    public static void main(String[] args) throws Exception {
        HoltWintersWeka holtWintersWeka = new HoltWintersWeka();
        holtWintersWeka.prepareTrainingData();

        System.out.println(holtWintersWeka.getTrainingDataSet().size());

        List<Double> forecast = holtWintersWeka.forecast(0.05, 1, 288, 288);
        System.out.println(forecast);
    }
}
