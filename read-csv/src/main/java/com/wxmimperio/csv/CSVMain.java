package com.wxmimperio.csv;

import com.wxmimperio.csv.utils.CSVHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wxmimperio on 2017/4/10.
 */
public class CSVMain {

    public static void main(String[] args) {
        InputStream is = CSVMain.class.getResourceAsStream("/test.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        List<String[]> csvFile = CSVHelper.readCSVFile(br, '\t');

        for (String[] line : csvFile) {
            System.out.println(Arrays.asList(line));
        }

        try {
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
