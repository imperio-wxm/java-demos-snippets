package com.wxmimperio.java8.pojo;

public class Person {
    private String name;
    private int age;
    private long number;
    private long id;

    public Person() {
    }

    public Person(String name, int age, long number, long id) {
        this.name = name;
        this.age = age;
        this.number = number;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", number=" + number +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
