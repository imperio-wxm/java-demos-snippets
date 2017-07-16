package com.wxmimperio.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;

/**
 * Created by wxmimperio on 2017/6/22.
 */
public class SimpleProcessorSuper implements ProcessorSupplier {

    @Override
    public Processor get() {
        return new SimpleProcessor();
    }
}
