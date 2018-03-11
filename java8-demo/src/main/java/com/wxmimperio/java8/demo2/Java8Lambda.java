package com.wxmimperio.java8.demo2;

import com.wxmimperio.java8.pojo.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wxmimperio on 2017/7/16.
 */
public class Java8Lambda {

    private static List<Person> personList = Arrays.asList(
            new Person("wxm1", 18, 1L, 001L),
            new Person("wxm2", 19, 2L, 002L),
            new Person("wxm3", 20, 3L, 003L),
            new Person("wxm4", 21, 4L, 004L),
            new Person("wxm5", 22, 5L, 005L)
    );

    public static void main(String[] args) {

        Java8Lambda java8Lambda = new Java8Lambda();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;
        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + java8Lambda.operate(10, 5, addition));
        System.out.println("10 - 5 = " + java8Lambda.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + java8Lambda.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + java8Lambda.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message -> System.out.println("Hello " + message);

        // 用括号
        GreetingService greetService2 = (message) -> System.out.println("Hello " + message);

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");

        List<Person> people = filterPerson(personList, person -> person.getAge() > 20);
        System.out.println(people);

        System.out.println(filterPerson(personList, person -> person.getNumber() > 4));

        personList.stream().filter(person -> person.getId() > 4).forEach(System.out::println);

        personList.stream().map(Person::getName).forEach(System.out::println);
    }

    private static List<Person> filterPerson(List<Person> oldList, FilterPerson<Person> filterPerson) {
        List<Person> newList = new ArrayList<Person>();
        for (Person person : oldList) {
            if (filterPerson.filer(person)) {
                newList.add(person);
            }
        }
        return newList;
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}
