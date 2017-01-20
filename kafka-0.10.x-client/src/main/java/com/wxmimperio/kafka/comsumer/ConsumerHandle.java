package com.wxmimperio.kafka.comsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerHandle implements Runnable {

    private KafkaConsumer consumer;
    private List<String> topicList;
    private static final int minBatchSize = 10;
    private String group;


    private Long countNum = 0L;

    public ConsumerHandle(KafkaConsumer<String, String> consumer, List<String> topicList, String group) {
        this.consumer = consumer;
        this.topicList = topicList;
        this.consumer.subscribe(this.topicList);
        this.group = group;

       /* TopicPartition topicPartition = new TopicPartition(this.topic, 1);

        long offset = this.consumer.position(topicPartition);

        System.out.println("=============" + offset);*/
    }

    @Override
    public void run() {
     /*   TopicPartition topicPartition = new TopicPartition(this.topic, 0);
        List<TopicPartition> list = new ArrayList<>();
        list.add(topicPartition);

        Collection<TopicPartition> collection = list;

       *//* this.consumer.seekToEnd(collection);

        long offset = this.consumer.position(topicPartition);*//*

        Map<TopicPartition, Long> endOffsets = this.consumer.endOffsets(collection);
        System.out.println(endOffsets);*/

        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        /*TopicPartition topicPartition = new TopicPartition(this.topic, 0);

        OffsetAndMetadata offsetAndMetadata = this.consumer.committed(topicPartition);
        System.out.println(offsetAndMetadata.offset());*/


        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Integer.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
                /*System.out.println("Thread=" + Thread.currentThread().getName() +
                        " value=" + record.value() + " partition=" + record.partition() +
                        " topic" + record.topic() + " offset" + record.offset() + " time=" + record.timestamp() + " group=" + this.group);*/
            }

            countNum += buffer.size();

            Calendar nowCal = new GregorianCalendar();
            int nowSecond = nowCal.get(Calendar.SECOND);
            if (nowSecond % 2 == 0) {
                System.out.println("插入发送count");
                countNum = 0L;
            }

            System.out.println("Thread=" + Thread.currentThread().getName() + " " + countNum + " group = " + group + " topic" + topicList);

            if (buffer.size() % minBatchSize == 0) {
                consumer.commitSync(); //批量完成写入后，手工sync offset
                buffer.clear();
            }
            consumer.commitSync(); //批量完成写入后，手工sync offset
            buffer.clear();
        }
    }
}
