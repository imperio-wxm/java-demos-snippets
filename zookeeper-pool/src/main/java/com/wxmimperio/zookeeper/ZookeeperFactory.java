package com.wxmimperio.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weiximing.imperio on 2016/8/24.
 */
public class ZookeeperFactory implements PooledObjectFactory<ZkClient> {
    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperFactory.class);

    public ZookeeperFactory() {
    }

    @Override
    public PooledObject<ZkClient> makeObject() throws Exception {
        ZkClient zkClient = new ZkClient("zkHost:zkPort");
        return new DefaultPooledObject<ZkClient>(zkClient);
    }

    @Override
    public void destroyObject(PooledObject<ZkClient> pooledObject) throws Exception {
        ZkClient zk = pooledObject.getObject();
        if (zk != null) {
            zk.close();
            zk = null;
        }
    }

    @Override
    public boolean validateObject(PooledObject<ZkClient> pooledObject) {
        ZkClient zk = pooledObject.getObject();
        return true;
    }

    @Override
    public void activateObject(PooledObject<ZkClient> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<ZkClient> pooledObject) throws Exception {

    }
}
