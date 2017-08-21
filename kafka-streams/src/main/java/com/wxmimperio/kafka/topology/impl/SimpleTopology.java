package com.wxmimperio.kafka.topology.impl;

import com.wxmimperio.kafka.topology.Topology;
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;

import java.io.File;
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
                System.out.println("key = " + key + " , line = " + line.length);
                schema = new Schema.Parser().parse(new File("src/main/resources/wooolh_item_glog.avsc"));
                BinaryDecoder binaryEncoder = DecoderFactory.get().binaryDecoder(line, null);
                System.out.println(binaryEncoder.readString());
                DatumReader<GenericRecord> datumReader = new SpecificDatumReader<GenericRecord>(schema);
                gr = datumReader.read(gr, binaryEncoder);
                for (Schema.Field field : gr.getSchema().getFields()) {
                    System.out.println("field = " + field.name() + ", value = " + gr.get(field.name()));
                }
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
            context.commit();
        }

        @Override
        public void close() {
            System.out.println("metrics = " + context.metrics());
        }
    }
}
