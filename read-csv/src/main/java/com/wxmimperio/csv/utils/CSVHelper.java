package com.wxmimperio.csv.utils;

import com.google.common.collect.Lists;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/4/7.
 */
public class CSVHelper {
    private static final Logger LOG = LoggerFactory.getLogger(CSVHelper.class);

    public static final char DEFAULT_ESCAPE_CHARACTER = '\"';

    /**
     * @param splitChar
     * @return
     */
    public static List<String[]> readCSVFile(BufferedReader br, char splitChar) {
        List<String[]> info = Lists.newArrayList();

        CSVReader reader = null;
        try {
            reader = new CSVReader(br, splitChar, DEFAULT_ESCAPE_CHARACTER);
            String[] values = null;
            while ((values = reader.readNext()) != null) {
                info.add(values);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }
        LOG.info("Read csv file "  + ", load " + info.size() + " lines.");
        return info;
    }

    /**
     *
     * @param filePath
     * @param splitChar
     * @return
     */
    public static List<String[]> readCSVFile(String filePath, char splitChar) {
        List<String[]> info = Lists.newArrayList();

        CSVReader reader = null;
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8"));
            reader = new CSVReader(input, splitChar, DEFAULT_ESCAPE_CHARACTER);
            String[] values = null;
            while ((values = reader.readNext()) != null) {
                info.add(values);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }
        LOG.info("Read csv file "  + ", load " + info.size() + " lines.");
        return info;
    }


    /**
     * @param filePath
     * @param content
     * @param splitChar
     * @return
     */
    public static boolean writeCSVFile(String filePath, List<String[]> content, char splitChar, boolean append) {
        boolean flags = false;

        CSVWriter csvWriter = null;
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath, append), Charset.forName("GBK"));
            csvWriter = new CSVWriter(out, splitChar);
            csvWriter.writeAll(content);
            flags = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(csvWriter);
        }
        LOG.info("Write csv file " + filePath + ", load " + content.size() + " lines.");
        return flags;
    }

    /**
     * @param object
     */
    private static void close(Object object) {
        try {
            if (object != null) {
                if (object.getClass().getName().equalsIgnoreCase(CSVReader.class.getName())) {
                    CSVReader csvReader = (CSVReader) object;
                    csvReader.close();
                } else {
                    CSVWriter csvWriter = (CSVWriter) object;
                    csvWriter.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
