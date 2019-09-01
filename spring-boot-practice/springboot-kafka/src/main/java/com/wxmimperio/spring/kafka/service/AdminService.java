package com.wxmimperio.spring.kafka.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private KafkaAdmin kafkaAdmin;

    @Autowired
    public AdminService(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public void admin() {
        // 1. 默认情况下，broker不可用，则有log记录；但是context会继续启动
        // 2. 可以用initialize()方法显式重试
        kafkaAdmin.initialize();
        // 3. 如果你希望创建topic失败时，context失败则设置fatalIfBrokerNotAvailable 为true
        kafkaAdmin.setFatalIfBrokerNotAvailable(false);
        // 4. 当有已经存在的topic时，自动创建不会报错
        // 5. 当已经存在的topic与自动创建的topic，partition不一致时会记录info日周四
        kafkaAdmin.setAutoCreate(true);
    }

    /**
     * auto create topic
     *
     * @return
     */
    @Bean
    public NewTopic topic1() {
        // public NewTopic(String name, int numPartitions, short replicationFactor);
        // public NewTopic(String name, Map<Integer, List<Integer>> replicasAssignments)
        return new NewTopic("auto_create_topic", 3, (short) 1);
    }
}
