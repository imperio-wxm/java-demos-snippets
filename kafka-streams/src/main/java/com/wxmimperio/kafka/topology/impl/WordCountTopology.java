package com.wxmimperio.kafka.topology.impl;

import com.wxmimperio.kafka.topology.Topology;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

/**
 * Created by weiximing.imperio on 2017/10/27.
 */
public class WordCountTopology implements Topology {

    @Override
    public TopologyBuilder get(String source, String sink) {
        TopologyBuilder builder = new TopologyBuilder();
        builder = builder.addSource("SOURCE", source);
        builder = builder.addProcessor("PROCESS1", new WordCountProcessor(), "SOURCE");
        builder = builder.addSink("SINK", sink, "PROCESS1");
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
            context.forward(key,value);
        }

        @Override
        public void punctuate(long timestamp) {
            System.out.println("SimpleProcessor, Partition = " + context.partition() + " offset = " + context.offset());
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
}
