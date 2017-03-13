/*
package com.wxmimperio.kafka.simpleconsumer.common;

import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.*;
import kafka.common.OffsetAndMetadata;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

*/
/**
 * Created by wxmimperio on 2016/12/4.
 *//*

public class SimpleConsumerAPI {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleConsumerAPI.class);

    //topic名称
    private String topic;

    //分区号
    private int partition;

    //所有的Kafka客户端地址,Key是地址，Value是端口号
    private Map<String, Integer> brokers;

    //low level api 消费者对象
    private SimpleConsumer consumer;

    //上一次offset位置
    private long offset;

    //groupId
    private String groupId;

    public SimpleConsumerAPI(String topic, int partition, long offset, String groupId, List<String> brokerAddress) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.groupId = groupId;
        this.brokers = this.getBrokers(brokerAddress);
    }

    */
/**
     * 获取broker信息
     *
     * @param brokerAddress
     * @return
     *//*

    private Map<String, Integer> getBrokers(List<String> brokerAddress) {
        Map<String, Integer> brokerMap = new HashMap<String, Integer>();
        for (String address : brokerAddress) {
            String[] arr = address.split(":");
            brokerMap.put(arr[0], Integer.parseInt(arr[1]));
        }
        return brokerMap;
    }

    */
/**
     * 获取SimpleConsumer
     *
     * @param broker
     * @param port
     * @param clientId
     * @return
     *//*

    private SimpleConsumer getSimpleConsumer(String broker, int port, String clientId) {
        return new SimpleConsumer(broker, port, 100000, 64 * 1024, clientId);
    }

    */
/**
     * 启动consumer
     *//*

    public void open() {
        String leaderBrokerName = this.getLeaderBrokerName();
        this.init(leaderBrokerName);
    }

    */
/**
     * 关闭
     *//*

    public void close() {
        if (this.consumer != null) {
            this.consumer.close();
            this.consumer = null;
        }
    }

    */
/**
     * 获取批量查询数据的Response对象
     *
     * @param curOffset
     * @return
     *//*

    public FetchResponse getFetchResponse(long curOffset) {
        kafka.api.FetchRequest req = new FetchRequestBuilder()
                .clientId(ParamsConst.FETCH_RESPONSE_CLIENT)
                .addFetch(this.topic, this.partition, curOffset, Integer.MAX_VALUE).build();
        return this.consumer.fetch(req);
    }

    */
/**
     * 获取分区列表
     *
     * @return
     *//*

    public List<Integer> getPartitionList() {
        List<String> topics = Collections.singletonList(this.topic);
        List<Integer> partitionList = new ArrayList<Integer>();
        boolean find = false;

        for (String broker : this.brokers.keySet()) {
            SimpleConsumer getPartitionMetadataClient = this.getSimpleConsumer(broker, this.brokers.get(broker), ParamsConst.GET_PARTITION_CLIENT);
            try {
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                kafka.javaapi.TopicMetadataResponse resp = getPartitionMetadataClient.send(req);
                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        partitionList.add(part.partitionId());
                    }
                    find = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                getPartitionMetadataClient.close();
            }
            if (find) {
                break;
            }
        }
        return partitionList;
    }

    */
/**
     * 发送commit请求
     *//*

    public boolean commitRequest(List<OffsetMetadata> offsetList) {
        kafka.javaapi.OffsetCommitRequest offsetCommitRequest = commitOffset(offsetList, topic, partition);
        kafka.javaapi.OffsetCommitResponse offsetResp = this.consumer.commitOffsets(offsetCommitRequest);

        if (offsetResp.hasError()) {
            for (Object partitionErrorCode : offsetResp.errors().values()) {
                if ((Short) partitionErrorCode == ErrorMapping.OffsetMetadataTooLargeCode()) {
                    LOG.error("OffsetMetadataTooLargeCode");
                    throw new OffsetMetadataTooLargeException("OffsetMetadataTooLargeCode:");
                } else if ((Short) partitionErrorCode == ErrorMapping.NotCoordinatorForConsumerCode() || (Short) partitionErrorCode == ErrorMapping.ConsumerCoordinatorNotAvailableCode()) {
                    LOG.error("NotCoordinatorForConsumerCode");
                }
            }
            throw new OffsetCommitException("commit offset error");
        }
        return true;
    }

    */
