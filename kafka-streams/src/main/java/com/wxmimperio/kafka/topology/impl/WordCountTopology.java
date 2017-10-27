package com.wxmimperio.kafka.topology.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wxmimperio.kafka.topology.TopologyList;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Map;

/**
 * Created by weiximing.imperio on 2017/10/27.
 */
public class WordCountTopology implements TopologyList {

    @Override
    public TopologyBuilder get(String source, String[] sink) {
        TopologyBuilder builder = new TopologyBuilder();
        builder = builder.addSource("SOURCE", source);

        // PROCESS1
        builder = builder.addProcessor("PROCESS1", new WordCountProcessor(), "SOURCE");
        builder = builder.addSink("SINK1", sink[0], "PROCESS1");

        // PROCESS2
        builder = builder.addProcessor("PROCESS2", new SplitProcessor(), "SOURCE");
        builder = builder.addSink("SINK2", sink[1], "PROCESS2");
        return builder;
    }

    private class WordCountProcessor implements Processor<String, String>, ProcessorSupplier {
        private ProcessorContext context;

        @Override
        public void init(ProcessorContext processorContext) {
            this.context = processorContext;
        }

        @Override
        public void process(String key, String value) {
            System.out.println("key = " + key + ", value = " + value);
            context.forward(key, value);
        }

        @Override
        public void punctuate(long timestamp) {
            System.out.println("WordCountProcessor, Partition = " + context.partition() + " offset = " + context.offset());
            context.commit();
        }

        @Override
        public void close() {
            System.out.println("metrics = " + context.metrics());
        }

        @Override
        public Processor get() {
            return new WordCountProcessor();
        }
    }


    private class SplitProcessor implements Processor<String, String>, ProcessorSupplier {
        private ProcessorContext context;

        @Override
        public Processor get() {
            return new SplitProcessor();
        }

        @Override
        public void init(ProcessorContext processorContext) {
            this.context = processorContext;
        }

        @Override
        public void process(String key, String value) {
            JsonObject jsonValue = new JsonParser().parse(value).getAsJsonObject();
            StringBuilder line = new StringBuilder();
            for (Map.Entry<String, JsonElement> entry : jsonValue.entrySet()) {
                line.append(entry.getValue().getAsString()).append("\t");
            }
            context.forward(key, line.toString().substring(0, line.toString().length() - 1));
        }

        @Override
        public void punctuate(long timestamp) {
            System.out.println("SplitProcessor, Partition = " + context.partition() + " offset = " + context.offset());
            context.commit();
        }

        @Override
        public void close() {
            System.out.println("metrics = " + context.metrics());
        }
    }
}
