package com.wxmimperio.similarity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import info.debatty.java.stringsimilarity.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

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
        String str1 = "2020-12-28 21:15:24,416 ERROR org.apache.flink.runtime.executiongraph.ExecutionGraph - Job OCPM-JOIN (46039845540e491653afff63def4b38a) switched from state RUNNING to FAILING. java.lang.RuntimeException at org.apache.flink.streaming.runtime.io.RecordWriterOutput.pushToRecordWriter(RecordWriterOutput.java:110) at org.apache.flink.streaming.runtime.io.RecordWriterOutput.collect(RecordWriterOutput.java:89) at org.apache.flink.streaming.runtime.io.RecordWriterOutput.collect(RecordWriterOutput.java:45) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamFilter.processElement(StreamFilter.java:40) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.pushToOperator(OperatorChain.java:579) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:554) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:534) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamFilter.processElement(StreamFilter.java:40) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.pushToOperator(OperatorChain.java:579) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:554) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:534) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamMap.processElement(StreamMap.java:41) at org.apache.flink.streaming.runtime.io.StreamInputProcessor.processInput(StreamInputProcessor.java:202) at org.apache.flink.streaming.runtime.tasks.OneInputStreamTask.run(OneInputStreamTask.java:112) at org.apache.flink.streaming.runtime.tasks.StreamTask.invoke(StreamTask.java:302) at org.apache.flink.runtime.taskmanager.Task.run(Task.java:711) at java.lang.Thread.run(Thread.java:748) Caused by: java.lang.ArrayIndexOutOfBoundsException";
        String str2 = "2020-12-28 21:16:03,357 ERROR org.apache.flink.runtime.executiongraph.ExecutionGraph - Job OCPM-JOIN (46039845540e491653afff63def4b38a) switched from state RUNNING to FAILING. java.lang.RuntimeException at org.apache.flink.streaming.runtime.io.RecordWriterOutput.pushToRecordWriter(RecordWriterOutput.java:110) at org.apache.flink.streaming.runtime.io.RecordWriterOutput.collect(RecordWriterOutput.java:89) at org.apache.flink.streaming.runtime.io.RecordWriterOutput.collect(RecordWriterOutput.java:45) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamFilter.processElement(StreamFilter.java:40) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.pushToOperator(OperatorChain.java:579) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:554) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:534) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamFilter.processElement(StreamFilter.java:40) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.pushToOperator(OperatorChain.java:579) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:554) at org.apache.flink.streaming.runtime.tasks.OperatorChain$CopyingChainingOutput.collect(OperatorChain.java:534) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:721) at org.apache.flink.streaming.api.operators.AbstractStreamOperator$CountingOutput.collect(AbstractStreamOperator.java:699) at org.apache.flink.streaming.api.operators.StreamMap.processElement(StreamMap.java:41) at org.apache.flink.streaming.runtime.io.StreamInputProcessor.processInput(StreamInputProcessor.java:202) at org.apache.flink.streaming.runtime.tasks.OneInputStreamTask.run(OneInputStreamTask.java:112) at org.apache.flink.streaming.runtime.tasks.StreamTask.invoke(StreamTask.java:302) at org.apache.flink.runtime.taskmanager.Task.run(Task.java:711) at java.lang.Thread.run(Thread.java:748) Caused by: java.lang.ArrayIndexOutOfBoundsException";

        double result = l.similarity(str1, str2);
        BigDecimal bg = new BigDecimal(result).setScale(2, RoundingMode.DOWN);
        System.out.println(bg.doubleValue());
        System.out.println(Double.valueOf(l.similarity(str1, str2)).floatValue());

        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "My $tring"));

        Cosine cosine = new Cosine(2);


        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            //str1 = str1.replace("ERROR org.apache.flink.runtime.executiongraph.aaaa - Job OCPM-JOIN (46039845540e491653afff63def4b38a) switched from state RUNNING to FAILING.", "123");
            list.add(str1 + UUID.randomUUID().toString());
            list.add(str2);
        }
        long start = System.currentTimeMillis();

        JaroWinkler jw = new JaroWinkler();
        List<String> ll = similarityFilter(jw, list, 0.85);
        System.out.println(ll.size());
        System.out.println("cost = " + (System.currentTimeMillis() - start));
        ll.forEach(k -> {
            System.out.println(k);
        });

        System.out.println(jw.similarity(str1, str2));
        System.out.println(cosine.similarity(cosine.getProfile(str1), cosine.getProfile(str2)));
        System.out.println(l.similarity(str1, str2));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private static List<String> similarityFilter(NormalizedLevenshtein levenshtein, List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.size() == 1) {
                    break;
                }
                if (isSimilarity(levenshtein, list.get(i), list.get(j), 0.95)) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    private static List<String> similarityFilter(Cosine cosine, List<String> list, double standard) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.size() == 1) {
                    break;
                }
                if (isSimilarity(cosine, list.get(i), list.get(j), standard)) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    private static List<String> similarityFilter(JaroWinkler jw, List<String> list, double standard) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.size() == 1) {
                    break;
                }
                if (isSimilarity(jw, list.get(i), list.get(j), standard)) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    private static boolean isSimilarity(NormalizedLevenshtein normalizedLevenshtein, String str1, String str2, double standard) {
        return normalizedLevenshtein.similarity(str1, str2) > standard;
    }

    private static boolean isSimilarity(Cosine cosine, String str1, String str2, double standard) {
        return cosine.similarity(cosine.getProfile(str1), cosine.getProfile(str2)) > standard;
    }

    private static boolean isSimilarity(JaroWinkler jw, String str1, String str2, double standard) {
        return jw.similarity(str1, str2) > standard;
    }
}
