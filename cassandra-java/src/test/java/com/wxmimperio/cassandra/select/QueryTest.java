package com.wxmimperio.cassandra.select;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class QueryTest {

    @Test
    public void queryTest() {
        String querySQL = "SELECT * FROM kafka_crass.kafka_table;";
        List<Map<String, Object>> resultList = Query.query(querySQL, "kafka_table", "kafka_crass");
        System.out.println(resultList.size());

        for (Map<String, Object> result : resultList) {
            System.out.println("===========================");
            System.out.println("\tid=" + (Integer) result.get("id"));
            System.out.println("\ttopic=" + result.get("topic"));
            System.out.println("\toffset=" + (Long) result.get("offset"));
            System.out.println("===========================");
        }
    }
}
