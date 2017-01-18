package com.wxmimperio.kafka.comsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerHandle implements Runnable {

    private KafkaConsumer consumer;
    private String topic;
    private static final int minBatchSize = 10;

    public ConsumerHandle(KafkaConsumer<String, String> consumer, String topic) {
        this.consumer = consumer;
        this.topic = topic;
        this.consumer.subscribe(Collections.singletonList(this.topic));

       /* TopicPartition topicPartition = new TopicPartition(this.topic, 1);

        long offset = this.consumer.position(topicPartition);

        System.out.println("=============" + offset);*/
        List<String> partition = this.consumer.partitionsFor(this.topic);
        System.out.println(partition);
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

        TopicPartition topicPartition = new TopicPartition(this.topic, 0);

        OffsetAndMetadata offsetAndMetadata = this.consumer.committed(topicPartition);
        System.out.println(offsetAndMetadata.offset());
        System.out.println(offsetAndMetadata.metadata());

      /*  while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Integer.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
                System.out.println("Thread=" + Thread.currentThread().getName() +
                        " value=" + record.value() + " partition=" + record.partition() +
                        " topic" + record.topic() + " offset" + record.offset() + " time=" + record.timestamp());
            }
           *//* if (buffer.size() % minBatchSize == 0) {
                consumer.commitSync(); //批量完成写入后，手工sync offset
                buffer.clear();
            }
            consumer.commitSync(); //批量完成写入后，手工sync offset
            buffer.clear();*//*
        }*/
    }
}
