package com.wxmimperio.kafka.consumer;

import com.wxmimperio.kafka.flume.FlumeRpcClient;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by weiximing.imperio on 2017/7/18.
 */
public class Main {

    private Consumer<String, String> consumer;
    FlumeRpcClient rpcClient;

    public static void main(String[] args) {
        try {
            new Main().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFlumeRpcClient() {
        rpcClient = new FlumeRpcClient();
        rpcClient.initClient("");
    }

    private static List<TopicPartition> getTopicPartList(List<PartitionInfo> partInfoList) {
        List<TopicPartition> tpList = new ArrayList<TopicPartition>();
        for (PartitionInfo pi : partInfoList) {
            tpList.add(new TopicPartition(pi.topic(), pi.partition()));
        }
        return tpList;
    }

    private void initKafkaConsumer() {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        consumer = new KafkaConsumer<>(props);
    }

    public void start() throws IOException, ParseException {
        initFlumeRpcClient();
        initKafkaConsumer();

        List<TopicPartition> tpList = getTopicPartList(consumer.partitionsFor(""));
        consumer.assign(tpList);
        long timestamp = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20170718 00:00:00").getTime();
        Map<TopicPartition, Long> map = new HashMap<>();
        for (TopicPartition tp : tpList) {
            map.put(tp, timestamp);
        }
        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(map);
        for (TopicPartition tp : offsets.keySet()) {
            consumer.seek(tp, offsets.get(tp).offset());
        }


        List<Event> events = new ArrayList<>();
        try {
            while (true) {
                for (ConsumerRecord<String, String> record : consumer.poll(1000)) {
                    if (record.value().startsWith("")) {
                        System.out.println(record.value());

                        Event event = EventBuilder.withBody(record.value().toString(), Charset.forName("UTF-8"));
                        events.add(event);
                    }
                }
                rpcClient.sendDataToFlume(events);
            }
        } finally {
            consumer.close();
            rpcClient.cleanUp();
        }
    }
}
