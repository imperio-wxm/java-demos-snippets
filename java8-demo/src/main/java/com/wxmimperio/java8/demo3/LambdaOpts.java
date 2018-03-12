package com.wxmimperio.java8.demo3;

import java.util.Comparator;
import java.util.function.Consumer;

public class LambdaOpts {

    public static void main(String[] args) {
        // 无参无返回值
        Runnable runnable = () -> System.out.println("hello");
        runnable.run();

        // 一个参数无返回值
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("wxm");

        // 有多个参数，lambda中有多条语句，有返回值
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("x = " + x + ", y = " + y);
            return Integer.compare(x, y);
        };
        System.out.println(comparator.compare(1, 2));

        Comparator<Integer> comparator1 = Integer::compare;
        System.out.println(comparator1.compare(1, 2));
    }
}
