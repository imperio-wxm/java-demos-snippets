package com.wxmimperio.kafka.producer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ProducerHandle implements Runnable {
    private Producer<String, String> producer;
    private int threadNumber;
    private String topic;

    public ProducerHandle(Producer<String, String> producer, int threadNumber, String topic) {
        this.producer = producer;
        this.threadNumber = threadNumber;
        this.topic = topic;
    }

    @Override
    public void run() {
        int messageNo = 0;
        while (messageNo <= 50000) {
            Date nowTime = new Date();
            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

            String key = String.valueOf(threadNumber);
            String data = "hello kafka message" + messageNo + " " + key + " " + time.format(nowTime);

            producer.send(new ProducerRecord<String, String>(topic, Integer.toString(messageNo), data));

            System.out.println("Thread " + Thread.currentThread().getName() + " " + data);
            messageNo++;

           /* try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
