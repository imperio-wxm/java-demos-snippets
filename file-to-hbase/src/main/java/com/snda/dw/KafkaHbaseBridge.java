package com.snda.dw;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.snda.dw.pojo2.Bslog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class KafkaHbaseBridge {
    // private static final String HBASE_ZOOKEEPER_QUORUM = "10.1.9.92";
    // private static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "2222";
    private static final String HBASE_ZOOKEEPER_QUORUM = "10.1.10.141";
    private static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "2190";
    private static final String HBASE_TABLE_NAME = "dqx_deposit_detail_20170222_2";
    private static final String BOOTSTRAP_SERVERS = "10.128.113.104:9092";
    private static final String KAFKA_TOPIC = "tmp_bslog";
    private static final int BATCH_SIZE = 100;
    private HTable table;
    private Consumer<String, String> consumer;

    KafkaHbaseBridge() throws IOException {
        initHBaseTable();
        initKafkaConsumer();
    }

    final static byte COLUMN_SEPARATOR = 0x01;

    private void put(List<Bslog> cache) throws IOException {
        List<Put> putList = Lists.newArrayList();
        for (Bslog log : cache) {
            String ptId = log.getPtId();
            String orderId = log.getOrderId();
            ByteArrayOutputStream keyOs = new ByteArrayOutputStream();
            keyOs.write(ptId.getBytes());
            keyOs.write(COLUMN_SEPARATOR);
            keyOs.write(orderId.getBytes());
            keyOs.flush();
            byte[] key = keyOs.toByteArray();
            keyOs.close();

            String time = log.getSettleTime();
            String price = log.getPrice() + "";
            ByteArrayOutputStream valueOs = new ByteArrayOutputStream();
            valueOs.write(time.getBytes());
            valueOs.write(COLUMN_SEPARATOR);
            valueOs.write(price.getBytes());
            valueOs.flush();
            byte[] value = valueOs.toByteArray();
            valueOs.close();

            Put put = new Put(key);
            System.out.println("======== key = " + new String(key) + "    ========= settle_time = " + time);
            put.add("cf".getBytes(), "v".getBytes(), value);
            putList.add(put);
        }
        if (!putList.isEmpty()) {
            table.put(putList);
        }
    }

    private void start() throws IOException {
        List<Bslog> cache = Lists.newArrayList();

        List<TopicPartition> topicPartitionList = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            topicPartitionList.add(new TopicPartition("tmp_bslog", i));
        }

        consumer.assign(topicPartitionList);

        for(TopicPartition topicPartition : topicPartitionList) {
            consumer.seek(topicPartition,10000);
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            while (true) {
                for (ConsumerRecord<String, String> record : consumer.poll(1)) {

                    String message = record.value().split("\t")[1];
                    JSONObject messageObj = JSON.parseObject(message);
                    if (messageObj.getInteger("messageType") == 101) {
                        Bslog bslog = JSON.parseObject(message, Bslog.class);
                        String dateBegin = dateFormat.format(bslog.getSettleTime());
                        Date myDate2 = dateFormat.parse("2017-02-24 00:00:00");

                        if (java.sql.Date.valueOf(dateBegin).after(myDate2)) {
                            // System.out.println(bslog);
                            cache.add(bslog);
                            if (cache.size() == BATCH_SIZE) {
                                put(cache);
                                cache.clear();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    private void stop() throws IOException {
        table.close();
        consumer.close();
    }

    private void initHBaseTable() throws IOException {
        System.out.println("begin initHBaseTable.....");
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
        conf.set("hbase.zookeeper.property.clientPort", HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT);
        table = new HTable(conf, HBASE_TABLE_NAME);
        System.out.println("finish initHBaseTable.....");
    }

    private void initKafkaConsumer() {
        System.out.println("begin initKafkaConsumer.....");
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "tmp_group");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Lists.newArrayList(KAFKA_TOPIC));
        System.out.println("finish initKafkaConsumer.....");
    }

    public static void main(String[] args) throws IOException {
        new KafkaHbaseBridge().start();
    }
}
