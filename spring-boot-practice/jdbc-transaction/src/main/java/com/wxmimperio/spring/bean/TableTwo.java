package com.wxmimperio.spring.bean;

public class TableTwo {
    private Integer id;
    private String age;

    public TableTwo() {
    }

    public TableTwo(String age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
