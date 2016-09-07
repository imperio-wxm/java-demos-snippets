package com.wxmimperio.cassandra.connection;

import com.datastax.driver.core.Cluster;

import java.util.Collections;
import java.util.List;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class CassandraConn {

    public static Cluster getCluster() {
        Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
        return cluster;
    }
}
