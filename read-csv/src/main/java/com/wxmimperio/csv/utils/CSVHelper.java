package com.wxmimperio.csv.utils;

import com.google.common.collect.Lists;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/4/7.
 */
public class CSVHelper {

    private static final Logger LOG = LoggerFactory.getLogger(CSVHelper.class);

    public static List<String[]> readCSVFile(String filePath, char splitChar) {
        List<String[]> info = Lists.newArrayList();
        File file = new File(filePath);

        CSVReader reader = null;

        try {
            reader = new CSVReader(new FileReader(file), splitChar);
            String[] values = null;
            while ((values = reader.readNext()) != null) {
                info.add(values);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readerClose(reader);
        }
        LOG.info("Read csv file " + filePath + ", load " + info.size() + " lines.");
        return info;
    }

    private static void readerClose(CSVReader csvReader) {
        if (csvReader != null) {
            try {
                csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
