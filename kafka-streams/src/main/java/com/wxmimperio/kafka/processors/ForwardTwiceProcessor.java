package com.wxmimperio.kafka.processors;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;

public class ForwardTwiceProcessor implements Processor<byte[], byte[]>, ProcessorSupplier {

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        context.forward(key, value, "ChildOneProcess");
        context.forward(key, value, "ChildTwoProcess");
    }

    @Override
    public void punctuate(long timestamp) {
        context.commit();
    }

    @Override
    public void close() {

    }

    @Override
    public Processor get() {
        return new ForwardTwiceProcessor();
    }
}
