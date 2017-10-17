package com.wxmimperio.kafka.topology.simpleimpl;

import com.google.gson.JsonParser;
import com.wxmimperio.kafka.HttpClientUtil;
import com.wxmimperio.kafka.topology.Topology;
import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import org.apache.avro.generic.GenericRecord;

import java.io.IOException;

/**
 * Created by weiximing.imperio on 2017/8/21.
 */
public class SimpleTopology implements Topology {

    @Override
    public TopologyBuilder get(String source, String sink) {
        TopologyBuilder builder = new TopologyBuilder();
        builder = builder.addSource("SOURCE", source);
        builder = builder.addProcessor("PROCESS1", new SimpleProcessor(), "SOURCE");
        builder = builder.addSink("SINK", sink, "PROCESS1");
        return builder;
    }

    private class SimpleProcessor implements Processor<String, byte[]>, ProcessorSupplier {
        private ProcessorContext context;

        @Override
        public Processor get() {
            return new SimpleProcessor();
        }

        @Override
        public void init(ProcessorContext processorContext) {
            this.context = processorContext;
            this.context.schedule(1000);
        }

        @Override
        public void process(String key, byte[] line) {
            Schema schema = null;
            GenericRecord gr = null;

            try {
                schema = getSchemaFromRegistry(context.topic());
                BinaryDecoder binaryEncoder = DecoderFactory.get().binaryDecoder(line, null);
                DatumReader<GenericRecord> datumReader = new SpecificDatumReader<GenericRecord>(schema);
                gr = datumReader.read(gr, binaryEncoder);

                System.out.println(gr);

                context.forward(key, gr.toString().getBytes());

                Thread.sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void punctuate(long timestamp) {
            System.out.println("SimpleProcessor, Partition = " + context.partition() + " offset = " + context.offset());
            //context.forward();
            context.commit();
        }

        @Override
        public void close() {
            System.out.println("metrics = " + context.metrics());
        }

        private Schema getSchemaFromRegistry(String name) throws IOException {
            String subject = HttpClientUtil.doGet("http://10.128.74.83:8081/subjects/" + name + "/versions/latest", null);
            String schema = new JsonParser().parse(subject).getAsJsonObject().get("schema").getAsString();
            return new Schema.Parser().parse(schema);
        }
    }
}
