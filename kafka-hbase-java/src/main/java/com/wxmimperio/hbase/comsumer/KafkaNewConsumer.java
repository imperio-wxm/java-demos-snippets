package com.wxmimperio.hbase.comsumer;

import com.wxmimperio.hbase.common.ParamsConst;
import com.wxmimperio.hbase.common.PropManager;
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
        props.put("bootstrap.servers", PropManager.getInstance().getPropertyByString(ParamsConst.KAFKA_CONNECT));
        props.put("group.id", group);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    public void execute(int numThread) {
        //ThreadPool
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < numThread; i++) {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(createProducerConfig(this.group));
            executor.submit(new ConsumerHandle(consumer, this.topicList));
        }
    }
}
