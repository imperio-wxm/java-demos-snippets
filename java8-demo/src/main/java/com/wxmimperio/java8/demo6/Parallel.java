package com.wxmimperio.java8.demo6;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

public class Parallel {

    @Test
    public void test() {
        Instant start = Instant.now();
        Long sum = LongStream.rangeClosed(0, 100000000000L).parallel().reduce(0, Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("cost = " + Duration.between(start, end).toMillis());
    }
}
