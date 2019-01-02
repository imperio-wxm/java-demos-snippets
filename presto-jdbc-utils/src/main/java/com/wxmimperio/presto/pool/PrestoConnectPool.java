package com.wxmimperio.presto.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by weiximing.imperio on 2017/8/25.
 */
public class PrestoConnectPool {
    private final static Logger logger = LoggerFactory.getLogger(PrestoConnectPool.class);
    private GenericObjectPool<Connection> pool;
    private static final int CONNECT_POOL_SIZE = 3;

    private static class SingletonHolder {
        private static final PrestoConnectPool INSTANCE = new PrestoConnectPool();
    }

    public static PrestoConnectPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private PrestoConnectPool() {
        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
        conf.setMaxTotal(CONNECT_POOL_SIZE);
        conf.setTestOnReturn(true);
        ConnectFactory connectFactory = new ConnectFactory();
        pool = new GenericObjectPool<Connection>(connectFactory, conf);
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            pool.returnObject(connection);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getActive() {
        return pool.getNumActive();
    }
}
