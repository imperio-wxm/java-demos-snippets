package com.wxmimperio.file;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/9/15.
 */
public class FileMain {

    public static void main(String[] args) {
        /*String filePath = "/home/rt/kafka_2.11-0.8.2.1/bin/sy_dc_collect_20170925.txt";
        FileReaderOps.readFileByLines(filePath);*/

    }

    public static void readFileByLines(String fileName) {

        int allSize = 0;

        File file = new File(fileName);
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int index = 1;
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains("Each write cost") && line.contains("")) {
                    int n = 4;
                    String size = line.substring(line.length() - n, line.length());
                    System.out.println(size);
                    allSize += Integer.valueOf(size);
                }
                index++;
            }

            reader.close();

            System.out.println(allSize);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
