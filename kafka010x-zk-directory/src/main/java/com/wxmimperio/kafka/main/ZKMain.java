package com.wxmimperio.kafka.main;

import com.wxmimperio.kafka.curator.CuratorConnectPool;
import com.wxmimperio.kafka.utils.CuratorUtils;
import kafka.admin.AdminClient;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.NotCoordinatorForConsumerException;
import kafka.common.OffsetMetadataAndError;
import kafka.common.TopicAndPartition;
import kafka.coordinator.GroupOverview;
import kafka.coordinator.GroupSummary;
import kafka.coordinator.MemberSummary;
import kafka.javaapi.OffsetFetchRequest;
import kafka.javaapi.OffsetFetchResponse;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import scala.Tuple2;
import scala.collection.JavaConversions;

import java.util.*;
import java.util.List;

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


       /* for (GroupOverview go : JavaConversions.asJavaCollection(adminClient.listAllGroupsFlattened())) {
            System.out.println("group_id = " + go.groupId());

            List<scala.collection.immutable.List<AdminClient.ConsumerSummary>> consumerGroup = JavaConversions.seqAsJavaList(adminClient.describeConsumerGroup(go.groupId()).toList());
            for (scala.collection.immutable.List<AdminClient.ConsumerSummary> consumerSummaryList : consumerGroup) {
                System.out.println(consumerSummaryList.size());
                for (AdminClient.ConsumerSummary consumerSummary : JavaConversions.seqAsJavaList(consumerSummaryList.toList())) {
                    for (TopicPartition topicPartition : JavaConversions.seqAsJavaList(consumerSummary.assignment().toList())) {
                        System.out.println(topicPartition.topic());
                        System.out.println(topicPartition.partition());
                    }
                }
            }
        }*/

        /*for (GroupOverview groupOverview : JavaConversions.asJavaCollection(adminClient.listAllConsumerGroupsFlattened())) {
            System.out.println("group_id = " + groupOverview.groupId());

            List<scala.collection.immutable.List<AdminClient.ConsumerSummary>> groupSummary = JavaConversions.seqAsJavaList(adminClient.describeConsumerGroup(groupOverview.groupId()).toList());
            for(scala.collection.immutable.List<AdminClient.ConsumerSummary> consumerSummaryList : groupSummary) {
                for(AdminClient.ConsumerSummary consumerSummary : JavaConversions.seqAsJavaList(consumerSummaryList.toList())) {
                    System.out.println(consumerSummary.assignment());
                }
            }
        }

        Set<String> topicList = new HashSet<>();

        for (GroupOverview go : JavaConversions.asJavaCollection(adminClient.listAllGroupsFlattened())) {
            System.out.println("group_id = " + go.groupId());

            List<scala.collection.immutable.List<AdminClient.ConsumerSummary>> consumerGroup = JavaConversions.seqAsJavaList(adminClient.describeConsumerGroup(go.groupId()).toList());
            for (scala.collection.immutable.List<AdminClient.ConsumerSummary> consumerSummaryList : consumerGroup) {
                System.out.println(consumerSummaryList.size());
                for (AdminClient.ConsumerSummary consumerSummary : JavaConversions.seqAsJavaList(consumerSummaryList.toList())) {
                    for (TopicPartition topicPartition : JavaConversions.seqAsJavaList(consumerSummary.assignment().toList())) {
                        System.out.println(topicPartition.topic());
                        topicList.add(topicPartition.topic());
                        System.out.println(topicPartition.partition());
                    }
                }
            }
        }

        System.out.println(new ArrayList<>(topicList));

        for (GroupOverview go : JavaConversions.asJavaCollection(adminClient.listAllGroupsFlattened())) {
            System.out.println("group_id = " + go.groupId());

            List<scala.collection.immutable.List<AdminClient.ConsumerSummary>> consumerGroup = JavaConversions.seqAsJavaList(adminClient.describeConsumerGroup(go.groupId()).toList());
            for (scala.collection.immutable.List<AdminClient.ConsumerSummary> consumerSummaryList : consumerGroup) {
                System.out.println(consumerSummaryList.size());
                for (AdminClient.ConsumerSummary consumerSummary : JavaConversions.seqAsJavaList(consumerSummaryList.toList())) {
                    System.out.println(consumerSummary.clientId());
                    System.out.println(consumerSummary.memberId());
                    System.out.println(consumerSummary.clientHost());
                }
            }
        }*/

        /*for (Tuple2<Node, scala.collection.immutable.List<GroupOverview>> tuple : JavaConversions.asJavaCollection(adminClient.listAllGroups())) {
            System.out.println(tuple._1());
            System.out.println(tuple._2());
            for (GroupOverview groupOverview : JavaConversions.seqAsJavaList(tuple._2().toList())) {
                System.out.println(groupOverview.groupId());
            }
        }*/

       /* for (GroupOverview go : JavaConversions.asJavaCollection(adminClient.listAllGroupsFlattened())) {
            System.out.println("group_id = " + go.groupId());

            List<scala.collection.immutable.List<AdminClient.ConsumerSummary>> consumerGroup = JavaConversions.seqAsJavaList(adminClient.describeConsumerGroup(go.groupId()).toList());
            for (scala.collection.immutable.List<AdminClient.ConsumerSummary> consumerSummaryList : consumerGroup) {
                System.out.println(consumerSummaryList.size());
                for (AdminClient.ConsumerSummary consumerSummary : JavaConversions.seqAsJavaList(consumerSummaryList.toList())) {
                    System.out.println(consumerSummary.memberId());
                    for (TopicPartition topicPartition : JavaConversions.seqAsJavaList(consumerSummary.assignment().toList())) {
                        System.out.println(topicPartition);
                    }
                }
            }
        }*/

        List<String> brokers = new ArrayList<>();
        brokers.add("192.168.18.74:9092");

        /*long logSize = getLastOffset("192.168.18.74", 9092, "test_1", 0, kafka.api.OffsetRequest.LatestTime());
        System.out.println(logSize);*/

        long offset = fetchOffset("192.168.18.74", 9092, "group_1", "test_2", 0);

        System.out.println(offset);

        adminClient.close();
    }

    public static long getLastOffset(String leaderHost, int port, String topic, int partitionId, long time) {

        System.out.println(kafka.api.OffsetRequest.CurrentVersion());

        SimpleConsumer consumer =
                new SimpleConsumer(leaderHost, port, 100 * 1000, 64 * 1024, "findLastOffset" + topic + partitionId);


        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partitionId);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> reqInfo = new HashMap<>();
        reqInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(time, 1));
        OffsetRequest req =
                new OffsetRequest(reqInfo, kafka.api.OffsetRequest.CurrentVersion(), "offsetRequest" + topic + partitionId);
        OffsetResponse resp = consumer.getOffsetsBefore(req);
        consumer.close();
        //获取失败
        if (resp.hasError()) {
            short errorCode = resp.errorCode(topic, partitionId);
            System.out.println(("(Topic=" + topic + ", Partition=" + partitionId +
                    ", Time=" + time + ") 查询 offset 失败，失败代码为：" + errorCode +
                    ",  失败原因为: " + ErrorMapping.exceptionFor(errorCode)));
            return -1;
        }
        //里面只有1个数据
        long[] offsets = resp.offsets(topic, partitionId);
        return offsets[0];
    }

    /**
     * 获取当前topic-partition 已经commit的最后一个offset
     *
     * @param leaderHost
     * @param port
     * @param group
     * @param topic
     * @param partitionId
     * @return
     */
    public static long fetchOffset(String leaderHost, int port, String group, String topic, int partitionId) {
        List<TopicAndPartition> partitions = new ArrayList<TopicAndPartition>();
        TopicAndPartition partition = new TopicAndPartition(topic, partitionId);

        SimpleConsumer consumer =
                new SimpleConsumer(leaderHost, port, 100 * 1000, 64 * 1024, "fetchOffset" + topic + partitionId);

        long retrievedOffset;
        partitions.add(partition);
        //CurrentVersion为1，则metadata从kafka获取，为0表示从zookeeper获取
        OffsetFetchRequest fetchRequest = new OffsetFetchRequest(group, partitions, (short) 1, 0, "getOffsetClient");
        OffsetFetchResponse fetchResponse = consumer.fetchOffsets(fetchRequest);
        OffsetMetadataAndError result = fetchResponse.offsets().get(partition);

        consumer.close();
        short offsetFetchErrorCode = result.error();
        if (offsetFetchErrorCode == ErrorMapping.NotCoordinatorForConsumerCode()) {
            System.out.println("NotCoordinatorForConsumerCode");
            throw new NotCoordinatorForConsumerException("NotCoordinatorForConsumerCode:16");
        } else {
            retrievedOffset = result.offset();
        }
        return retrievedOffset;
    }
}
