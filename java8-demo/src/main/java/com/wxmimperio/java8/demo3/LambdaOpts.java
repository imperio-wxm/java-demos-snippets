package com.wxmimperio.java8.demo3;

import com.wxmimperio.java8.pojo.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaOpts {

    private static List<Person> personList = Arrays.asList(
            new Person("wxm1", 18, 1L, 001L),
            new Person("wxm2", 19, 2L, 002L),
            new Person("wxm3", 20, 3L, 003L),
            new Person("wxm4", 21, 4L, 004L),
            new Person("wxm5", 22, 5L, 005L)
    );

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

    @Test
    public void sort() {
        Collections.sort(personList, (p1, p2) -> {
            if (p1.getId() > p2.getId()) {
                return p1.getName().compareTo(p2.getName());
            } else {
                return Long.compare(p1.getId(), p2.getId());
            }
        });

        System.out.println(personList);
    }

    // Consumer<T>
    @Test
    public void consumerInterFace() {
        consume(100.0D, e -> System.out.println(e * 100));
    }

    private void consume(double price, Consumer<Double> consumer) {
        consumer.accept(price);
    }

    //Supplier<T>
    @Test
    public void supplierInterFace() {
        System.out.println(getNumber(5, () -> (int) (Math.random() * 100)));
    }

    private List<Integer> getNumber(int count, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    // Function<T, R>
    @Test
    public void functionInterFace() {
        System.out.println(getUpperCase("dfasdfas", e -> e.toUpperCase()));
    }

    private String getUpperCase(String oldStr, Function<String, String> function) {
        return function.apply(oldStr);
    }
}
