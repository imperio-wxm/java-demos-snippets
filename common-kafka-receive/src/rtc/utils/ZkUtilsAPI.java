package com.sdo.dw.rtc.utils;

import kafka.cluster.Broker;
import kafka.common.TopicAndPartition;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.data.Stat;
import scala.Tuple2;
import scala.collection.JavaConversions;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by weiximing.imperio on 2016/11/11.
 */
public class ZkUtilsAPI {

    private static Logger logger = LogManager.getLogger("ZkUtilsAPI");

    public static Set<TopicAndPartition> getAllPartitions(ZkClient zkClient) {
        return JavaConversions.setAsJavaSet(ZkUtils.getAllPartitions(zkClient));
    }

    public static List<String> getAllTopics(ZkClient zkClient) {
        return JavaConversions.seqAsJavaList(ZkUtils.getAllTopics(zkClient));
    }

    public static List<Broker> getBrokerInfo(ZkClient zkClient, int brokerId) {
        System.out.println(ZkUtils.getBrokerInfo(zkClient, brokerId).get().id());
        return JavaConversions.seqAsJavaList(ZkUtils.getBrokerInfo(zkClient, brokerId).toList().toSeq());
    }

    public static Map getConsumersPerTopic(ZkClient zkClient, String group, Boolean excludeInternalTopics) {
        return JavaConversions.mutableMapAsJavaMap(ZkUtils.getConsumersPerTopic(zkClient, group, excludeInternalTopics));
    }

    //获取此group启动的consumer id
    public static List<String> getConsumersInGroup(ZkClient zkClient, String group) {
        return JavaConversions.seqAsJavaList(ZkUtils.getConsumersInGroup(zkClient, group));
    }

    //deletePartition

    //getPartitionsUndergoingPreferredReplicaElection

    //updatePartitionReassignmentData

    //getPartitionReassignmentZkData

    //parseTopicsData

    //parsePartitionReassignmentData

    //parsePartitionReassignmentDataWithoutDedup

    //getPartitionsBeingReassigned

    //getPartitionsForTopics
    /*public static Map getPartitionsForTopics(ZkClient zkClient,Iterator<String> topics) {


        return JavaConversions.mutableMapAsJavaMap(
                ZkUtils.getPartitionsForTopics(zkClient, topics));
    }*/

    //getPartitionAssignmentForTopics

    //getReplicaAssignmentForTopics

    //getPartitionLeaderAndIsrForTopics

    //getPartitionLeaderAndIsrForTopics

    //pathExists

    //getChildrenParentMayNotExist

    //getChildren

    //readDataMaybeNull

    //readData

    /*public static Tuple2<String, Stat> readData(String path, Stat stat) {
        System.out.println(ZkUtils.readData(path,stat));
        return zkClient..readData(zkClient,stat);
    }*/
}
