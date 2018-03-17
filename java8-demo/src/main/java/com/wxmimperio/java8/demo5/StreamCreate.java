package com.wxmimperio.java8.demo5;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamCreate {

    @Test
    public void ops1() {
        // 1. 可以通过Collection 中的stream 或 parallelStream 方法获取
        List<String> list = Arrays.asList("dfasd", "dfasdf", "dfasd");
        list.parallelStream().forEach(System.out::println);

        // 2. 可以通过Arrays 中的 stream 或 parallelStream 方法获取
        Arrays.stream(new String[]{"dfasdf", "dfasd", "dfasd"})
                .map(e -> e + "123").forEach(System.out::println);

        // 3. 可以通过Stream 中的 of() 方法获取流
        Stream.of("sdfasd", "dfasdf", "dfasdf", "1").filter(e -> e.length() > 2).forEach(System.out::println);

        // 4. 创建无限流
        // 迭代
        Stream.iterate(0, e -> e + 10).limit(20).forEach(System.out::println);

        // 生成
        Stream.generate(() -> Math.random()).limit(10).forEach(System.out::println);
    }
}
