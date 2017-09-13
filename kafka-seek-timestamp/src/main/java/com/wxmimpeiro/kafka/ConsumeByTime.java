package com.wxmimpeiro.kafka;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by weiximing.imperio on 2017/9/13.
 */
public class ConsumeByTime {

    private KafkaConsumer<String, byte[]> consumer;
    private String[] topics;

    public ConsumeByTime(String[] topics) {
        this.topics = topics;
    }

    private Properties initProp() {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100000);
        return props;
    }

    private void initConsumer() {
        consumer = new KafkaConsumer<>(initProp());
    }

    public void start() throws Exception {

        initConsumer();

        // get topics and partitions
        List<TopicPartition> tpList = Lists.newLinkedList();
        for (String topic : topics) {
            tpList.addAll(getTopicPartList(consumer.partitionsFor(topic)));
        }
        consumer.assign(tpList);

        // set start timestamp
        long timestamp = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20170904 23:50:00").getTime();
        Map<TopicPartition, Long> map = Maps.newHashMap();
        for (TopicPartition tp : tpList) {
            map.put(tp, timestamp);
        }

        // seek by timestamp
        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(map);
        for (TopicPartition tp : offsets.keySet()) {
            try {
                consumer.seek(tp, offsets.get(tp).offset());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // start consume data
        consumeData();
    }

    private void consumeData() {
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(1000);
            for (TopicPartition partition : records.partitions()) {
                List<ConsumerRecord<String, byte[]>> partitionRecords = records.records(partition);
                for (ConsumerRecord<String, byte[]> record : partitionRecords) {
                    try {
                        byte[] buffer = record.value();

                        System.out.println(new String(buffer, "UTF-8"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     *
     * @param partInfoList
     * @return
     */
    private static List<TopicPartition> getTopicPartList(List<PartitionInfo> partInfoList) {
        List<TopicPartition> tpList = new ArrayList<TopicPartition>();
        for (PartitionInfo pi : partInfoList) {
            tpList.add(new TopicPartition(pi.topic(), pi.partition()));
        }
        return tpList;
    }
}
