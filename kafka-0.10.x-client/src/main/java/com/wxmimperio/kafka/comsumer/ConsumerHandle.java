package com.wxmimperio.kafka.comsumer;

import com.wxmimperio.kafka.nettyclient.base.EchoClient;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ConsumerHandle implements Runnable {

    private KafkaConsumer<String, byte[]> consumer;
    private List<String> topicList;
    private static final int minBatchSize = 10;
    private String group;


    private Long countNum = 0L;

    private Map<String, Long> topicCountNum = new ConcurrentHashMap<>();

    public ConsumerHandle(KafkaConsumer<String, byte[]> consumer, List<String> topicList, String group) {
        this.consumer = consumer;
        this.topicList = topicList;
        this.consumer.subscribe(this.topicList);
        this.group = group;

       /* TopicPartition topicPartition = new TopicPartition(this.topic, 1);

        long offset = this.consumer.position(topicPartition);

        System.out.println("=============" + offset);*/
    }

    @Override
    public void run() {
        long lastOffset = 0L;
        int partition = -1;
        String currentTopic = "";
     /*   TopicPartition topicPartition = new TopicPartition(this.topic, 0);
        List<TopicPartition> list = new ArrayList<>();
        list.add(topicPartition);

        Collection<TopicPartition> collection = list;

       *//* this.consumer.seekToEnd(collection);

        long offset = this.consumer.position(topicPartition);*//*

        Map<TopicPartition, Long> endOffsets = this.consumer.endOffsets(collection);
        System.out.println(endOffsets);*/

        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        /*TopicPartition topicPartition = new TopicPartition(this.topic, 0);

        OffsetAndMetadata offsetAndMetadata = this.consumer.committed(topicPartition);
        System.out.println(offsetAndMetadata.offset());*/

        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        try {

            //Schema schema = new Schema.Parser().parse(new File("src/main/resources/meiyu_user_active.avsc"));
            //Schema schema = new Schema.Parser().parse(new File("src/main/resources/meiyu_user_reg.avsc"));
            //Schema schema = new Schema.Parser().parse(new File("src/main/resources/pt_game_login.avsc"));
            //Schema schema = new Schema.Parser().parse(new File("src/main/resources/meiyu_user_deposit.avsc"));

            Schema schema = new Schema.Parser().parse(new File("src/main/resources/pt_game_deposit.avsc"));

            DatumReader<GenericRecord> datumReader = new SpecificDatumReader<GenericRecord>(schema);


            while (true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(100);

                for (TopicPartition topicPartition : records.partitions()) {
                    List<ConsumerRecord<String, byte[]>> topicPartitionRecords = records.records(new TopicPartition(topicPartition.topic(), topicPartition.partition()));


                    //System.out.println("topic = " + topicPartition.topic() + " partition = " + topicPartition.partition() + " size = " + topicPartitionRecords.size());
                    String key = "cassandra-" + this.group + "-" + topicPartition.topic() + "-" + topicPartition.partition();
                    long oldCount = (topicCountNum.get(key) == null) ? 0L : topicCountNum.get(key);
                    topicCountNum.put(key, oldCount + topicPartitionRecords.size());


                    for (ConsumerRecord<String, byte[]> record : topicPartitionRecords) {
                    /*buffer.add(record);
                    record.key();*/
                        //System.out.println(record.key());

                        //System.out.println(new String(record.value()));

                        byte[] bufferMsg = record.value();
                        BinaryDecoder binaryEncoder = DecoderFactory.get().binaryDecoder(bufferMsg, null);
                        GenericRecord gr = null;
                        gr = datumReader.read(gr, binaryEncoder);
                        System.out.println("==================");
                        System.out.println(gr.get("event_time"));
                        System.out.println(gr.get("app_id"));
                        //System.out.println(gr.get("channel_id"));
                        // active
                        //System.out.println(gr.get("mobile_type"));
                        //System.out.println(gr.get("device_id"));
                        // reg
                        //System.out.println(gr.get("user_id"));

                        // pt_login
                        //System.out.println(gr.get("area_id"));
                        //System.out.println(gr.get("pt_id"));

                        //mobile_deposit
                        //System.out.println(gr.get("area_id"));
                        //System.out.println(gr.get("user_id"));
                        //System.out.println(gr.get("amount"));
                        //System.out.println(gr.get("order_id"));
                        System.out.println(gr.get("area_id"));
                        System.out.println(gr.get("amount"));
                        System.out.println(gr.get("group_id"));
                        System.out.println(gr.get("item_id"));
                        System.out.println(gr.get("item_num"));
                        System.out.println(gr.get("pt_id"));
                        System.out.println(gr.get("order_id"));

                        //System.out.println(record.serializedValueSize());
                        lastOffset = record.offset();
                        partition = record.partition();
                        currentTopic = record.topic();
/*                    System.out.println("Thread=" + Thread.currentThread().getName() +
                            " value=" + record.value() + " partition=" + record.partition() +
                            " topic" + record.topic() + " offset" + record.offset() + " time=" + record.timestamp() + " group=" + this.group);*/
                    }
                }

            /*for (ConsumerRecord<String, String> record : records.records(this.topicList.get(0))) {
            }

            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
                lastOffset = record.offset();
                partition = record.partition();
                currentTopic = record.topic();
                System.out.println("Thread=" + Thread.currentThread().getName() +
                        " value=" + record.value() + " partition=" + record.partition() +
                        " topic" + record.topic() + " offset" + record.offset() + " time=" + record.timestamp() + " group=" + this.group);

            }*/

                countNum += buffer.size();

           /* Calendar nowCal = new GregorianCalendar();
            int nowSecond = nowCal.get(Calendar.SECOND);
            if (nowSecond == 00) {

                Future<Boolean> future = threadPool.submit(new Callable<Boolean>() {
                    public Boolean call() throws Exception {
                        EchoClient echoClient = new EchoClient("127.0.0.1", 65535, topicCountNum);
                        return echoClient.send();
                    }
                });

                try {
                    if(future.get().booleanValue()) {
                        countNum = 0L;
                        System.out.println(topicCountNum);
                        topicCountNum.clear();
                        System.out.println("插入发送count");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                *//*EchoClient echoClient = new EchoClient("127.0.0.1", 65535, topicCountNum);
                if (echoClient.send()) {
                    countNum = 0L;
                    System.out.println(topicCountNum);
                    topicCountNum.clear();
                }
                System.out.println("插入发送count");*//*
            }*/

                //System.out.println("Thread=" + Thread.currentThread().getName() + " " + countNum + " group = " + group + " topic" + topicList);

           /* if (buffer.size() % minBatchSize == 0) {
                consumer.commitAsync(); //批量完成写入后，手工sync offset
                buffer.clear();
            }
            consumer.commitAsync(); //批量完成写入后，手工sync offset
            buffer.clear();*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
