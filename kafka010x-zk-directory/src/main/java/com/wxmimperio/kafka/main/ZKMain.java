package com.wxmimperio.kafka.main;

import com.wxmimperio.kafka.curator.CuratorConnectPool;
import com.wxmimperio.kafka.utils.CuratorUtils;
import kafka.admin.AdminClient;
import kafka.coordinator.GroupOverview;
import kafka.coordinator.GroupSummary;
import kafka.coordinator.MemberSummary;
import org.apache.curator.framework.CuratorFramework;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.requests.DescribeGroupsResponse;
import org.apache.zookeeper.data.Stat;
import kafka.admin.ConsumerGroupCommand;
import scala.Serializable;
import scala.collection.JavaConversions;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by weiximing.imperio on 2017/1/16.
 */
public class ZKMain {

    public static void main(String args[]) {
        CuratorConnectPool curatorConnectPool = new CuratorConnectPool();
        CuratorFramework client = curatorConnectPool.getConnection();

        //获取根目录孩子节点
        getZookeeperRoot(client);
        getBrokerListAndInfo(client);
        curatorConnectPool.releaseConnection(client);


        try {
            getGroupList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getZookeeperRoot(CuratorFramework client) {
        String path = "/";
        List<String> rootC = CuratorUtils.getChildNode(client, path);
        System.out.println(rootC);
    }

    private static void getBrokerListAndInfo(CuratorFramework client) {
        String path = "/brokers/ids";
        List<String> brokers = CuratorUtils.getChildNode(client, path);

        /**
         * {
         *  "jmx_port":-1, //监听端口
         *  "timestamp":"1484529625173", //启动时间
         *  "endpoints":["PLAINTEXT://192.168.18.74:9092"],//监听列表
         *  "host":"192.168.18.74",//地址
         *  "version":3,//版本
         *  "port":9092//端口
         *  }
         */
        for (String str : brokers) {
            String infoPath = "/brokers/ids/" + str;
            String brokerInfo = CuratorUtils.readDataMaybeNull(client, infoPath);
            System.out.println(brokerInfo);
        }
    }

    private static void getGroupList() throws Exception {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "192.168.18.74:9092");
        AdminClient adminClient = AdminClient.create(props);


        for (GroupOverview go : JavaConversions.asJavaCollection(adminClient.listAllGroupsFlattened())) {
            System.out.println("group_id = " + go.groupId());
            GroupSummary groupSummary = adminClient.describeGroup(go.groupId());
            for (MemberSummary memberSummary : JavaConversions.seqAsJavaList(groupSummary.members())) {
                System.out.println(memberSummary.clientId());
            }
            List<scala.collection.immutable.List<AdminClient.ConsumerSummary>> consumerGroup = JavaConversions.seqAsJavaList(adminClient.describeConsumerGroup(go.groupId()).toList());
            for (scala.collection.immutable.List<AdminClient.ConsumerSummary> consumerSummaryList : consumerGroup) {
                for (AdminClient.ConsumerSummary consumerSummary : JavaConversions.seqAsJavaList(consumerSummaryList.toList())) {
                    for (TopicPartition topicPartition : JavaConversions.seqAsJavaList(consumerSummary.assignment().toList())) {
                        System.out.println(topicPartition.topic());
                        System.out.println(topicPartition.partition());
                    }
                }
            }
        }
        adminClient.close();
    }
}
