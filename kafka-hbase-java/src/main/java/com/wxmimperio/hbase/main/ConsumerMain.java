package com.wxmimperio.hbase.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxmimperio.hbase.common.ParamsConst;
import com.wxmimperio.hbase.common.PropManager;
import com.wxmimperio.hbase.comsumer.KafkaNewConsumer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerMain {
    /*public static void main(String args[]) {
        List<String> topicList = PropManager.getInstance().getPropertyByStringList(ParamsConst.TOPIC_NAME);
        String threadNum = PropManager.getInstance().getPropertyByString(ParamsConst.THREAD_NUM);
        String groupName = PropManager.getInstance().getPropertyByString(ParamsConst.GROUP_ID);

        KafkaNewConsumer consumer = new KafkaNewConsumer(topicList, groupName);
        consumer.execute(Integer.valueOf(threadNum));
    }*/

    private static final ThreadLocal<SimpleDateFormat> srcFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        }
    };

    public static void main(String[] args) {
        String filePath = PropManager.getInstance().getPropertyByString(ParamsConst.READ_PATH);
        readTxtFile(filePath);
    }

    public static void readTxtFile(String filePath) {
        try {
            List<String> list = new ArrayList<>();

            long line = 0L;

            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {

                    JSONObject messageObj = JSON.parseObject(lineTxt);
                    String timestamp = messageObj.getString("timestamp");

                    try {
                        Date date = srcFormat.get().parse(timestamp);

                        if (date.before(srcFormat.get().parse("2017-06-01T00:00:00.000+08:00"))) {
                            list.add(lineTxt);
                            //System.out.println(lineTxt);
                            line++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("parse error=" + lineTxt);
                    }
                }
                read.close();

                System.out.println("line= " + line);

                //write
                writer(list);
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }


    public static void writer(List<String> list) {
        FileWriter fw = null;
        String filePath = PropManager.getInstance().getPropertyByString(ParamsConst.FILE_PATH);
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(filePath);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        for (String str : list) {
            pw.println(str);
        }
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
