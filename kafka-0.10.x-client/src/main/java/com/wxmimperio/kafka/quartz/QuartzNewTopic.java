package com.wxmimperio.kafka.quartz;

import com.wxmimperio.kafka.comsumer.ConsumerHandle;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by wxmimperio on 2016/12/3.
 */
public class QuartzNewTopic implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzNewTopic.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        final ConsumerHandle consumerHandle = (ConsumerHandle) jobExecutionContext.getJobDetail().getJobDataMap().get("consumerHandle");

        synchronized (consumerHandle.getConsumer()) {
            System.out.println(consumerHandle.getConsumer().toString());
            consumerHandle.getConsumer().commitSync();
            consumerHandle.getBuffer().clear();
            System.out.println("================ " + System.currentTimeMillis());
        }
    }
}
