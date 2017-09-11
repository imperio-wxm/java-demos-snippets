package com.wxmimperio.kafka.processors;

import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.StateStoreSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.state.Stores;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wxmimperio on 2017/6/21.
 */
public class SimpleStreams {

    public static void main(String[] args) {
        final Properties streamsConfiguration = new Properties();
        // Give the Streams application a unique name.  The name must be unique in the Kafka cluster
        // against which the application is run.
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "SimpleProcessor-v0.1");
        // Where to find Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.128.74.83:9092");
        streamsConfiguration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "10.128.74.83:2181");
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.KEY_SERDE_CLASS_DOC, StringSerializer.class);
        streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
        streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_DOC, ByteArraySerializer.class);
        //streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);

        TopologyBuilder builder = new TopologyBuilder();

        Map<String, String> changelogConfig = new HashMap();
        StateStoreSupplier countStoreSupplier = Stores.create("COUNTS")
                .withKeys(Serdes.String())
                .withValues(Serdes.Long())
                .inMemory()
                //.enableLogging(changelogConfig)
                .build();

        builder.addSource("SOURCE", "wooolh_item_glog")
                .addProcessor("PROCESS1", new SimpleProcessorSuper(), "SOURCE")
                // create the in-memory state store "COUNTS" associated with processor "PROCESS1"
                //.addStateStore(countStoreSupplier, "PROCESS1")
                //.addProcessor("PROCESS2", new SimpleProcessorSuper() /* the ProcessorSupplier that can generate MyProcessor3 */, "PROCESS1")
                //.addProcessor("PROCESS3", new SimpleProcessorSuper()/* the ProcessorSupplier that can generate MyProcessor3 */, "PROCESS1")

                // connect the state store "COUNTS" with processor "PROCESS2"
                //.connectProcessorAndStateStores("PROCESS1", "COUNTS")
                //.connectProcessorAndStateStores("PROCESS1", "COUNTS")

                .addSink("SINK1", "test_stream", "PROCESS1");


        KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);

        System.out.println(builder.copartitionGroups());

        streams.start();
    }
}
