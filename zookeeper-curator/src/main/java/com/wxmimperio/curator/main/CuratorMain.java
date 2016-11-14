package com.wxmimperio.curator.main;

import com.wxmimperio.curator.Watcher.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorMain {
    public static void main(String[] args) {
        try {
            CuratorWatcher curatorWatcher = new CuratorWatcher();
            curatorWatcher.addWatcher().start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
