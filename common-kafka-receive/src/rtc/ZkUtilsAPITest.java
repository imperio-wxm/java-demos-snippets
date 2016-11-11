package com.sdo.dw.rtc.utils;

import kafka.common.TopicAndPartition;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import scala.Tuple2;

import java.util.*;


/**
 * Created by weiximing.imperio on 2016/11/10.
 */
public class ZkUtilsAPITest {

    private ZkClient getZkClient() {
        return new ZkClient("192.168.18.74:2181",10000,10000);
    }

    private void closeZkClient(ZkClient zkClient) {
        if (zkClient != null) {
            zkClient.close();
        }
    }

    @Test
    public void zkUtilsAPI() {
        ZkClient zkClient = getZkClient();
        zkClient.setZkSerializer(new MyZkSerializer());

        //allTopics
        List<String> allTopics = ZkUtilsAPI.getAllTopics(zkClient);
        System.out.println(Arrays.toString(allTopics.toArray()));

        //getAllPartitions
        Set<TopicAndPartition> topicAndPartitions = ZkUtilsAPI.getAllPartitions(zkClient);
        for (TopicAndPartition topicAndPartition : topicAndPartitions) {
            System.out.println("topic = " + topicAndPartition.topic() + " partition = " + topicAndPartition.partition());
        }

        //getBrokerInfo
        /*List<Broker> brokers = ZkUtilsAPI.getBrokerInfo(zkClient, 0);
        System.out.println(brokers);
        for (Broker broker : brokers) {
            System.out.println("broker: id=" + broker.id() + " host=" + broker.host() + " " + broker.toString());
        }*/

        //getConsumersPerTopic
        /*Map map = ZkUtilsAPI.getConsumersPerTopic(zkClient, "group_1", true);
        System.out.println(map);
        for (Object key : map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key));
        }*/

        //getConsumersInGroup
        List<String> consumersInGroup = ZkUtilsAPI.getConsumersInGroup(zkClient, "group_1");
        System.out.println(Arrays.toString(consumersInGroup.toArray()));

        /*List<String> topics = new ArrayList<String>();
        topics.add("test");
        Map a = ZkUtilsAPI.getPartitionsForTopics(zkClient,topics);*/

        System.out.println(zkClient.getChildren("/consumers/group_1/offsets/third_test"));

        System.out.println(zkClient.readData("/brokers/topics/third_test/partitions/0/state", new Stat()));

        closeZkClient(zkClient);
    }
}
