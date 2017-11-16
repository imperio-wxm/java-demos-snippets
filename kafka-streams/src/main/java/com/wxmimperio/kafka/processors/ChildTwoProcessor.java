package com.wxmimperio.kafka.processors;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;


public class ChildTwoProcessor implements Processor<byte[], byte[]>, ProcessorSupplier {
    private static Logger LOG = LoggerFactory.getLogger(FatherProcessor.class);

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        String messageValue = new String(value, Charset.forName("UTF-8"));
        System.out.println("ChildTwoProcessor ===== " + messageValue);
        context.forward(key, value);
    }

    @Override
    public void punctuate(long timestamp) {
        context.commit();
    }

    @Override
    public void close() {
        LOG.info("Processor " + context.applicationId() + " is closed!");
    }

    @Override
    public Processor get() {
        return new ChildTwoProcessor();
    }
}