/**
     * 包装手动commit请求
     *
     * @param offsetList
     * @param topic
     * @param partition
     * @return
     *//*

    private OffsetCommitRequest commitOffset(List<OffsetMetadata> offsetList, String topic, int partition) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);

        Map<TopicAndPartition, OffsetAndMetadata> offsets = new LinkedHashMap<TopicAndPartition, OffsetAndMetadata>();

        for (OffsetMetadata offsetMetadata : offsetList) {
            offsets.put(topicAndPartition, new OffsetAndMetadata(offsetMetadata, System.currentTimeMillis(), -1L));
        }
        return new OffsetCommitRequest(this.groupId, offsets, 0, ParamsConst.COMMIT_REQUEST_CLIENT);
    }

    */
/**
     * 根据leader的地址初始化消费者
     *
     * @param leaderBrokerName
     *//*

    private void init(String leaderBrokerName) {
        this.consumer = this.getSimpleConsumer(leaderBrokerName, this.brokers.get(leaderBrokerName), ParamsConst.INIT_CLIENT);
        long fetchOffset = fetchOffset();
        long logSize = this.getLogSize();

        if (this.offset < 0) {
            LOG.info("log size==========" + logSize);
            LOG.info("fetch offset==========" + fetchOffset());

            //1.如果fetch offset小于log size，表示第一次运行或者有消息丢失
            if (fetchOffset < logSize) {
                if (fetchOffset() == -2) {
                    //2.程序第一次运行。默认offset为-2
                    this.offset = logSize;
                } else if (fetchOffset() == -1) {
                    //3.当前partition从未commit，默认值为-1，则要从0开始读取
                    this.offset = 0;
                } else {
                    //4.表示缓冲部分有数据丢失，从丢失处开始读数据
                    this.offset = fetchOffset;
                }
            } else {
                //2.如果fetch offset不小于当前log size，则表示没有数据丢失，offset为最新
                this.offset = logSize;
            }
        }
    }

    */
/**
     * 获取leader的名字
     * 此时Kafka集群有可能在做leader选举，所以，没获取到的话就循环等待
     *
     * @return
     *//*

    private String getLeaderBrokerName() {
        PartitionMetadata partitionMeta = null;
        int times = 0;
        while (partitionMeta == null || partitionMeta.leader() == null) {
            if (times == 5) {
                throw new IllegalStateException("试了5次也没有找到leader，退出了");
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

    */
/**
     * 获取分区元数据信息
     *
     * @return
     *//*

    private PartitionMetadata getPartitionMetadata() {
        PartitionMetadata partitionMeta = null;
        List<String> topics = Collections.singletonList(this.topic);

        boolean find = false;
        for (String broker : this.brokers.keySet()) {
            SimpleConsumer getPartitionMetadataClient = this.getSimpleConsumer(broker, this.brokers.get(broker), ParamsConst.GET_PARTITION_CLIENT);
            try {
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                kafka.javaapi.TopicMetadataResponse resp = getPartitionMetadataClient.send(req);
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
                getPartitionMetadataClient.close();
            }
            if (find) {
                break;
            }
        }
        return partitionMeta;
    }

    */
/**
     * 获取当前的offset
     *
     * @return
     *//*

    private long fetchOffset() {
        List<TopicAndPartition> partitions = new ArrayList<TopicAndPartition>();
        TopicAndPartition partition = new TopicAndPartition(this.topic, this.partition);
        long retrievedOffset = 0;

        partitions.add(partition);
        //CurrentVersion为1，则metadata从kafka获取，为0表示从zookeeper获取
        OffsetFetchRequest fetchRequest = new OffsetFetchRequest(this.groupId, partitions, kafka.api.OffsetRequest.CurrentVersion(), 0, ParamsConst.FETCH_OFFSET_CLIENT);
        OffsetFetchResponse fetchResponse = this.consumer.fetchOffsets(fetchRequest);
        OffsetMetadataAndError result = fetchResponse.offsets().get(partition);

        short offsetFetchErrorCode = result.error();
        if (offsetFetchErrorCode == ErrorMapping.NotCoordinatorForConsumerCode()) {
            LOG.error("NotCoordinatorForConsumerCode");
            throw new NotCoordinatorForConsumerException("NotCoordinatorForConsumerCode:16");
        } else {
            retrievedOffset = result.offset();
        }
        return retrievedOffset;
    }

    */
/**
     * 获取log size（最新的offset）
     *
     * @return
     *//*

    public long getLogSize() {
        TopicAndPartition topicAndPartition = new TopicAndPartition(this.topic, this.partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
        OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), ParamsConst.GET_LOG_SIZE_CLIENT);
        OffsetResponse response = this.consumer.getOffsetsBefore(request);
        if (response.hasError()) {
            return 0;
        }
        long[] offsets = response.offsets(this.topic, this.partition);
        return offsets[0];
    }

    public long getOffset() {
        return offset;
    }
}
*/
