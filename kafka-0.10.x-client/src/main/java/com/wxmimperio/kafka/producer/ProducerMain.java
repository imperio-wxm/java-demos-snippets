package com.wxmimperio.kafka.producer;

/**
 * Created by wxmimperio on 2016/12/5.
 */
public class ProducerMain {
    public static void main(String args[]) {
        /*KafkaNewProducer producer = new KafkaNewProducer("test_001");
        producer.execute(3);

        KafkaNewProducer producer1 = new KafkaNewProducer("test_2");
        producer1.execute(3);

        KafkaNewProducer producer2 = new KafkaNewProducer("test_1");
        producer2.execute(3);*/

        KafkaNewProducer producer = new KafkaNewProducer("sy_bpe_all");
        producer.execute(3);

    }
}
