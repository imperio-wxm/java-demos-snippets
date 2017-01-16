package com.wxmimperio.kafka.curator;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by weiximing.imperio on 2016/11/15.
 */
public class CuratorFactory implements PooledObjectFactory<CuratorFramework> {
    private static final Logger LOG = LoggerFactory.getLogger(CuratorFactory.class);

    public CuratorFactory() {
    }

/*    @Resource(name = "globalConfig")
    private GlobalConfig globalConfig;*/

    @Override
    public PooledObject<CuratorFramework> makeObject() throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.18.74:2181")
                .connectionTimeoutMs(5000)   //连接超时时间
                .sessionTimeoutMs(3000)      //会话超时时间
                .retryPolicy(new RetryNTimes(3, 3000)).maxCloseWaitMs(500)
                .build();
        curatorFramework.start();
        return new DefaultPooledObject<CuratorFramework>(curatorFramework);
    }

    @Override
    public void destroyObject(PooledObject<CuratorFramework> pooledObject) throws Exception {
        CuratorFramework curatorFramework = pooledObject.getObject();
        if (curatorFramework != null) {
            curatorFramework.close();
        }
        LOG.info("CuratorFramework Destroy!");
    }

    @Override
    public boolean validateObject(PooledObject<CuratorFramework> pooledObject) {
        CuratorFramework curatorFramework = pooledObject.getObject();
        return curatorFramework.getState() == CuratorFrameworkState.STARTED;
    }

    @Override
    public void activateObject(PooledObject<CuratorFramework> pooledObject) throws Exception {
        CuratorFramework curatorFramework = pooledObject.getObject();
        if (curatorFramework.getState() == CuratorFrameworkState.LATENT) {
            curatorFramework.start();
        }
    }

    @Override
    public void passivateObject(PooledObject<CuratorFramework> pooledObject) throws Exception {

    }
}
