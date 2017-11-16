package com.wxmimperio.kafka.processors;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class FatherProcessor implements Processor<byte[], byte[]>, ProcessorSupplier {
    private static Logger LOG = LoggerFactory.getLogger(FatherProcessor.class);

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        String messageValue = new String(value, Charset.forName("UTF-8"));

        JsonObject jsonMessage = new JsonParser().parse(messageValue).getAsJsonObject();

        String messageType = jsonMessage.get("type").getAsString();
        if (messageType.equalsIgnoreCase("1")) {
            context.forward(key, value, "ChildOneProcess");
        } else if (messageType.equalsIgnoreCase("2")) {
            context.forward(key, value, "ChildTwoProcess");
        }
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
        return new FatherProcessor();
    }
}
