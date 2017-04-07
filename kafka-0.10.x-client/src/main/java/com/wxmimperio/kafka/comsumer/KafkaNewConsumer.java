package com.wxmimperio.kafka.comsumer;

import com.wxmimperio.kafka.nettyclient.base.EchoClient;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class KafkaNewConsumer {

    private List<String> topicList;
    private String group;

    public KafkaNewConsumer(List<String> topicList, String group) {
        this.topicList = topicList;
        this.group = group;
    }

    //Init conf
    private static Properties createProducerConfig(String group) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.18.51:9092");
        props.put("group.id", group);
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", ByteArrayDeserializer.class.getName());

        return props;
    }

    public void execute(int numThread) {
        //ThreadPool
        ExecutorService executor = Executors.newCachedThreadPool();

        //KafkaNewProducer Message
        for (int i = 0; i < numThread; i++) {
            KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(createProducerConfig(this.group));
            //Send Message
            executor.submit(new ConsumerHandle(consumer, this.topicList,this.group));
        }
    }
}

/*
kafka-simple-consumer-shell.sh --topic __consumer_offsets --partition 49 --broker-list 192.168.18.74:9092 --max-messages 1 --formatter "kafka.coordinator.GroupMetadataManager\$OffsetsMessageFormatter"
*/
