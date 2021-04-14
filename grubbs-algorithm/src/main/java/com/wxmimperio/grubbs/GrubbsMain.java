package com.wxmimperio.grubbs;

import com.wxmimperio.grubbs.tools.CCAritheticCore;
import com.wxmimperio.grubbs.tools.Grubbs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className GrubbsMain.java
 * @description This is the description of GrubbsMain.java
 * @createTime 2021-04-08 16:41:00
 */
public class GrubbsMain {

    public static void main(String[] args) {
        /*double[] ans;
        ans = Grubbs.grubsUse(genData());
        System.out.println(ans.length);
        for (double s : ans)
            System.out.println(s);*/

        List<String> testList = new ArrayList<>();

        testList = testList.stream().limit(10).collect(Collectors.toList());


        System.out.println(testList);


        /*CCAritheticCore arithmetic = new CCAritheticCore();

        List<Double> list = genDataValue();

        List<Double> doubles = new ArrayList<>();
        if (list == null) {
            doubles = null;
        } else {
            int size = list.size();
            if (size <= 3) {
                doubles = null;
            }
            if (size < 8) {
                doubles = arithmetic.fusionToList(1, list);
            }
            if (size < 11) {
                doubles = arithmetic.fusionToList(2, list);
            }
            if (size >= 11) {
                doubles = arithmetic.fusionToList(10, list);
            }
        }
        for (double value : doubles) {
            System.out.println(value);
        }*/
    }

    private static List<Double> genDataValue() {
        List<Double> list = new ArrayList<>();
        list.add(1440538.0D);
        list.add(1284417.0D);
        list.add(3456669.0D);
        list.add(3513111.0D);
        list.add(3789001.0D);
        list.add(1061008.0D);
        list.add(237882.0D);
        list.add(1309979.0D);
        list.add(1514653.0D);
        list.add(1351500.0D);
        list.add(1284417.0D);
        list.add(1351500.0D);

        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                list.add(1351500.0D);
            } else {
                list.add(1284417.0D);
            }
        }
        return list;
    }


    private static double[] genData() {
        double[] data = new double[100];
        data[0] = 1440538.0D;
        data[1] = 1284417.0D;
        data[2] = 3456669.0D;
        data[3] = 3513111.0D;
        data[4] = 3789001.0D;
        data[5] = 1061008.0D;
        data[6] = 237882.0D;
        data[7] = 1351500.0D;
        data[8] = 1309979.0D;
        data[9] = 1514653.0D;

        data[10] = 1351500.0D;
        data[11] = 1351500.0D;
        data[12] = 1351500.0D;
        data[13] = 1351500.0D;
        data[14] = 1351500.0D;
        data[15] = 1351500.0D;
        data[16] = 1351500.0D;
        data[17] = 1351500.0D;
        data[18] = 1351500.0D;
        data[19] = 1351500.0D;

        for (int i = 20; i < 100; i++) {
            if (i % 2 == 0) {
                data[i] = 1351500.0D;
            } else {
                data[i] = 1284417.0D;
            }
        }
        return data;
    }
}
