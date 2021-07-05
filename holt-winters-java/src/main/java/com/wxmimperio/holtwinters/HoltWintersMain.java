package com.wxmimperio.holtwinters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxmimperio.holtwinters.elasticsearch.HoltWintersModel;
import weka.classifiers.timeseries.HoltWinters;

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
 * @className HoltWintersMain.java
 * @description This is the description of HoltWintersMain.java
 * @createTime 2021-01-11 21:07:00
 */
public class HoltWintersMain {
    private static final List<HoltWintersWeka2.OriginData> originData = new ArrayList<>();
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void main(String[] args) throws Exception {
      /*  long[] aa = new long[100];

        for (int i = 1; i < 100; i++) {
            aa[i] = i * 10L;
        }

        long[] y = {362, 385, 432, 341, 382, 409, 498, 387, 473, 513, 582, 474, 544, 582, 681, 557, 628, 707, 773, 592, 627, 725, 854, 661};

       *//* Double[] dd = {362D, 385D, 432D, 341D, 382D, 409D, 498D, 387D, 473D, 513D,
                582D, 474D, 544D, 582D, 681D, 557D, 628D, 707D, 773D, 592D, 627D, 725D, 854D, 661D};*//*

        Double[] dd = {362D, 385D, 432D, 341D, 382D, 409D, 498D, 387D, 473D, 513D,
                582D, 474D, 544D, 582D, 681D, 557D, 628D, 707D, 773D, 592D, 627D, 725D, 854D, 661D};


        double[] bb = HoltWintersJava.forecast(dd, 0.2, 0, 0, 3, 3, true);*/
/*        System.out.println(y.length);
        System.out.println(bb.length);
        System.out.println(Arrays.toString(bb));*/

        // double alpha, double beta, double gamma, int period, SeasonalityType seasonalityType, boolean pad
        // 训练结束，训练时长:245秒,最优参数[Alpha=0.02,Gamma=0.29999998,Beta=0.02],RMSE=0.5923668427430137
        // 训练结束，训练时长:241秒,最优参数[Alpha=0.02,Gamma=0.29999998,Beta=0.01],RMSE=0.5923668427430137
        // 训练结束，训练时长:244秒,最优参数[Alpha=0.049999997,Gamma=0.07,Beta=0.95],RMSE=0.1290282700613994
        float beta = 0.2F;
        HoltWintersModel holtWintersModel =
                new HoltWintersModel(0.01, 0.001, 0.84, 288, HoltWintersModel.SeasonalityType.MULTIPLICATIVE, false);
        //=System.out.println(Arrays.toString(holtWintersModel.doPredict(Arrays.asList(dd), 3)));

        inputData();


        List<Double> value = originData.stream().sorted(new Comparator<HoltWintersWeka2.OriginData>() {
            @Override
            public int compare(HoltWintersWeka2.OriginData o1, HoltWintersWeka2.OriginData o2) {
                return Long.valueOf(o1.getTime() - o2.getTime()).intValue();
            }
        }).map(HoltWintersWeka2.OriginData::getValue).collect(Collectors.toList());

        System.out.println(Arrays.toString(holtWintersModel.doPredict(value, 288)));

        //value = value.subList(0, 577);
        System.out.println(value.size());

        training(value);


        /*double[] result = holtWintersModel.doPredict(value, 288);
        System.out.println(result.length);

        Map<Long, HoltWintersWeka2.OriginData> appInfoMap = originData.stream()
                .collect(Collectors.toMap(HoltWintersWeka2.OriginData::getTime, appInfo -> appInfo));
        */
        // System.out.println(Arrays.toString(holtWintersModel.doPredict(value, 288)));

      /*  double[] ss = HoltWintersJava.forecast(data.values().toArray(new Double[]{}), 0.6, 0.2, 0, 288, 288, false);
        System.out.println(Arrays.toString(ss));*/
    }

    private static long genTime(int index, int stepMin) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTimeFormat.parse("2021-01-18 00:00"));
        //calendar.add(Calendar.DAY_OF_YEAR, -2);
        calendar.add(Calendar.MINUTE, index * stepMin);
        return calendar.getTime().getTime();
    }

    public static double doTraining(Map<Long, HoltWintersWeka2.OriginData> appInfoMap, double[] result) throws Exception {
        double mdiff = 0D;
        int realForCastNum = 0;
        for (int i = 0; i < result.length; i++) {
            long timeKey = genTime(i, 5);
            if (appInfoMap.containsKey(timeKey)) {
                double oldValue = appInfoMap.get(timeKey).getValue();
                double nowValue = result[i];
                mdiff += (Math.pow(nowValue - oldValue, 2));
                realForCastNum++;
            } else {
                System.out.println(timeKey);
            }
        }
        return Math.sqrt(mdiff / realForCastNum);
    }

    public static void training(List<Double> value) throws Exception {
        long startTs = System.currentTimeMillis();
        float defaultStep = 0.01F;
        float alpha = 0.0F;
        float gamma = 0.0F;
        float bestAlpha = 0.0F;
        float bestGamma = 0.0F;
        float bestBeta = 0.0F;
        int defaultMax = 100;
        double minSe = Integer.MAX_VALUE;

        Map<Long, HoltWintersWeka2.OriginData> appInfoMap = originData.stream()
                .collect(Collectors.toMap(HoltWintersWeka2.OriginData::getTime, appInfo -> appInfo));

        for (int alphaIdx = 1; alphaIdx <= defaultMax; alphaIdx++) {
            alpha = defaultStep * alphaIdx;
            if (alphaIdx % 10 == 0) {
                System.out.println(alphaIdx);
            }
            for (int gammaIdx = 1; gammaIdx <= defaultMax; gammaIdx++) {
                gamma = defaultStep * gammaIdx;

                //System.out.println("alpha = " + alpha + " gamma = " + gamma + " beta = " + beta);
                HoltWintersModel holtWintersModel =
                        new HoltWintersModel(alpha, 0.001, gamma, 288, HoltWintersModel.SeasonalityType.MULTIPLICATIVE, false);

                double[] result = holtWintersModel.doPredict(value, 288);
                double rmse = doTraining(appInfoMap, result);
                if (rmse < minSe) {
                    minSe = rmse;
                    bestAlpha = alpha;
                    bestGamma = gamma;
                }
            }
        }
        long endTs = System.currentTimeMillis();
        System.out.println("训练结束，训练时长:" + (endTs - startTs) / 1000 + "秒,最优参数[Alpha=" + bestAlpha + ",Gamma=" + bestGamma + ",Beta=" + bestBeta + "],RMSE=" + minSe);
    }

    public static List<HoltWintersWeka2.OriginData> inputData() throws Exception {
        List<Double> point = new ArrayList<>();
        String fileName = "E:\\code\\java-demos-snippets\\holt-winters-java\\src\\main\\resources\\14_19.csv";
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
                originData.add(new HoltWintersWeka2.OriginData(timeKey, sum));
            }
        }
        //System.out.println(point);
        return originData;
    }

    private static long getTime(String time) throws Exception {
        return dateTimeFormat.parse(time).getTime();
    }
}
