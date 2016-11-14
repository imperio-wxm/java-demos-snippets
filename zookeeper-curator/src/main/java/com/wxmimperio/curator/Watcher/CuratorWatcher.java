package com.wxmimperio.curator.Watcher;

import com.wxmimperio.curator.connect.CuratorConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;


/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorWatcher {
    private CuratorFramework client;

    public CuratorWatcher() {
        CuratorConnect curatorConnect = new CuratorConnect();
        this.client = curatorConnect.getCuratorConnect();
    }

    public PathChildrenCache addWatcher() throws Exception {
        final PathChildrenCache pccache = new PathChildrenCache(this.client, "/consumers/group_1/offsets/fourth_test", true);
        pccache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        System.out.println(pccache.getCurrentData().size());
        pccache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()) {//子节点的事件类型
                    case CHILD_ADDED:
                        System.out.println(pathChildrenCacheEvent.getData());//通过pathChildrenCacheEvent，可以获取到节点相关的数据
                        break;
                    case CHILD_REMOVED:
                        System.out.println(pathChildrenCacheEvent.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        byte[] newOffset = pathChildrenCacheEvent.getData().getData();
                        System.out.println(pathChildrenCacheEvent.getData().getPath() + "offset = " + new String(newOffset));
                        break;
                    default:
                        break;
                }
            }
        });
        return pccache;
    }
}
