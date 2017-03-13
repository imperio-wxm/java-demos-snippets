package com.wxmimperio.hbase.comsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxmimperio.hbase.hbase.HbaseDao;
import com.wxmimperio.hbase.pojo.Bslog;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerHandle implements Runnable {

    private KafkaConsumer consumer;
    private List<String> topicList;
    private static final int batchSize = 10;

    public ConsumerHandle(KafkaConsumer<String, String> consumer, List<String> topicList) {
        this.consumer = consumer;
        this.topicList = topicList;
        this.consumer.subscribe(this.topicList);
    }

    @Override
    public void run() {

        List<Bslog> bslogs = new ArrayList<>();

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {
                    //可以批量插入
                    //或手动提交offset，目前是自动
                    String message = record.value().split("\t")[1];
                    JSONObject messageObj = JSON.parseObject(message);

                    if (messageObj.getInteger("messageType") == 101) {
                        Bslog bslog = JSON.parseObject(message, Bslog.class);
                        if (bslogs.size() % batchSize == 0) {
                            HbaseDao.putList(bslogs);
                            bslogs.add(bslog);
                        }
                        System.out.println(bslog);
                    }
                }
                HbaseDao.putList(bslogs);
                bslogs.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
