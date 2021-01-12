package com.wxmimperio.holtwinters;


import weka.classifiers.timeseries.HoltWinters;
import weka.core.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className Test.java
 * @description This is the description of Test.java
 * @createTime 2021-01-12 12:32:00
 */
public class HoltWintersForecast {

    //    WekaForecaster
    Instances trainingDataSet;
    Attribute attrTime;
    Attribute attrVal;


    boolean exportTraining = false;
    private File expFile = null;

    public void setExportTraining(boolean exportTraining) {
        this.exportTraining = exportTraining;
    }

    Map<Long, Double> testDataSet = new HashMap<>();


    public HoltWintersForecast() {
        attrTime = new Attribute("time", true);
        attrVal = new Attribute("value");
        ArrayList<Attribute> atts = new ArrayList<>();
        atts.add(attrTime);
        atts.add(attrVal);
        trainingDataSet = new Instances("timeseries", atts, 0);
        trainingDataSet.setClass(attrVal);
    }

    //手动插入训练数据
    private void insertTrainningData(String time, Double value) {
        Instance instance = new DenseInstance(2);
        instance.setDataset(trainingDataSet);
        instance.setValue(attrTime, time);
        instance.setValue(attrVal, value);
        trainingDataSet.add(instance);
    }

    private void insertTestData(Double time, Double value) {
        testDataSet.put(Long.valueOf(time.intValue()), value);
    }

    //从map中导入数据到训练集中
    public void importDataByMap(HashMap<Object, Object> dataMap) {
        if (dataMap != null && dataMap.size() > 0) {
            for (Map.Entry<Object, Object> entry : dataMap.entrySet()) {
                try {
                    String time = entry.getKey().toString();
                    Double val = Double.valueOf(entry.getValue().toString());
                    insertTrainningData(time, val);
                } catch (Exception ex) {
                    continue;
                }
            }
        }
    }

    //TODO:从文件中导入数据（CSV文件）
    public void importTrainingDatabyFile(String FileName) {
        try {
            //(文件完整路径),编码格式
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileName), "utf-8"));//GBK
            reader.readLine();//显示标题行,没有则注释掉
//            System.out.println(reader.readLine());
            String line = null;
            String tempStr = "0";
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件时候的分割符,我使用的是,号
                String timeStr = item[0];//CSV中的数据,如果有标题就不用-1
                String valStr = item[1];
                if (!"null".equals(valStr)) {
                    tempStr = valStr;
                }
                Double val = Double.valueOf(tempStr);
                insertTrainningData(timeStr, val);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importTestDataByFile(String FileName) {
        try {
            //(文件完整路径),编码格式
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileName), "utf-8"));//GBK
            reader.readLine();//显示标题行,没有则注释掉
