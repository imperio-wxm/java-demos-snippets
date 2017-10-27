package com.wxmimperio.kafka.topology;

import org.apache.kafka.streams.processor.TopologyBuilder;

/**
 * Created by weiximing.imperio on 2017/10/27.
 */
public interface TopologyList {

    TopologyBuilder get(String source, String[] sinks);
}
