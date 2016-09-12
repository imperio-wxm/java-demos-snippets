package com.wxmimperio.cassandra.connection;

import com.datastax.driver.core.Cluster;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class CassandraConn {

    public static Cluster getCluster() {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoints("10.1.11.228").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cluster;
    }
}
