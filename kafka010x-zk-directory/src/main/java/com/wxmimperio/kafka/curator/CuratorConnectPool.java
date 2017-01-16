package com.wxmimperio.kafka.curator;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weiximing.imperio on 2016/11/15.
 */
public class CuratorConnectPool {
    private static final Logger LOG = LoggerFactory.getLogger(CuratorConnectPool.class);
    private GenericObjectPool<CuratorFramework> curatorPool;
    private static final int CURATOR_CONN_POOL_COUNT = 8;

    public CuratorConnectPool() {
        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
        conf.setMaxTotal(CURATOR_CONN_POOL_COUNT);
        CuratorFactory curatorFactory = new CuratorFactory();
        this.curatorPool = new GenericObjectPool<CuratorFramework>(curatorFactory, conf);
    }

    public CuratorFramework getConnection() {
        CuratorFramework curatorFramework = null;
        try {
            curatorFramework = this.curatorPool.borrowObject();

            LOG.info("当前有" + this.curatorPool.getNumActive() + "个活跃Curator连接在连接池中.");
            LOG.info("当前有" + this.curatorPool.listAllObjects().size() + "个Curator连接在连接池中.");
            LOG.info("当前有" + this.curatorPool.getNumWaiters() + "个等待Curator连接在连接池中.");
            LOG.info("当前有" + this.curatorPool.getDestroyedCount() + "个销毁Curator连接在连接池中.");
        } catch (Exception e) {
            this.closeConnection(curatorFramework);
            e.printStackTrace();
        }
        return curatorFramework;
    }

    public void releaseConnection(CuratorFramework curatorFramework) {
        try {
            this.curatorPool.returnObject(curatorFramework);
        } catch (Exception e) {
            this.closeConnection(curatorFramework);
            e.printStackTrace();
        }
    }

    public void closeConnection(CuratorFramework curatorFramework) {
        try {
            if (curatorFramework != null) {
                curatorFramework.close();
            }
        } catch (Exception e) {
            curatorFramework = null;
            e.printStackTrace();
        }
    }
}
