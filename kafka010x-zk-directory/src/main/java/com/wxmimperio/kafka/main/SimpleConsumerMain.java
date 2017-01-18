package com.wxmimperio.kafka.main;

import kafka.common.ErrorMapping;
import kafka.common.NotCoordinatorForConsumerException;
import kafka.common.OffsetMetadataAndError;
import kafka.common.TopicAndPartition;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.coordinator.GroupTopicPartition;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2017/1/17.
 */
public class SimpleConsumerMain {

    public static void main(String args[]) {
        long offset = fetchOffset("192.168.18.74",9092,"group_1","test_1",0);
        System.out.println(offset);
    }

    public static long fetchOffset(String leaderHost, int port, String group, String topic, int partitionId) {
        List<TopicAndPartition> partitions = new ArrayList<TopicAndPartition>();
        TopicAndPartition partition = new TopicAndPartition(topic, partitionId);

        kafka.javaapi.consumer.SimpleConsumer consumer =
                new kafka.javaapi.consumer.SimpleConsumer(leaderHost, port, 100 * 1000, 64 * 1024, "fetchOffset" + topic + partitionId);

        long retrievedOffset = 0;
        partitions.add(partition);
        //CurrentVersion为1，则metadata从kafka获取，为0表示从zookeeper获取
        kafka.javaapi.OffsetFetchRequest fetchRequest = new kafka.javaapi.OffsetFetchRequest(group, partitions, (short) 1, 0, "getOffsetClient");
        kafka.javaapi.OffsetFetchResponse fetchResponse = consumer.fetchOffsets(fetchRequest);
        OffsetMetadataAndError result = fetchResponse.offsets().get(partition);

        consumer.close();
        short offsetFetchErrorCode = result.error();
        if (offsetFetchErrorCode == ErrorMapping.NotCoordinatorForConsumerCode()) {
            System.out.println("NotCoordinatorForConsumerCode");
            throw new NotCoordinatorForConsumerException("NotCoordinatorForConsumerCode:16");
        } else {
            retrievedOffset = result.offset();
        }
        return retrievedOffset;
    }
}
