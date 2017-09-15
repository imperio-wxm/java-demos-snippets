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
public class FileReaderOps {

    private static String[] tables = {"click_type", "appid", "ad_channel", "click_time", "extend", "extend_param", "mac",
            "idfa", "idfa_type", "imei", "androidid", "advertisingid", "ua", "ip", "result", "is_show"};

    private static String checkDate = "2017-09-13";

    public static void readFileByLines(String fileName) {
        List<String> buffer = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        File file = new File(fileName);
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int index = 1;
            String line = "";
            while ((line = reader.readLine()) != null) {
                JsonObject returnData = new JsonParser().parse(line).getAsJsonObject();
                Date date = sdf.parse(returnData.get("data").getAsJsonObject().get("click_time").getAsString().substring(0, 11));
                if (date.compareTo(sdf.parse(checkDate)) == 0) {
                    buffer.add(handlerData(returnData));
                }
                index++;
            }

            //String writeFileName = FileReaderOps.class.getResource("/").getPath() + "mobile_" + checkDate + ".txt";
            String writeFileName = "" + checkDate + ".txt";
            FileWriterOps.appendFile(writeFileName, buffer);
            System.out.println("All size = " + index);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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

    private static String handlerData(JsonObject returnData) {
        if (returnData.has("data")) {
            JsonObject data = returnData.get("data").getAsJsonObject();
            StringBuffer stringBuffer = new StringBuffer();
            String event_time = "";
            for (String name : tables) {
                if ("click_time".equalsIgnoreCase(name)) {
                    event_time = data.get(name) == null ? "" : data.get(name).getAsString();
                }
                stringBuffer.append(data.get(name).getAsString()).append("\t");
            }
            stringBuffer.insert(0, event_time + "\t");

            String returnMsg = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
            return returnMsg;
        }
        return "";
    }

    private static void checkFileExistAndCreate(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
