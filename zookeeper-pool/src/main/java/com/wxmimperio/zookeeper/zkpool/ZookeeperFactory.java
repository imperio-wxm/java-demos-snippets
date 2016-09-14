package com.wxmimperio.zookeeper.zkpool;

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

    ZookeeperFactory() {
    }

    @Override
    public PooledObject<ZkClient> makeObject() throws Exception {
        ZkClient zkClient = new ZkClient("192.168.18.35:2181");
        return new DefaultPooledObject<ZkClient>(zkClient);
    }

    @Override
    public void destroyObject(PooledObject<ZkClient> pooledObject) throws Exception {
        ZkClient zkClient = pooledObject.getObject();
        if (zkClient != null) {
            zkClient.close();
            zkClient = null;
        }
    }

    @Override
    public boolean validateObject(PooledObject<ZkClient> pooledObject) {
        ZkClient zkClient = pooledObject.getObject();
        return zkClient != null;
    }

    @Override
    public void activateObject(PooledObject<ZkClient> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<ZkClient> pooledObject) throws Exception {

    }
}
