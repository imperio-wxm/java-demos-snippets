package com.wxmimperio.java8.demo4;

import com.wxmimperio.java8.pojo.Person;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MethodReference {

    // 对象::实例方法名
    @Test
    public void reference1() {
        Consumer<String> stringConsumer = (str) -> System.out.println(str);
        stringConsumer.accept("45645");

        Consumer<String> consumer = System.out::println;
        consumer.accept("dfasd");
    }

    @Test
    public void reference2() {
        Person person = new Person("wxm", 50, 10L, 20L);
        Supplier<String> supplier = () -> person.getName();
        String name = supplier.get();
        System.out.println(name);

        Supplier<Integer> supplier1 = person::getAge;
        int age = supplier1.get();
        System.out.println(age);
    }

    // 类::静态方法名
    @Test
    public void reference3() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        System.out.println(comparator.compare(12, 53));

        Comparator<Integer> comparator1 = Integer::compareTo;
        System.out.println(comparator1.compare(56, 10));
    }

    // 类::实例方法名
    @Test
    public void reference4() {
        // 当第一个参数为函数式接口的调用者，而第二个参数是函数式接口的参数时才能应用
        BiPredicate<String, String> biPredicate = (x, y) -> x.equalsIgnoreCase(y);
        System.out.println(biPredicate.test("123", "123"));

        BiPredicate<String, String> biPredicate1 = String::equalsIgnoreCase;
        System.out.println(biPredicate1.test("abc", "123"));
    }
}
