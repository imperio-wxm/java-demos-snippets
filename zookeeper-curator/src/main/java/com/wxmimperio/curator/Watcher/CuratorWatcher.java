package com.wxmimperio.curator.Watcher;

import com.wxmimperio.curator.connect.CuratorConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
        final PathChildrenCache pccache = new PathChildrenCache(this.client, "/brokers/topics", true);
        pccache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        List<ChildData> childDataList = pccache.getCurrentData();
        System.out.println(childDataList.size());
        for (ChildData childData : childDataList) {
            System.out.println(new String(childData.getData()));
            String path[] = childData.getPath().split("/");
            System.out.println(path[path.length - 1]);
        }
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
    public NodeCache NodeCache() throws Exception {
        /**
         * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
         */
        ExecutorService pool = Executors.newFixedThreadPool(5);

        //节点监听
        final NodeCache cache = new NodeCache(this.client, "/consumers");
        cache.start();
        cache.getListenable().addListener(new NodeCacheListener() {//监听对象
            @Override
            public void nodeChanged() throws Exception {
                byte[] newOffset = cache.getCurrentData().getData();
                System.out.println(cache.getCurrentData().getPath() + "offset = " + new String(newOffset));
            }
        }, pool);
        return cache;
    }
}
