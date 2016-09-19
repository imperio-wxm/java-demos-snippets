package com.wxmimperio.simple.consumer.clients;

import com.wxmimperio.simple.consumer.clients.commit.CommitOffset;
import kafka.javaapi.FetchResponse;

import java.util.List;

/**
 * Created by weiximing.imperio on 2016/9/19.
 */
public interface ISimpleConsumerAPI {
    void open();

    void close();

    FetchResponse getFetchResponse(long curOffset);

    List<Integer> getPartitionList();

    boolean commitRequest(List<CommitOffset> offsetList);

    long getLogSize();
}
