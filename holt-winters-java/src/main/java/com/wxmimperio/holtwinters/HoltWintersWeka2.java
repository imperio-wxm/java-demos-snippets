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
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className HoltWintersWeka.java
 * @description This is the description of HoltWintersWeka.java
 * @createTime 2021-01-12 17:54:00
 */
public class HoltWintersWeka2 {
    private Instances trainingDataSet;
    //private final Attribute attrTime = new Attribute("time", true);
    private final Attribute attrVal = new Attribute("value");
    private static final List<OriginData> originData = new ArrayList<>();
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HoltWintersWeka2() {
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
        inputData();
        //Optional<Double> max = data.stream().max(Double::compareTo);
        //Optional<Double> min = data.stream().min(Comparator.comparing(Function.identity()));
        //max.ifPresent(data::remove);
        //min.ifPresent(data::remove);
        originData.forEach(originData -> {
            Instance instance = new DenseInstance(2);
            instance.setDataset(trainingDataSet);
            instance.setValue(attrVal, originData.getValue());
            trainingDataSet.add(instance);
        });
    }

    public Instances getTrainingDataSet() {
        return trainingDataSet;
    }

    public static List<OriginData> inputData() throws Exception {
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
                long timeKey = getTime(time);
                originData.add(new OriginData(timeKey, sum));
            }
        }
        System.out.println(point);
        return originData;
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

    public double doTraining(double alpha, double gamma, int cycleLength, int foreCastNum, int stepMin) throws Exception {
        HoltWinters holtWinters = initialHoltWinters(alpha, gamma, cycleLength);
        double mdiff = 0D;
        int realForCastNum = 0;
        for (int i = 0; i < foreCastNum; i++) {
            double rs = holtWinters.forecast();
            rs = new BigDecimal(rs).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            holtWinters.updateForecaster(rs);
            long timeKey = genTime(i, stepMin);
            Map<Long, List<OriginData>> originDataMap = originData.stream().collect(Collectors.groupingBy(OriginData::getTime));
            if (originDataMap.containsKey(timeKey)) {
                mdiff += (Math.pow(rs - originDataMap.get(timeKey).get(0).getValue(), 2));
                realForCastNum++;
            } else {
                System.out.println(timeKey);
            }
        }
        return Math.sqrt(mdiff / realForCastNum);
    }

    public static double standardDeviation(List<Double> x) {
        int m = x.size();
        double sum = 0;
        // 求和
        for (double value : x) {
            sum += value;
        }
        // 求平均值
        double dAve = sum / m;
        double dVar = 0;
        // 求方差
        for (double v : x) {
            dVar += (v - dAve) * (v - dAve);
        }
        return Math.sqrt(dVar / m);
    }

    private static long genTime(int index, int stepMin) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTimeFormat.parse(dateFormat.format(new Date()) + " 00:00"));
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        calendar.add(Calendar.MINUTE, index * stepMin);
        return calendar.getTime().getTime();
    }

    /*private String getTime(String time) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(time));
        calendar.add(Calendar.MINUTE, 5);
        return dateFormat.format(calendar.getTime());
    }*/

    private static long getTime(String time) throws Exception {
        return dateTimeFormat.parse(time).getTime();
    }

    public static void main(String[] args) throws Exception {
        HoltWintersWeka2 holtWintersWeka = new HoltWintersWeka2();
        holtWintersWeka.prepareTrainingData();

        System.out.println(holtWintersWeka.getTrainingDataSet().size());

        List<Double> bestResult = holtWintersWeka.forecast(0.01, 0.81, 288, 288);
        System.out.println(bestResult);

        training(holtWintersWeka);
    }

    public static void training(HoltWintersWeka2 holtWintersWeka) throws Exception {
        long startTs = System.currentTimeMillis();
        float defaultStep = 0.01F;
        float alpha = 0.0F;
        float gamma = 0.0F;
        float bestAlpha = 0.0F;
        float bestGamma = 0.0F;
        int defaultMax = 100;
        double minSe = Integer.MAX_VALUE;
        for (int alphaIdx = 1; alphaIdx <= defaultMax; alphaIdx++) {
            alpha = defaultStep * alphaIdx;
            if (alphaIdx % 10 == 0) {
                System.out.println(alphaIdx);
            }
            for (int gammaIdx = 1; gammaIdx <= defaultMax; gammaIdx++) {
                //迭代计算alpha和beta值，步长0.01
                gamma = defaultStep * gammaIdx;
                double rmse = holtWintersWeka.doTraining(alpha, gamma, 288, 288, 5);
                if (rmse < minSe) {
                    minSe = rmse;
                    bestAlpha = alpha;
                    bestGamma = gamma;
                }
            }
        }
        long endTs = System.currentTimeMillis();
        System.out.println("训练结束，训练时长:" + (endTs - startTs) / 1000 + "秒,最优参数[Alpha=" + bestAlpha + ",Gamma=" + bestGamma + "],RMSE=" + minSe);
    }

    public static class OriginData {
        private long time;
        private double value;

        public OriginData() {
        }

        public OriginData(long time, double value) {
            this.time = time;
            this.value = value;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
