package com.wxmimperio.cassandra.insert;

import org.junit.Test;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class InsertTest {

    @Test
    public void singleInsert() {
        String insertSQL = "INSERT INTO kafka_crass.kafka_table (id,topic,offset) " +
                "VALUES (1,'topic_001',123456789);";
        String json = "{\"id\":\"2\",\"topic\":\"topic_002\",\"offset\":\"9876543210\"}";
        String jsonSQL = "INSERT INTO kafka_crass.kafka_table JSON '" + json + "'";

        System.out.println(jsonSQL);

        SingleInsert.insert(insertSQL);
        SingleInsert.insert(jsonSQL);
    }

    @Test
    public void btchInsertTest() {
        BatchInsert.batchInsert();
    }
}
