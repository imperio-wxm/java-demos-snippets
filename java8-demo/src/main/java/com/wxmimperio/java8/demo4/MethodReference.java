package com.wxmimperio.java8.demo4;

import com.wxmimperio.java8.pojo.Person;
import org.junit.Test;

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
}
