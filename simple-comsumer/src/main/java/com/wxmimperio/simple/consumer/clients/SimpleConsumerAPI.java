package com.wxmimperio.simple.consumer.clients;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.NotCoordinatorForConsumerException;
import kafka.common.OffsetMetadataAndError;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import org.apache.kafka.common.errors.OffsetOutOfRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by weiximing.imperio on 2016/9/19.
 */
public class SimpleConsumerAPI implements ISimpleConsumerAPI {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleConsumerAPI.class);
    /**
     * 所有的Kafka客户端地址
     * Key是地址，Value是端口号
     */
    private Map<String, Integer> brokers;

    private String topic;

    private String groupId;

    private int partition;

    /**
     * 上次读取的位置
     */
    private long offset;

    /**
     * 低级API消费者对象
     */
    private SimpleConsumer consumer;

    public SimpleConsumerAPI(Map<String, Integer> brokers, String topic, int partition, String groupId) {
        this.brokers = brokers;
        this.topic = topic;
        this.partition = partition;
        this.groupId = groupId;
    }

    /**
     * 取得broker的ip和port
     *
     * @param brokerAddress
     * @return
     */
    public Map<String, Integer> getBrokers(List<String> brokerAddress) {
        for (String address : brokerAddress) {
            String[] arr = address.split(":");
            System.out.println(arr[0] + "=" + Integer.parseInt(arr[1]));
            this.brokers.put(arr[0], Integer.parseInt(arr[1]));
        }
        return this.brokers;
    }

    /**
     * 获得SimpleConsumer
     *
     * @param broker
     * @param clientId
     * @return
     */
    public SimpleConsumer leaderSearcher(String broker, String clientId) {
        return this.leaderSearcher(broker, this.brokers.get(broker), clientId);
    }

    public SimpleConsumer leaderSearcher(String broker, int port, String clientId) {
        return this.leaderSearcher(broker, port, 100000, 64 * 1024, clientId);
    }

    private SimpleConsumer leaderSearcher(String broker, int port, int soTimeout, int bufferSize, String clientId) {
        return new SimpleConsumer(broker, port, soTimeout, bufferSize, clientId);
    }

    /**
     * 获取当前partition的offset
     *
     * @return
     */
    public long fetchOffset() {
        return this.fetchOffset(this.groupId);
    }

    public long fetchOffset(String groupId) {
        return this.fetchOffset(this.topic, groupId);
    }

    private long fetchOffset(String topic, String groupId) {
        List<TopicAndPartition> partitions = new ArrayList<TopicAndPartition>();
        TopicAndPartition partition = new TopicAndPartition(topic, this.partition);

        long retrievedOffset = 0;
        for (String broker : this.brokers.keySet()) {
            SimpleConsumer leaderSearcher = leaderSearcher(broker, "fetchOffsetClient");
            partitions.add(partition);

            //CurrentVersion为1，则metadata从kafka获取，为0表示从zookeeper获取
            OffsetFetchRequest fetchRequest = new OffsetFetchRequest(groupId, partitions, kafka.api.OffsetRequest.CurrentVersion(), 0, "fetchOffsetClient");
            OffsetFetchResponse fetchResponse = leaderSearcher.fetchOffsets(fetchRequest);
            OffsetMetadataAndError result = fetchResponse.offsets().get(partition);
            short offsetFetchErrorCode = result.error();

            if (offsetFetchErrorCode == ErrorMapping.NotCoordinatorForConsumerCode()) {
                throw new NotCoordinatorForConsumerException("NotCoordinatorForConsumerCode:16");
            } else if (offsetFetchErrorCode == ErrorMapping.OffsetMetadataTooLargeCode()) {
                throw new OffsetOutOfRangeException("OffsetMetadataTooLargeCode:12");
            } else if (offsetFetchErrorCode == ErrorMapping.OffsetOutOfRangeCode()) {

            } else {
                retrievedOffset = result.offset();
            }
            leaderSearcher.close();
        }
        return retrievedOffset;
    }

    /**
     * 获取leader的名字
     * 此时Kafka集群有可能在做leader选举，所以，没获取到的话就循环等待
     *
     * @return
     */
    public String getLeaderBrokerName() {
        PartitionMetadata partitionMeta = null;
        int times = 0;
        while (partitionMeta == null || partitionMeta.leader() == null) {
            if (times == 5) {
                throw new IllegalStateException("can not find leader at least 5 times!");
            } else if (times != 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                partitionMeta = this.getPartitionMetadata();
                times++;
            }
        }
        return partitionMeta.leader().host();
    }

    /**
     * 获取分区元数据信息
     *
     * @return
     */
    private PartitionMetadata getPartitionMetadata() {
        PartitionMetadata partitionMeta = null;
        List<String> topics = Collections.singletonList(this.topic);

        boolean find = false;
        for (String broker : this.brokers.keySet()) {
            SimpleConsumer leaderSearcher = leaderSearcher(broker, "leaderLookup");
            try {
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                TopicMetadataResponse resp = leaderSearcher.send(req);
                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        if (part.partitionId() == this.partition) {
                            partitionMeta = part;
                            find = true;
                            break;
                        }
                    }
                    if (find) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                leaderSearcher.close();
            }
            if (find) {
                break;
            }
        }
        return partitionMeta;
    }

    /**
     * 获取partition列表
     *
     * @return
     */
    public List<Integer> getPartitionList() {
        return this.getPartitionList(this.topic, this.brokers);
    }

    public List<Integer> getPartitionList(String topic, Map<String, Integer> brokers) {
        List<String> topics = Collections.singletonList(topic);
        List<Integer> partitionList = new ArrayList<Integer>();

        for (String broker : brokers.keySet()) {
            SimpleConsumer leaderSearcher = this.leaderSearcher(broker, "leaderLookup");
            try {
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                TopicMetadataResponse resp = leaderSearcher.send(req);
                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        partitionList.add(part.partitionId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                leaderSearcher.close();
            }
        }
        return partitionList;
    }

    /**
     * 根据leader的地址初始化消费者
     *
     * @param leaderBrokerName
     */
    private void init(String leaderBrokerName) {
        this.consumer = this.leaderSearcher(leaderBrokerName, this.getPort(leaderBrokerName), "initClient");
        long fetchOffset = fetchOffset();

        if (this.offset < 0) {

            System.out.println("log size==========" + this.getLastOffset());
            System.out.println("fetch offset==========" + fetchOffset);

            //1.如果fetch offset小于log size，表示第一次运行或者有消息丢失
            if (fetchOffset < this.getLastOffset()) {
                //2.第一次运行。默认offset为-1
                if (fetchOffset(this.topic) == -1) {
                    this.offset = this.getLastOffset();
                } else {
                    //3.表示缓冲部分有数据丢失，从丢失处开始读数据
                    this.offset = fetchOffset;
                }
            } else {
                //2.如果fetch offset不小于当前log size，则表示没有数据丢失，offset为最新
                this.offset = this.getLastOffset();
            }
        }
    }

    public long getLastOffset() {
        TopicAndPartition topicAndPartition = new TopicAndPartition(this.topic, this.partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
        OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), "getLastOffsetClient");
        OffsetResponse response = this.consumer.getOffsetsBefore(request);
        if (response.hasError()) {
            return 0;
        }
        long[] offsets = response.offsets(this.topic, this.partition);
        return offsets[0];
    }

    private int getPort(String broker) {
        return this.brokers.get(broker);
    }
}
