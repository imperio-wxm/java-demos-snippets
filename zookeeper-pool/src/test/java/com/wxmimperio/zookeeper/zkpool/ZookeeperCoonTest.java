package com.wxmimperio.zookeeper.zkpool;

import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

import java.util.List;

/**
 * Created by weiximing.imperio on 2016/9/14.
 */
public class ZookeeperCoonTest {

    @Test
    public void getChildren() {
        ZookeeperConnPool zookeeperConnPool = ZookeeperConnPool.getInstance();

        ZkClient zkClient = zookeeperConnPool.getConnection();

        List<String> newTopics = zkClient.getChildren("/brokers/topics");

        System.out.println(newTopics.size());

        for (String topic : newTopics) {
            System.out.println(topic);
        }
        zookeeperConnPool.releaseConnection(zkClient);
    }
}
