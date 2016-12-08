package com.wxmimperio.kafka.comsumer;

import com.wxmimperio.kafka.quartz.QuartzNewTopic;
import com.wxmimperio.kafka.quartz.QuartzUtil;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.quartz.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class KafkaNewConsumer {

    private String topic;

    public KafkaNewConsumer(String topic) {
        this.topic = topic;
    }

    public void execute(int numThread) throws Exception {
        //ThreadPool
        ExecutorService executor = Executors.newFixedThreadPool(3);
        QuartzUtil quartzUtil = QuartzUtil.getInstance("Job_Group", "Trigger_Group");
        //KafkaNewProducer Message
        for (int i = 0; i < numThread; i++) {
            final ConsumerHandle consumerHandle = new ConsumerHandle(topic);
            //Send Message
            executor.submit(consumerHandle);
            /*JobDataMap job1Map = new JobDataMap();
            job1Map.put("consumerHandle", consumerHandle);
            quartzUtil.addJob(
                    "get_topic_job" + consumerHandle.toString(),
                    "get_topic_trigger" + consumerHandle.toString(),
                    QuartzNewTopic.class,
                    "*//*1 * * * * ?",
                    job1Map
            );*/
        }
    }
}
