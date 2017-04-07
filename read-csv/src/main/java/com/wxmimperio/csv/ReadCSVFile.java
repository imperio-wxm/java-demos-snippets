package com.wxmimperio.csv;

import com.wxmimperio.csv.utils.CSVHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/4/7.
 */
public class ReadCSVFile {
    public static void main(String[] args) {
        String csvPath1 = System.getProperty("user.dir") + "\\config\\test.csv";

        List<String[]> csvFile = CSVHelper.readCSVFile(csvPath1, '\t');

        for (String[] line : csvFile) {
            System.out.println(Arrays.asList(line));
        }
    }
}
