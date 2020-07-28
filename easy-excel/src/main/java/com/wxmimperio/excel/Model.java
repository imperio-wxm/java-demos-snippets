package com.wxmimperio.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class Model implements Serializable {
    @Excel(name = "Col1")
    private String c1;
    @Excel(name = "Col2")
    private String c2;
    @Excel(name = "Cost")
    private long cost;
    @Excel(name = "Pre")
    private float pre;

    @Override
    public String toString() {
        return "Model{" +
                "c1='" + c1 + '\'' +
                ", c2='" + c2 + '\'' +
                ", cost=" + cost +
                ", pre=" + pre +
                '}';
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public float getPre() {
        return pre;
    }

    public void setPre(float pre) {
        this.pre = pre;
    }
}
