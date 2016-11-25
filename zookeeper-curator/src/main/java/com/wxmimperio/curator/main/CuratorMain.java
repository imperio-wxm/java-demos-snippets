package com.wxmimperio.curator.main;

import com.wxmimperio.curator.Watcher.CuratorWatcher;
import com.wxmimperio.curator.connect.CuratorConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import java.util.List;

/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorMain {
    public static void main(String[] args) {
        try {
            //CuratorWatcher curatorWatcher = new CuratorWatcher();
            //curatorWatcher.addWatcher();
            //curatorWatcher.NodeCache();
            //hread.sleep(Integer.MAX_VALUE);
            CuratorConnect curatorClient = new CuratorConnect();
            System.out.println(curatorClient.hashCode());

            CuratorConnect curatorClient1 = new CuratorConnect();
            System.out.println(curatorClient1.hashCode());
            /*CuratorFramework client = curatorClient.getCuratorConnect();
            List<String> topics = client.getChildren().forPath("/brokers/topics");
            for (String topic : topics) {
                System.out.println(topic);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
