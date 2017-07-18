package com.wxmimperio.kafka;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

/**
 * Created by wxmimperio on 2017/6/21.
 */
public class SimpleProcessor implements Processor<String, String> {
    private ProcessorContext context;
    private KeyValueStore<String, Long> kvStore;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        this.context = context;
        this.context.schedule(1000);
        this.kvStore = (KeyValueStore) context.getStateStore("COUNTS");
    }

    @Override
    public void process(String dummy, String line) {
        String count = line;
        System.out.println("obj = " + dummy);

        Long oldValue = (Long) this.kvStore.get(count);
        System.out.println("obj1 = " + oldValue);

        if (oldValue == null) {
            this.kvStore.put(count, 1L);
        } else {
            this.kvStore.put(count, oldValue + 1);
        }

    }

    @Override
    public void punctuate(long timestamp) {
        KeyValueIterator iter = this.kvStore.all();

        while (iter.hasNext()) {
            KeyValue entry = (KeyValue) iter.next();
            context.forward(entry.key, entry.value.toString());
        }
        iter.close();
        context.commit();
    }

    @Override
    public void close() {
        this.kvStore.close();
    }
}
