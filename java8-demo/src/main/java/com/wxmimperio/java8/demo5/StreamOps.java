package com.wxmimperio.java8.demo5;

import com.wxmimperio.java8.pojo.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class StreamOps {
    private static List<Person> personList = Arrays.asList(
            new Person("wxm1", 18, 1L, 001L),
            new Person("wxm2", 19, 2L, 002L),
            new Person("wxm3", 20, 3L, 003L),
            new Person("wxm4", 21, 4L, 004L),
            new Person("wxm5", 22, 5L, 005L),
            new Person("wxm5", 22, 5L, 005L),
            new Person("wxm5", 22, 5L, 005L)
    );

    @Test
    public void ops1() {
        // filter
        // 惰性求值
        personList.stream().filter(e -> e.getAge() > 19).filter(e -> e.getNumber() > 4)
                .forEach(System.out::println);
        System.out.println("======");
        // limit
        personList.stream().limit(2).forEach(System.out::println);
        System.out.println("======");
        // skip
        personList.stream().skip(2).forEach(System.out::println);
        System.out.println("======");
        // distinct
        personList.stream().distinct().forEach(System.out::println);
    }
}
