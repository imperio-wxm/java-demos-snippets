package com.wxmimperio.kafka.topology.avroimpl;

import com.wxmimperio.kafka.topology.Topology;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;


/**
 * Created by wxmimperio on 2017/10/18.
 */
public class AvroTopology implements Topology {

    @Override
    public TopologyBuilder get(String source, String sink) {
        TopologyBuilder builder = new TopologyBuilder();
        builder = builder.addSource("SOURCE", source);
        builder = builder.addProcessor("PROCESS1", new AvroProcessor(), "SOURCE");
        builder = builder.addSink("SINK", sink, "PROCESS1");
        return builder;
    }

    private class AvroProcessor implements Processor<String, byte[]>, ProcessorSupplier {
        private ProcessorContext context;

        @Override
        public Processor get() {
            return new AvroProcessor();
        }

        @Override
        public void init(ProcessorContext processorContext) {
            this.context = processorContext;
            this.context.schedule(1000);
        }

        @Override
        public void process(String key, byte[] values) {
            GenericRecord gr = null;

            String schemaPath = "" + context.topic();

            try {
                Schema schema = getSchemaByName(schemaPath);
                BinaryDecoder binaryEncoder = DecoderFactory.get().binaryDecoder(values, null);
                DatumReader<GenericRecord> datumReader = new SpecificDatumReader<GenericRecord>(schema);
                gr = datumReader.read(gr, binaryEncoder);

                System.out.println(gr);

                context.forward(key, gr.toString().getBytes());
                Thread.sleep(1000);
            } catch (Exception e) {
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

        private Schema getSchemaByName(String schemaPath) throws Exception {
            return new Schema.Parser().parse(schemaPath);
        }
    }
}
