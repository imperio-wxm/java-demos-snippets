package com.wxmimperio.hbase.comsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxmimperio.hbase.common.ParamsConst;
import com.wxmimperio.hbase.common.PropManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerHandle implements Runnable {

    private KafkaConsumer consumer;
    private List<String> topicList;
    private static final int batchSize = 10;

    private static final ThreadLocal<SimpleDateFormat> srcFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        }
    };

    public ConsumerHandle(KafkaConsumer<String, String> consumer, List<String> topicList) {
        this.consumer = consumer;
        this.topicList = topicList;
        this.consumer.subscribe(this.topicList);
    }

    @Override
    public void run() {

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    //可以批量插入
                    //或手动提交offset，目前是自动
                    String message = record.value();
                    JSONObject messageObj = JSON.parseObject(message);
                    String timestamp = messageObj.getString("timestamp");
                    Date date = srcFormat.get().parse(timestamp);

                    if (date.before(srcFormat.get().parse("2017-06-02T00:00:00.000+08:00"))) {
                        writer(message);
                        System.out.println(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writer(String message) {
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
        pw.println(message);
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
