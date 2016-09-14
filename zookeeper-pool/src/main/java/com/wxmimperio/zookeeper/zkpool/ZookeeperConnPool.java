package com.wxmimperio.zookeeper.zkpool;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weiximing.imperio on 2016/8/25.
 */
public class ZookeeperConnPool {
    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperConnPool.class);
    private GenericObjectPool<ZkClient> pool;
    private static final int ZOOKEEPER_CONN_POOL_COUNT = 1;

    private static class SingletonHolder {
        private static final ZookeeperConnPool INSTANCE = new ZookeeperConnPool();
    }

    public static ZookeeperConnPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ZookeeperConnPool() {
        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
        conf.setMaxTotal(ZOOKEEPER_CONN_POOL_COUNT);
        ZookeeperFactory zookeeperFactory = new ZookeeperFactory();
        pool = new GenericObjectPool<ZkClient>(zookeeperFactory, conf);
    }

    /**
     * 获取zookeeper连接
     *
     * @return
     * @throws Exception
     */
    public ZkClient getConnection() {
        ZkClient zkClient = null;
        try {
            zkClient = pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zkClient;
    }

    /**
     * zookeeper连接放回连接池
     *
     * @param zkClient
     */
    public void releaseConnection(ZkClient zkClient) {
        try {
            pool.returnObject(zkClient);
        } catch (Exception e) {
            if (zkClient != null) {
                zkClient.close();
            }
        }
    }

    /**
     * zkpool 销毁连接
     * @param zkClient
     */
    public void closeConnection(ZkClient zkClient) {
        try {
           if (zkClient != null) {
               zkClient.close();
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getActive() {
        return pool.getNumActive();
    }
}
