package com.wxmimperio.kafka.main;

import com.wxmimperio.kafka.topology.impl.WordCountTopology;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;

/**
 * Created by weiximing.imperio on 2017/10/27.
 */
public class WordCountProcessorMain {
    private KafkaStreams streams;


    public static void main(String[] args) {
        String sourceTopic = "streams-test1";
        String[] sinkTopic = "streams-output_1,streams-output_2".split(",", -1);
        new WordCountProcessorMain().start(sourceTopic, sinkTopic);
    }

    private void start(String sourceTopic, String[] sinkTopics) {
        final Properties settings = new Properties();
        // stream config
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "WordCountProcessor-v0.1");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.128.74.83:9092");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "10.128.74.83:2181");
        settings.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        settings.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);

        StreamsConfig config = new StreamsConfig(settings);
        WordCountTopology wordCountTopology = new WordCountTopology();
        streams = new KafkaStreams(wordCountTopology.get(sourceTopic, sinkTopics), config);
        streams.start();
    }
}
