package com.wxmimperio.kafka.serializer;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Collections;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2017/8/22.
 */
public class SpecificAvroSerde<T extends org.apache.avro.specific.SpecificRecord> implements Serde<T> {
    private final Serde<T> inner;

    /**
     * Constructor used by Kafka Streams.
     */
    public SpecificAvroSerde() {
        inner = (Serde<T>) Serdes.serdeFrom(new SpecificAvroSerializer<>(), new SpecificAvroDeserializer<>());
    }

    public SpecificAvroSerde(SchemaRegistryClient client) {
        this(client, Collections.<String, Object>emptyMap());
    }

    public SpecificAvroSerde(SchemaRegistryClient client, Map<String, ?> props) {
        inner = (Serde<T>) Serdes.serdeFrom(new SpecificAvroSerializer<>(client, props), new SpecificAvroDeserializer<>(client, props));
    }

    @Override
    public Serializer<T> serializer() {
        return inner.serializer();
    }

    @Override
    public Deserializer<T> deserializer() {
        return inner.deserializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        inner.serializer().configure(configs, isKey);
        inner.deserializer().configure(configs, isKey);
    }

    @Override
    public void close() {
        inner.serializer().close();
        inner.deserializer().close();
    }

}
