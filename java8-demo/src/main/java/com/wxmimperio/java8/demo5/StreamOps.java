package com.wxmimperio.java8.demo5;

import com.wxmimperio.java8.pojo.Person;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

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

        // collect
        System.out.println(personList.stream().map(Person::getName).collect(Collectors.toList()));

        System.out.println(personList.stream().collect(Collectors.averagingDouble(Person::getNumber)));

        //group by
        Map<Integer, List<Person>> personMap = personList.stream().collect(Collectors.groupingBy(Person::getAge));
        System.out.println(personMap);

        // 多级分组
        System.out.println(personList.stream().collect(Collectors.groupingBy(Person::getName,
                Collectors.groupingBy(Person::getAge))));

        DoubleSummaryStatistics doubleSummaryStatistics = personList.stream().collect(Collectors.summarizingDouble(Person::getNumber));
        System.out.println(doubleSummaryStatistics.getAverage());

        System.out.println(personList.stream().map(Person::getName).collect(Collectors.joining(",")));
    }

    @Test
    public void ops6() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);

        list.stream().map(e -> e * e).forEach(System.out::println);

        System.out.println("=========");

        Optional<Integer> sum = list.stream().map(e -> 1).reduce(Integer::sum);
        System.out.println(sum.get());
    }

    @Test
    public void ops7() {
       /* Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 600; i++) {
            map.put("wxm" + i, "value" + 1);
        }

        long start = System.currentTimeMillis();
        for (Map.Entry<String, String> value : map.entrySet()) {
            value.getKey();
            value.getValue();
            UUID.randomUUID().toString();
            UUID.randomUUID().toString();
            System.out.print(UUID.randomUUID().toString());
            System.out.print(UUID.randomUUID().toString());
        }
        System.out.println("Cost = " + (System.currentTimeMillis() - start) + " ms");

        long startSteam = System.currentTimeMillis();
        map.entrySet().stream().forEach(entry -> {
            entry.getKey();
            entry.getValue();
            UUID.randomUUID().toString();
            UUID.randomUUID().toString();
            System.out.print(UUID.randomUUID().toString());
            System.out.print(UUID.randomUUID().toString());
        });
        System.out.println("Cost = " + (System.currentTimeMillis() - startSteam) + " ms");*/

        System.out.println("==========================");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(String.valueOf(i));
        }

        long startList = System.currentTimeMillis();
        int i = 0;
        for (String str : list) {
            if (Integer.parseInt(str) % 2 == 0) {
                Integer.parseInt(str);
                i++;
            }
            //str = getRecoverFilePath("dfasdfa","dfasdfasdfasdfasdfasdf","fdsfadsf");
        }
        System.out.println("Cost = " + (System.currentTimeMillis() - startList) + " ms i =" + i);

        long startStreamList = System.currentTimeMillis();
        int j = 0;
        list.stream().filter(str -> Integer.parseInt(str) % 2 == 0).forEach(str -> {
            Integer.parseInt(str);
            //str = getRecoverFilePath("dfasdfa","dfasdfasdfasdfasdfasdf","fdsfadsf");
        });
        System.out.println("Cost = " + (System.currentTimeMillis() - startStreamList) + " ms j = " + j);
    }

    public static String getRecoverFilePath(String topic, String date, String type) {
        String filePrefix = "/user/hive/warehouse/dw.db";
        String[] pathList = {
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "part_date" + "=" + date.substring(0, 10),
        };
        String[] pathList2 = {
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "part_date" + "=" + date.substring(0, 10),
        };
        return StringUtils.join(pathList, "/");
    }

    @Test
    public void ops8() {
        Object[] str = "的发送的发送的,发的所发生的,佛挡杀佛几个好,dfsdfs,5,6".split(",", -1);

        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(String.valueOf(str[i]));
            } catch (ArrayIndexOutOfBoundsException e) {
                str = new ArrayList<Object>(Arrays.asList(str)) {{
                    add("");
                }}.toArray();
            }
        }
        System.out.println(str.length);
        System.out.println(Arrays.asList(str));
    }
}
