package com.wxmimperio.curator;

import com.wxmimperio.curator.Watcher.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.junit.Test;

/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorWatcherTest {

    @Test
    public void warcherTest() throws Exception {
        CuratorWatcher curatorWatcher = new CuratorWatcher();
        curatorWatcher.addWatcher();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
