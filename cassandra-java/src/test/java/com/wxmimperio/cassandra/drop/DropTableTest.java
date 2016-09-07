package com.wxmimperio.cassandra.drop;

import org.junit.Test;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class DropTableTest {
    @Test
    public void dropTable() {
        String dropTableSQL = "DROP TABLE kafka_crass.kafka_table;";
        DropTable.dropTable(dropTableSQL);
    }
}
