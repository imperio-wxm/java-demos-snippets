package com.wxmimperio.kafka.processors.simpleprocessor;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import java.io.File;
import java.io.IOException;

/**
 * Created by wxmimperio on 2017/6/21.
 */
public class SimpleProcessor implements Processor<String, byte[]> {
    private ProcessorContext context;
    //private KeyValueStore<String, Long> kvStore;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        this.context = context;
        this.context.schedule(1000);
        //this.kvStore = (KeyValueStore) context.getStateStore("COUNTS");
    }

    @Override
    public void process(String dummy, byte[] line) {
        /*String count = new String(line);
        System.out.println("obj = " + dummy);

        Long oldValue = (Long) this.kvStore.get(count);
        System.out.println("obj1 = " + oldValue);

        if (oldValue == null) {
            this.kvStore.put(count, 1L);
        } else {
            this.kvStore.put(count, oldValue + 1);
        }*/
        Schema schema = null;
        GenericRecord gr = null;
        try {
            schema = new Schema.Parser().parse(new File("src/main/resources/wooolh_item_glog.avsc"));
            BinaryDecoder binaryEncoder = DecoderFactory.get().binaryDecoder(line, null);
            System.out.println(binaryEncoder.readArrayStart());
            DatumReader<GenericRecord> datumReader = new SpecificDatumReader<GenericRecord>(schema);
            gr = datumReader.read(null, binaryEncoder);
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
       /* KeyValueIterator iter = this.kvStore.all();

        while (iter.hasNext()) {
            KeyValue entry = (KeyValue) iter.next();
            context.forward(entry.key, entry.value.toString());
        }
        iter.close();*/
        context.commit();
    }

    @Override
    public void close() {
        //this.kvStore.close();
    }
}
