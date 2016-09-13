package com.wxmimperio.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weiximing.imperio on 2016/8/24.
 */
public class WatcherDefault implements Watcher {
    /**
     * zookeeper watcher
     */
    private static final Logger LOG = LoggerFactory.getLogger(WatcherDefault.class);

    @Override
    public void process(WatchedEvent event) {
        Event.KeeperState keeperState = event.getState();
        Event.EventType eventType = event.getType();
        LOG.info("watched：keeperState = " + keeperState + "  :  eventType = " + eventType);
    }
}
