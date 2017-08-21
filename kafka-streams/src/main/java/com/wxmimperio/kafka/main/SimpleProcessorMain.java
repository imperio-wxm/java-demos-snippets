package com.wxmimperio.kafka.main;

import com.wxmimperio.kafka.topology.impl.SimpleTopology;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;

/**
 * Created by weiximing.imperio on 2017/8/21.
 */
public class SimpleProcessorMain {
    private KafkaStreams streams;

    public static void main(String[] args) {
        String sourceTopic = "";
        String sinkTopic = "test_stream";
        new SimpleProcessorMain().start(sourceTopic, sinkTopic);

    }

    private void start(String sourceTopic, String sinkTopic) {
        final Properties settings = new Properties();
        // stream config
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "SimpleProcessor-v0.1");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "");
        settings.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        settings.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
        settings.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        // consumer config
        settings.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);
        /*settings.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        settings.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());*/

        StreamsConfig config = new StreamsConfig(settings);
        SimpleTopology simpleTopology = new SimpleTopology();

        streams = new KafkaStreams(simpleTopology.get(sourceTopic, sinkTopic), config);
        streams.start();
    }


}
