package com.wxmimperio.java8.demo6;

import com.wxmimperio.java8.pojo.Person;
import org.junit.Test;

import java.util.Optional;

public class OptionalOps {

    @Test
    public void opt1() {
        // of() 传入的对象不能为null
        Optional<Person> optionalPerson = Optional.of(new Person());
        System.out.println(optionalPerson.get());
    }

    @Test
    public void opt2() {
        // 空的optional
        Optional<Person> empty = Optional.empty();
        System.out.println(empty.get());
    }

    @Test
    public void opt3() {
        Optional<Person> optionalPerson = Optional.ofNullable(new Person());
        System.out.println(optionalPerson.get());
    }

    @Test
    public void opt4() {
        // 判断是否有值
        Optional<Person> empty = Optional.empty();
        System.out.println(empty.isPresent());
    }

    @Test
    public void opt5() {
        // 没有值则用默认值代替
        Optional<Person> empty = Optional.empty();
        Person person = empty.orElse(new Person());
        System.out.println(person);
    }

    @Test
    public void opt6() {
        // 没有值则用默认值代替
        Optional<Person> empty = Optional.empty();
        Person person = empty.orElseGet(Person::new);
        System.out.println(person);
    }

    @Test
    public void opt7() {
        Optional<Person> optionalPerson = Optional.of(new Person("wxm", 12, 123456L, 45612L));
        Optional<String> name = optionalPerson.map(e -> e.getName());
        if (name.isPresent()) {
            System.out.println(name.get());
        }
    }

    @Test
    public void opt8() {
        Optional<Person> optionalPerson = Optional.of(new Person("wxm", 12, 123456L, 45612L));
        Optional<String> name = optionalPerson.flatMap(e -> Optional.of(e.getName()));
        if (name.isPresent()) {
            System.out.println(name.get());
        }
    }

    @Test
    public void opt9() {

    }

    @Test
    public void opt10() {

    }
}
