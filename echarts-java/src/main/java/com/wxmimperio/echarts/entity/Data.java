package com.wxmimperio.echarts.entity;

import java.util.List;

public class Data {

    private String name;
    private List<String> attr;
    private List<String> data;

    public Data() {
    }

    public Data(String name, List<String> attr, List<String> data) {
        this.name = name;
        this.attr = attr;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAttr() {
        return attr;
    }

    public void setAttr(List<String> attr) {
        this.attr = attr;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", attr=" + attr +
                ", data=" + data +
                '}';
    }
}
