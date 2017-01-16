package com.wxmimperio.kafka.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weiximing.imperio on 2016/11/28.
 */
public class CuratorWatcher {
    private CuratorFramework client;

    public CuratorWatcher(CuratorConnectPool curatorConnectPool) {

        this.client = curatorConnectPool.getConnection();

        System.out.println(client);
    }

    //子节点监听
    public PathChildrenCache addWatcher() throws Exception {
        final PathChildrenCache pccache = new PathChildrenCache(this.client, "/consumers/group_1/offsets/fourth_test", true);
        pccache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        pccache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                pathChildrenCacheEvent.getType();
                switch (pathChildrenCacheEvent.getType()) {//子节点的事件类型
                    case INITIALIZED:
                        System.out.println(pathChildrenCacheEvent.getInitialData());
                        break;
                    case CHILD_ADDED:
                        System.out.println(pathChildrenCacheEvent.getData());//通过pathChildrenCacheEvent，可以获取到节点相关的数据
                        break;
                    case CHILD_REMOVED:
                        System.out.println(pathChildrenCacheEvent.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        byte[] newOffset = pathChildrenCacheEvent.getData().getData();

                        Date nowTime = new Date();
                        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

                        System.out.println(time.format(nowTime) + " " + pathChildrenCacheEvent.getData().getPath() + "offset = " + new String(newOffset));
                        break;
                    default:
                        break;
                }
            }
        });
        return pccache;
    }
}
