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
        rpcClient.initClient("10.128.113.53:50001");
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
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "10.128.113.104:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "error_group");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        consumer = new KafkaConsumer<>(props);
    }

    public void start() throws IOException, ParseException {
        initFlumeRpcClient();
        initKafkaConsumer();

        String endTime = "2017-08-05 14:56:40";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(endTime);

        List<TopicPartition> tpList = getTopicPartList(consumer.partitionsFor("error_topic_glog"));
        consumer.assign(tpList);
        long timestamp = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20170804 00:00:00").getTime();
        Map<TopicPartition, Long> map = new HashMap<>();
        for (TopicPartition tp : tpList) {
            map.put(tp, timestamp);
        }
        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(map);
        for (TopicPartition tp : offsets.keySet()) {
            consumer.seek(tp, offsets.get(tp).offset());
        }

        try {
            while (true) {
                for (ConsumerRecord<String, String> record : consumer.poll(1000)) {
                    if (record.value().contains("swy_")) {

                        String event_time = record.value().split("\\|")[1];
                        String topic = record.value().split("\\|")[0];

                        if (sdf.parse(event_time).before(date)) {
                            List<Event> events = new ArrayList<>();

                            Event event = EventBuilder.withBody(record.value().toString(), Charset.forName("UTF-8"));
                            Map<String, String> headerMap = new HashMap<>();
                            headerMap.put("topic", topic);
                            event.setHeaders(headerMap);
                            events.add(event);
                            rpcClient.sendDataToFlume(events);
                            System.out.println(record.value());
                        }
                    }
                }
            }
        } finally {
            consumer.close();
            rpcClient.cleanUp();
        }
    }
}
