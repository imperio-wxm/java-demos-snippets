package com.wxmimperio.cassandra.connection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
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
            System.out.println("KeySpace:" + keyspaceMetadata.getName());
        }
    }
}
