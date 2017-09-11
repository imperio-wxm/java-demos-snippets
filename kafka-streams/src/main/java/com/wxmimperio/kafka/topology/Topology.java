package com.wxmimperio.kafka.topology;

import org.apache.kafka.streams.processor.TopologyBuilder;

/**
 * Created by weiximing.imperio on 2017/8/21.
 */
public interface Topology {
    TopologyBuilder get(String source, String sink);
}
