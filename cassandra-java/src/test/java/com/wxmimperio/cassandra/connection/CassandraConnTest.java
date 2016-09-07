package com.wxmimperio.cassandra.connection;

import com.datastax.driver.core.*;
import org.junit.Test;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class CassandraConnTest {

    @Test
    public void connTest() {
        Cluster cluster = CassandraConn.getCluster();

        Metadata metadata = cluster.getMetadata();

        for (Host host : metadata.getAllHosts()) {
            System.out.println("Host:" + host);
        }

        for (KeyspaceMetadata keyspaceMetadata : metadata.getKeyspaces()) {
            System.out.println("==========KeySpace============");
            System.out.println("KeySpace:" + keyspaceMetadata.getName());
            System.out.println("\t==========tables============");
            for (TableMetadata tableMetadata : keyspaceMetadata.getTables()) {
                System.out.println("\tTable:" + tableMetadata.getName());
            }
        }
    }
}
