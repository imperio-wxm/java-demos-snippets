package com.wxmimperio.zookeeper;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weiximing.imperio on 2016/8/25.
 */
public class ZookeeperConnPool {
    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperConnPool.class);
    private GenericObjectPool<ZooKeeper> pool;
    private static final int ZOOKEEPER_CONN_POOL_COUNT = 5;

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
        pool = new GenericObjectPool<ZooKeeper>(zookeeperFactory, conf);
    }

    /**
     * 获取zookeeper连接
     *
     * @return
     * @throws Exception
     */
    public ZooKeeper getConnection() {
        ZooKeeper zk = null;
        try {
            zk = pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zk;
    }

    /**
     * zookeeper连接放回连接池
     *
     * @param zk
     */
    public void releaseConnection(ZooKeeper zk) {
        try {
            pool.returnObject(zk);
        } catch (Exception e) {
            if (zk != null) {
                try {
                    zk.close();
                    zk = null;
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * zookeeper 销毁连接
     * @param zk
     */
    public void closeConnection(ZooKeeper zk) {
        try {
           if (zk != null) {
               zk.close();
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
