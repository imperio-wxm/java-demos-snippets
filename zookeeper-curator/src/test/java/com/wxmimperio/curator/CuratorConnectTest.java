package com.wxmimperio.curator;

import com.wxmimperio.curator.connect.CuratorConnect;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by weiximing.imperio on 2016/11/14.
 */
public class CuratorConnectTest {

    @Test
    public void connectTest() throws Exception {
        CuratorConnect curatorConnect = new CuratorConnect();
        CuratorFramework client = curatorConnect.getCuratorConnect();
        System.out.println(client.getChildren().forPath("/brokers/topics"));

        List<String> topicList = client.getChildren().forPath("/brokers/topics");
        for (String topic : topicList) {
            System.out.println(topic);
        }
        byte[]  newOffset = client.getData().forPath("/consumers/group_1/offsets/fourth_test/0");
        System.out.println("offset = " + new String(newOffset));
        curatorConnect.closeCuratorConnect(client);
    }
}
