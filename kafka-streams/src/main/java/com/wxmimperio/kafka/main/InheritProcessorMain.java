package com.wxmimperio.kafka.main;

import com.wxmimperio.kafka.topology.impl.InheritTopology;
import com.wxmimperio.kafka.topology.impl.SimpleTopology;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;

public class InheritProcessorMain {
    private KafkaStreams streams;

    public static void main(String[] args) {
        String sourceTopic = "inherit_stream_source";
        String sinkTopic = "inherit_one_sink,inherit_two_sink";
        new InheritProcessorMain().start(sourceTopic, sinkTopic);

    }

    private void start(String sourceTopic, String sinkTopic) {
        final Properties settings = new Properties();
        // stream config
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "InheritProcessor-v0.1");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.128.74.83:9092");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "10.128.74.83:2181");
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        //settings.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        // consumer config
        //settings.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);

        StreamsConfig config = new StreamsConfig(settings);
        InheritTopology inheritTopology = new InheritTopology();
        streams = new KafkaStreams(inheritTopology.get(sourceTopic, sinkTopic), config);
        streams.start();
    }
}
