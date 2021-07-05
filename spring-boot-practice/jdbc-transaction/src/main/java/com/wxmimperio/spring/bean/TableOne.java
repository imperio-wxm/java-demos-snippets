package com.wxmimperio.spring.bean;

public class TableOne {
    private Integer id;
    private String name;

    public TableOne() {
    }

    public TableOne(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
