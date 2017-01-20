package com.wxmimperio.kafka.comsumer;

import com.wxmimperio.kafka.nettyclient.base.EchoClient;
import org.apache.kafka.clients.consumer.KafkaConsumer;

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
        props.put("bootstrap.servers", "192.168.18.74:9092");
        props.put("group.id", group);
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    public void execute(int numThread) {
        //ThreadPool
        ExecutorService executor = Executors.newCachedThreadPool();

        //KafkaNewProducer Message
        for (int i = 0; i < numThread; i++) {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(createProducerConfig(this.group));
            EchoClient echoClient = new EchoClient("127.0.0.1", 65535);
            //Send Message
            executor.submit(new ConsumerHandle(consumer, this.topicList, this.group,echoClient));
        }
    }
}

/*
kafka-simple-consumer-shell.sh --topic __consumer_offsets --partition 49 --broker-list 192.168.18.74:9092 --max-messages 1 --formatter "kafka.coordinator.GroupMetadataManager\$OffsetsMessageFormatter"
*/