//            System.out.println(reader.readLine());
            String line = null;
            String tempStr = "0";
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件时候的分割符,我使用的是,号
                String timeStr = item[0];//CSV中的数据,如果有标题就不用-1
                String valStr = item[1];
                if (!"null".equals(valStr)) {
                    tempStr = valStr;
                }
                Long time = Long.valueOf(timeStr);
                Double val = Double.valueOf(tempStr);
                testDataSet.put(time, val);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对录入的参数执行holt-winters Training操作，并计算方差数据
     *
     * @param alpha       简单平滑权重
     * @param gamma       季节平滑权重
     * @param cyclelength 周期长度
     * @param startTime   开始时间
     * @param frequence   数据频率（精确到秒）
     * @return
     */
    public double doTraining(double alpha, double gamma, int cyclelength, long startTime, int frequence) {
        /**
         * 1. 开启循环，以0.01为步长 遍历 alpha和gamma参数 （双层循环）【方法外层控制】
         * 2. 依据参数设定新的HoltWinters示例，初始化训练集
         * 3. 循环一个周期执行forecast操作，计算预测值对应的时间戳拼装成一个周期的预测集
         * 4. 计算预测集和实际数据的方差。记录 方差，alpha，beta到集合中
         * 5. 结束迭代，对方差排序，取最小值对应的参数组作为最优参数组
         */
        FileWriter fileWritter = null;
        try {
            if (exportTraining) {
                //如果需要导出traing结果文件
                String fileName = "/Users/kingback/Documents/xx-trainingRes-" + alpha + "-" + gamma + ".csv";
                expFile = new File(fileName);
                if (!expFile.exists()) {
                    expFile.createNewFile();
                }
                fileWritter = new FileWriter(expFile.getPath(), true);
                fileWritter.write("time,tval,fval \n");
            }


            trainingDataSet.sort(attrTime);
//            trainingDataSet.get(0).
            HoltWinters holtWinters = initialHoltWinters(alpha, gamma, cyclelength);
            double RMSE = 0D;
            double MDIFF = 0D;

            for (int i = 0; i < cyclelength; i++) {
                double rs = holtWinters.forecast();
                holtWinters.updateForecaster(rs);
                //计算预测开始时间对应的时间节点
                Long fTime = i * frequence * 1000 + startTime;
//                System.out.println("" + fTime + "," + rs);
                Double tValue = testDataSet.get(fTime);
                if (fileWritter != null) {
                    String content = fTime + "," + tValue + "," + rs + "\n";
                    fileWritter.write(content);
                }
                MDIFF += (Math.pow(rs - tValue, 2));
            }
            RMSE = Math.sqrt(MDIFF / cyclelength);
            if (fileWritter != null) {
                fileWritter.close();
            }
            return RMSE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 执行预测方法
     *
     * @param alpha
     * @param gamma
     * @param foreCastNum
     * @param startTime
     * @param frequence
     */
    public HashMap<Long, Double> forecast(double alpha, double gamma, int cyclelength, int foreCastNum, long startTime, int frequence) {
        try {
            HashMap<Long, Double> res = new HashMap<>();
            trainingDataSet.sort(attrTime);
//            trainingDataSet.get(0).
            HoltWinters holtWinters = initialHoltWinters(alpha, gamma, cyclelength);

            for (int i = 0; i < foreCastNum; i++) {
                double rs = holtWinters.forecast();
                holtWinters.updateForecaster(rs);
                //计算预测开始时间对应的时间节点
                Long fTime = i * frequence * 1000 + startTime;
                res.put(fTime, rs);
            }
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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

    public void showTrainSetInfo() {
        System.out.println("size:" + trainingDataSet.size());
    }

    public static void main(String[] args) {
        Long startTs = System.currentTimeMillis();
        HoltWintersForecast hf = new HoltWintersForecast();
        hf.importTrainingDatabyFile("/Users/kingback/Documents/trainingData-34.csv");
        hf.importTestDataByFile("/Users/kingback/Documents/testData-34.csv");
        hf.showTrainSetInfo();
        double alpha = 0.0;
        double gamma = 0.0;
        double bestAlpha = 0.0;
        double bestGamma = 0.0;
        double minSE = 100;
        for (int alphaIdx = 1; alphaIdx <= 100; alphaIdx++) {
            alpha = 0.01 * alphaIdx;
            if (alphaIdx % 10 == 0) {
                System.out.println(alphaIdx);
            }
            for (int gammaIdx = 1; gammaIdx <= 100; gammaIdx++) {
                //迭代计算alpha和beta值，步长0.01
                gamma = 0.01 * gammaIdx;
                double rmse = hf.doTraining(alpha, gamma, 288, 1581523200000L, 300);
                if (rmse < minSE) {
                    minSE = rmse;
                    bestAlpha = alpha;
                    bestGamma = gamma;
                }
            }
        }
        Long endTs = System.currentTimeMillis();
        System.out.println("训练结束，训练时长:" + (endTs - startTs) / 1000 + "秒,最优参数[Alpha=" + bestAlpha + ",Gamma=" + bestGamma + "],RMSE=" + minSE);

     /*   HoltWintersForecast hfnew = new HoltWintersForecast();
        hfnew.importTrainingDatabyFile("/Users/kingback/Documents/trainingData-34.csv");
        hfnew.importTestDataByFile("/Users/kingback/Documents/testData-34.csv");
        hfnew.setExportTraining(true);
        hfnew.doTraining(bestAlpha, bestGamma, 288, 1581523200000L, 300);
//        hfnew.doTraining(0.1852652, 0.6984017, 288, 1581350400000L, 300);*/
    }
}
