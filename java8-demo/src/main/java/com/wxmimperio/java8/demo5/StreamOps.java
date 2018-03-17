package com.wxmimperio.java8.demo5;

import com.wxmimperio.java8.pojo.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamOps {
    private static List<Person> personList = Arrays.asList(
            new Person("wxm1", 18, 1L, 001L),
            new Person("wxm2", 19, 2L, 002L),
            new Person("wxm3", 20, 3L, 003L),
            new Person("wxm4", 21, 4L, 004L),
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

    @Test
    public void ops2() {
        // map
        personList.stream().map(e -> e.getNumber() * 10).forEach(System.out::println);
        personList.stream().map(Person::getName).forEach(System.out::println);
        System.out.println("=========");

        // flatmap
        List<String> names = Arrays.asList("wxm", "123");
        names.stream().flatMap(StreamOps::getCharacter).forEach(System.out::println);
        System.out.println("=========");
    }

    private static Stream<Character> getCharacter(String string) {
        List<Character> list = new ArrayList<>();
        for (Character ch : string.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }
}
