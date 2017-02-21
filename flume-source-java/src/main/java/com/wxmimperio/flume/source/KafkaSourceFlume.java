package com.wxmimperio.flume.source;

import com.google.common.collect.ImmutableMap;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.conf.ConfigurationException;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.instrumentation.SourceCounter;
import org.apache.flume.source.AbstractSource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.ObjectName;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by weiximing.imperio on 2017/2/21.
 */
public class KafkaSourceFlume extends AbstractSource implements EventDrivenSource, Configurable {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaSourceFlume.class);

    private KafkaConsumer<String, String> consumer;
    //The Source counter.
    private SourceCounter sourceCounter;
    //The Parameters.
    private Properties parameters;
    //The Context.
    private Context context;
    //The Executor service.
    private ExecutorService executorService;


    //init params
    public void configure(Context context) {
        this.context = context;
        ImmutableMap<String, String> props = this.context.getParameters();

        /**
         * 获取context配置
         */
        this.parameters = new Properties();
        for (String key : props.keySet()) {
            String value = props.get(key);
            if (value == null) {
                throw new ConfigurationException(key + " to consume from needs to be configured");
            }
            this.parameters.put(key, value);
        }
        //source monitoring count
        if (sourceCounter == null) {
            sourceCounter = new SourceCounter(getName());
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        //flume source资源监控
        sourceCounter.start();
        LOG.info("Kafka Source started...");

        String topic = (String) this.parameters.get("topicName");
        String threadCount = (String) this.parameters.get("threadCount");

        List<String> topicList = new ArrayList<>();
        topicList.add(topic);

        // now launch all the threads
        this.executorService = Executors.newFixedThreadPool(Integer.parseInt(threadCount) * 5);
        this.consumer = new KafkaConsumer<>(createProducerConfig());
        this.consumer.subscribe(topicList);

        //多线程处理
        for (int threadNum = 0; threadNum < Integer.parseInt(threadCount); threadNum++) {
            this.executorService.submit(new ConsumerWorker(this.consumer, threadNum, this.sourceCounter));
        }
    }

    @Override
    public synchronized void stop() {
        try {
            this.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.stop();
        this.sourceCounter.stop();
        // Remove the MBean registered for Monitoring
        ObjectName objName = null;
        try {
            objName = new ObjectName("org.apache.flume.source" + ":type=" + getName());
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(objName);
        } catch (Exception ex) {
            System.out.println("Failed to unregister the monitored counter: " + objName + ex.getMessage());
        }
    }


    //Init conf
    private Properties createProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", (String) this.parameters.get("kafkaConnect"));
        props.put("group.id", (String) this.parameters.get("groupName"));
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    /**
     * shutdown consumer threads.
     *
     * @throws Exception the exception
     */
    private void shutdown() throws Exception {
        if (consumer != null) {
            consumer.close();
        }
        if (executorService != null) {
            executorService.shutdown();
        }
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }


    /**
     * Real Consumer Thread.
     */
    private class ConsumerWorker implements Runnable {
        KafkaConsumer<String, String> consumer;
        private int threadNumber;
        private SourceCounter srcCount;

        public ConsumerWorker(KafkaConsumer<String, String> consumer, int threadNumber, SourceCounter srcCount) {
            this.consumer = consumer;
            this.threadNumber = threadNumber;
            this.srcCount = srcCount;
        }

        /**
         * Run void.
         */
        @Override
        public void run() {
            List<Event> events = new ArrayList<>();
            ConsumerRecords<String, String> records = this.consumer.poll(100);
            try {
                for (ConsumerRecord<String, String> record : records) {

                    String message = record.value().replace(",", "\t");

                    LOG.info("Receive Message [Thread " + this.threadNumber + ": " + message + "]");
                    Event event = null;
                    try {
                        event = EventBuilder.withBody(message.getBytes("UTF-8"));
                        events.add(event);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    this.srcCount.incrementEventAcceptedCount();
                }
                //send event to channel
                getChannelProcessor().processEventBatch(events);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
