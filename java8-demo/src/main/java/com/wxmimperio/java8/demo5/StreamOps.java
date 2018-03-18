package com.wxmimperio.java8.demo5;

import com.wxmimperio.java8.pojo.Person;
import org.junit.Test;

import java.util.*;
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

        // flatmap 以一个函数作为参数，将流中的每个值应用于这个函数后转成新的流，最后将这些新流统一成一个
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

    @Test
    public void ops3() {
        // sorted
        List<String> list = Arrays.asList("dfasd", "12545", "456");
        list.stream().sorted().forEach(System.out::println);

        personList.stream().sorted((e1, e2) -> {
            if (e1.getName().equals(e2.getName())) {
                return e1.getAge().compareTo(e2.getAge());
            } else {
                return e1.getName().compareTo(e2.getName());
            }
        }).forEach(System.out::println);
    }

    @Test
    public void ops4() {
        // allMatch
        System.out.println(personList.stream().allMatch(e -> e.getAge() > 300));

        //anyMatch
        System.out.println(personList.stream().anyMatch(e -> e.getAge() == 18));

        //noneMatch
        System.out.println(personList.stream().noneMatch(e -> e.getName().equals("wxm")));

        //findFirst
        Optional<Person> person = personList.stream().findFirst();
        person.orElse(new Person());//避免空指针，有可能为空，用一个新对象替代
        System.out.println(person);

        //findAny
        System.out.println(personList.parallelStream().findAny());

        //count
        System.out.println(personList.parallelStream().count());

        // max、min
        System.out.println(personList.parallelStream().max(Comparator.comparingInt(Person::getAge)));
        System.out.println(personList.parallelStream().min(Comparator.comparingLong(Person::getNumber)));
    }


    @Test
    public void ops5() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // reduce(T identity，BinaryOperator)
        System.out.println(list.stream().reduce(0, (x, y) -> x + y));

        Optional<Integer> sum = personList.stream().map(Person::getAge).reduce(Integer::sum);
        System.out.println(sum);
    }
}
