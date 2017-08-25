package com.wxmimperio.presto.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by weiximing.imperio on 2017/8/25.
 */
public class ConnectFactory implements PooledObjectFactory<Connection> {
    private final static Logger logger = LoggerFactory.getLogger(ConnectFactory.class);

    private static final String JDBC_DRIVER = "com.facebook.presto.jdbc.PrestoDriver";
    private static final String DB_URL = "jdbc:presto:///cassandra/rtc";
    private static final String USER = "presto";
    private static final String PASS = "";


    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        return new DefaultPooledObject<Connection>(connection);
    }

    @Override
    public void destroyObject(PooledObject<Connection> pooledObject) throws Exception {
        if (pooledObject instanceof Connection) {
            Connection connection = pooledObject.getObject();
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public boolean validateObject(PooledObject<Connection> pooledObject) {
        if (pooledObject instanceof Connection) {
            Connection connection = pooledObject.getObject();
            if (connection != null) {
                try {
                    return connection.isClosed();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void activateObject(PooledObject<Connection> pooledObject) throws Exception {
    }

    @Override
    public void passivateObject(PooledObject<Connection> pooledObject) throws Exception {
        if (pooledObject instanceof Connection) {
            Connection connection = pooledObject.getObject();
            if (connection != null) {
                connection.clearWarnings();
            }
        }
    }
}
