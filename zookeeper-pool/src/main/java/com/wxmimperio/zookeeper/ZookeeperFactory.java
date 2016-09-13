package com.wxmimperio.zookeeper;


import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weiximing.imperio on 2016/8/24.
 */
public class ZookeeperFactory implements PooledObjectFactory<ZooKeeper> {
    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperFactory.class);

    public ZookeeperFactory() {
    }

    @Override
    public PooledObject<ZooKeeper> makeObject() throws Exception {
        WatcherDefault watcherDefault = new WatcherDefault();
        ZooKeeper zk = new ZooKeeper("", 1000, watcherDefault);
        return new DefaultPooledObject<ZooKeeper>(zk);
    }

    @Override
    public void destroyObject(PooledObject<ZooKeeper> pooledObject) throws Exception {
        ZooKeeper zk = pooledObject.getObject();
        if (zk != null) {
            zk.close();
            zk = null;
        }
    }

    @Override
    public boolean validateObject(PooledObject<ZooKeeper> pooledObject) {
        ZooKeeper zk = pooledObject.getObject();
        return zk.getState().isConnected() && zk.getState().isAlive();
    }

    @Override
    public void activateObject(PooledObject<ZooKeeper> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<ZooKeeper> pooledObject) throws Exception {

    }
}
