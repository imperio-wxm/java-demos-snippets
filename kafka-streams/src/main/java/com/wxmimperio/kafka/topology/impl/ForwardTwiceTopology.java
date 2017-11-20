package com.wxmimperio.kafka.topology.impl;

import com.wxmimperio.kafka.processors.ChildOneProcessor;
import com.wxmimperio.kafka.processors.ChildTwoProcessor;
import com.wxmimperio.kafka.processors.ForwardTwiceProcessor;
import com.wxmimperio.kafka.topology.Topology;
import org.apache.kafka.streams.processor.TopologyBuilder;

public class ForwardTwiceTopology implements Topology {

    @Override
    public TopologyBuilder get(String source, String sink) {
        String sinks[] = sink.split(",");
        String sinkOne = sinks[0];
        String sinkTwo = sinks[1];


        TopologyBuilder builder = new TopologyBuilder();
        builder = builder.addSource("SOURCE", source);
        builder = builder.addProcessor("PROCESS1", new ForwardTwiceProcessor(), "SOURCE");

        builder = builder.addProcessor("ChildOneProcess", new ChildOneProcessor(), "PROCESS1");
        builder = builder.addProcessor("ChildTwoProcess", new ChildTwoProcessor(), "PROCESS1");

        builder = builder.addSink("ChildOneSink", sinkOne, "ChildOneProcess");
        builder = builder.addSink("ChildTwoSink", sinkTwo, "ChildTwoProcess");
        return builder;
    }
}
