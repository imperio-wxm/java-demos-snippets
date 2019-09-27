package com.wxmimperio.spring.kafka.service;

import com.wxmimperio.spring.kafka.bean.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static org.apache.kafka.common.record.TimestampType.CREATE_TIME;
import static org.apache.kafka.common.record.TimestampType.LOG_APPEND_TIME;

@Service
public class ProducerService {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMsg(final KafkaMessage kafkaMessage) {
        kafkaMessage.getData().forEach(data -> {
            // public ListenableFuture<SendResult<K, V>> send(String topic, Integer partition, Long timestamp, K key, V data) {
            // 1. org.apache.kafka.common.record.TimestampType.CREATE_TIME (record 被创建的时间)
            // 2. org.apache.kafka.common.record.TimestampType.LOG_APPEND_TIME (用户自定义的时间将被忽略，以进入broker时间为准)
            ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(kafkaMessage.getTopic(), data);
            listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(SendResult<String, String> result) {

                }
            });
            // 阻塞等待结果使用 listenableFuture.get()
            // kafkaTemplate会自动flush，在阻塞等待时提前调用flush
            // kafkaTemplate.flush();
            // listenableFuture.get();
        });
    }

    @Transactional
    public void sendMsgTransactional(final KafkaMessage kafkaMessage) {
        kafkaMessage.getData().forEach(data -> {
            kafkaTemplate.executeInTransaction(transaction -> {
                transaction.send(kafkaMessage.getTopic(), data);
                return true;
            });
        });
    }
}
