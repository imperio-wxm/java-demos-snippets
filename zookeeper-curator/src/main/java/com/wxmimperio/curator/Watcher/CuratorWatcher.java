package com.wxmimperio.curator.Watcher;

import com.wxmimperio.curator.connect.CuratorConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;


/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorWatcher {
    private CuratorFramework client;

    public CuratorWatcher() {
        CuratorConnect curatorConnect = new CuratorConnect();
        this.client = curatorConnect.getCuratorConnect();
    }

    //子节点监听
    public PathChildrenCache addWatcher() throws Exception {
        final PathChildrenCache pccache = new PathChildrenCache(this.client, "/consumers/group_1/offsets/fourth_test", true);
        pccache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        System.out.println(pccache.getCurrentData().size());
        pccache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                pathChildrenCacheEvent.getType();
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

    //节点监听
    public NodeCache NodeCache() throws Exception{
        //节点监听
        final NodeCache cache = new NodeCache(this.client,"/consumers/group_1/offsets/fourth_test/0");
        cache.start();
        cache.getListenable().addListener(new NodeCacheListener() {//监听对象
            @Override
            public void nodeChanged() throws Exception {
                byte[] newOffset = cache.getCurrentData().getData();
                System.out.println(cache.getCurrentData().getPath() + "offset = " + new String(newOffset));
            }
        });
        return cache;
    }
}
