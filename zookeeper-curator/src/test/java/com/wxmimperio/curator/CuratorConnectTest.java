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
        byte[] topicInfo = client.getData().forPath("/brokers/topics/third_test");
        System.out.println(new String(topicInfo));

        List<String> topicList = client.getChildren().forPath("/brokers/topics");
        for (String topic : topicList) {
            System.out.println(topic);
        }

        List<String> groupList = client.getChildren().forPath("/consumers/group_1/offsets");
        System.out.println(groupList.size());
        for (String group : groupList) {
            System.out.println("-----" + group);
        }

        byte[] offsetInfo = client.getData().forPath("/consumers/group_1/offsets/third_test/0");
        System.out.println(new String(offsetInfo));

        byte[] partitionInfo = client.getData().forPath("/brokers/topics/third_test/partitions/0/state");
        System.out.println(new String(partitionInfo));

        List<String> brokerList = client.getChildren().forPath("/brokers/ids");
        System.out.println(brokerList.size());
        for (String broker : brokerList) {
            System.out.println(broker);
        }

        byte[] brokerInfo = client.getData().forPath("/brokers/ids/0");
        System.out.println(new String(brokerInfo));

        byte[] controllerInfo = client.getData().forPath("/controller");
        System.out.println(new String(controllerInfo));

        byte[] topicConfig = client.getData().forPath("/config/topics/third_test");
        System.out.println(new String(topicConfig));

        List<String> configList = client.getChildren().forPath("/config/topics/third_test");
        System.out.println(configList.size());
        for (String config : configList) {
            System.out.println(config);
        }

        byte[] controllerConfig = client.getData().forPath("/controller");
        System.out.println(new String(controllerConfig));

        ///consumers/[groupId]/ids  group_1_sh-weiximing-1479364957567-c2535729
        List<String> consumerIdList = client.getChildren().forPath("/consumers/group_1/ids");
        for (String consumerId : consumerIdList) {
            System.out.println(consumerId);
        }

        /*byte[] consumerInfo = client.getData().forPath("/consumers/group_1/ids/group_1_sh-weiximing-1479364957567-c2535729");
        System.out.println(new String(consumerInfo));*/

        byte[] consumerOwner = client.getData().forPath("/consumers/group_1/owners/fourth_test/1");
        //System.out.println(new String(consumerOwner));
        String[] consumerOwners = new String(consumerOwner).split("\\n");
        System.out.println(Arrays.toString(consumerOwners));

    }
}
