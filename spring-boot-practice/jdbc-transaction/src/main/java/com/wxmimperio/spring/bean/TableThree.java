package com.wxmimperio.spring.bean;

public class TableThree {
    private Integer id;
    private String job;

    public TableThree() {
    }

    public TableThree(String job) {
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
