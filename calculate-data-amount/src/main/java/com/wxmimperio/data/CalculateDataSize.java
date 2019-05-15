package com.wxmimperio.data;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculateDataSize {


    public static void main(String[] args) {
        String filePath = "out.txt";
        List<String> result = new ArrayList<>();
        long allSize = 0;
        int index = 0;
        while (index != 2000000) {
            String data = "285a7b1dd9af4905a87329ef22324f97-285a7b1dd9af4905a87329ef22324f97-285a7b1dd9af4905a87329ef22324f97-285a7b1dd9af4905a87329ef22324f97";
            allSize += data.getBytes().length;
            result.add(data);
            if (index == 0) {
                // 单条记录大小
                System.out.println(String.format("Single record size = %s MB, %s Bytes", bytesToMB(data.getBytes().length), data.getBytes().length));
            }
            index++;
        }
        System.out.println(String.format("All records size = %s MB, %s Bytes", bytesToMB(allSize), allSize));
        System.out.println("===============");
        //writeFile(result, filePath);
    }

    private static double bytesToMB(long byteSize) {
        // Bytes -> MB, 保留3位小数
        return new BigDecimal(byteSize * 1.00 / 1024 / 1024).setScale(3, RoundingMode.FLOOR).doubleValue();
    }

    private static void writeFile(List<String> data, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            data.forEach(pw::write);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                System.out.println(String.format("File %s, size = %s MB, %s Bytes", file.getName(), bytesToMB(file.length()), file.length()));
                System.out.println(String.format("Write %s done.", file.getName()));
            }
        }
    }
}
