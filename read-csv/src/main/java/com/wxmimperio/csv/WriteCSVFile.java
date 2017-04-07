package com.wxmimperio.csv;

import com.google.common.collect.Lists;
import com.wxmimperio.csv.utils.CSVHelper;

import java.util.List;

/**
 * Created by weiximing.imperio on 2017/4/7.
 */
public class WriteCSVFile {

    public static void main(String[] args) {
        String csvPath1 = System.getProperty("user.dir") + "\\config\\test.csv";

        boolean append = true;

        List<String[]> context = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            String[] str = {"abc" + i, "def" + i, "123456" + i};
            context.add(str);
        }

        boolean flags = CSVHelper.writeCSVFile(csvPath1, context, ',', append);
        if (flags) {
            System.out.println("Write success!");
        }
    }
}
