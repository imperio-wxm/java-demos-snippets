package com.wxmimperio.grubbs;

import com.wxmimperio.grubbs.tools.Grubbs;

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
        double[] ans;
        ans = Grubbs.grubsUse(genData());
        System.out.println(ans.length);
        for (double s : ans)
            System.out.println(s);
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
            if(i % 2 == 0) {
                data[i] = 1351500.0D;
            } else {
                data[i] = 1284417.0D;
            }
        }
        return data;
    }
}
