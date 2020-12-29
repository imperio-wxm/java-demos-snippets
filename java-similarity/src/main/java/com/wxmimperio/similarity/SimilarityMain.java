package com.wxmimperio.similarity;

import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className SimilarityMain.java
 * @description This is the description of SimilarityMain.java
 * @createTime 2020-12-29 15:09:00
 */
public class SimilarityMain {

    /**
     * https://github.com/tdebatty/java-string-similarity
     *
     * @param args
     */
    public static void main(String[] args) {
        NormalizedLevenshtein l = new NormalizedLevenshtein();
        String str1 = "2020-12-28 21:15:24,416 ERROR org.apache.flink.runtime.executiongraph.aaaa - Job OCPM-JOIN (46039845540e491653afff63def4b38a) switched from state RUNNING to FAILING. java.lang.RuntimeException at org.apache.flink.streaming.runtime.io.RecordWriterOutput.pushToRecordWriter(RecordWriterOutput.java:110) at org.apache.flink.streaming.runtime.io.RecordWriterOutput.collect(RecordWriterOutput.java:89) at org.apache.flink.streaming.runtime.io.RecordWriterOutput.collect(RecordWriterOutput.java:45) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamFilter.processElement(StreamFilter.java:40) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.pushToOperator(OperatorChain.java:579) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:554) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:534) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamFilter.processElement(StreamFilter.java:40) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.pushToOperator(OperatorChain.java:579) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:554) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:534) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamMap.processElement(StreamMap.java:41) at org.apache.flink.streaming.runtime.io.StreamInputProcessor.processInput(StreamInputProcessor.java:202) at org.apache.flink.streaming.runtime.tasks.OneInputStreamTask.run(OneInputStreamTask.java:112) at org.apache.flink.streaming.runtime.tasks.StreamTask.invoke(StreamTask.java:302) at org.apache.flink.runtime.taskmanager.Task.run(Task.java:711) at java.lang.Thread.run(Thread.java:748) Caused by: java.lang.ArrayIndexOutOfBoundsException";
        String str2 = "2020-12-28 21:16:03,357 ERROR org.apache.flink.runtime.executiongraph.ExecutionGraph - Job OCPM-JOIN (46039845540e491653afff63def4b38a) switched from state RUNNING to FAILING. java.lang.RuntimeException at org.apache.flink.streaming.runtime.io.RecordWriterOutput.pushToRecordWriter(RecordWriterOutput.java:110) at org.apache.flink.streaming.runtime.io.RecordWriterOutput.collect(RecordWriterOutput.java:89) at org.apache.flink.streaming.runtime.io.RecordWriterOutput.collect(RecordWriterOutput.java:45) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamFilter.processElement(StreamFilter.java:40) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.pushToOperator(OperatorChain.java:579) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:554) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:534) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamFilter.processElement(StreamFilter.java:40) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.pushToOperator(OperatorChain.java:579) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:554) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:534) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamMap.processElement(StreamMap.java:41) at org.apache.flink.streaming.runtime.io.StreamInputProcessor.processInput(StreamInputProcessor.java:202) at org.apache.flink.streaming.runtime.tasks.OneInputStreamTask.run(OneInputStreamTask.java:112) at org.apache.flink.streaming.runtime.tasks.StreamTask.invoke(StreamTask.java:302) at org.apache.flink.runtime.taskmanager.Task.run(Task.java:711) at java.lang.Thread.run(Thread.java:748) Caused by: java.lang.ArrayIndexOutOfBoundsException";

        double result = l.similarity(str1, str2);
        BigDecimal bg = new BigDecimal(result).setScale(2, RoundingMode.DOWN);
        System.out.println(bg.doubleValue());
        System.out.println(Double.valueOf(l.similarity(str1, str2)).floatValue());

        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));
    }
}
