package com.wxmimperio.echarts.entity;

public class TaskInfo {

    private String type;
    private Integer id;
    private String taskName;
    private String date;
    private String time;
    private Long diff;

    public TaskInfo() {
    }

    public TaskInfo(String type, Integer id, String taskName, String date, String time, Long diff) {
        this.type = type;
        this.id = id;
        this.taskName = taskName;
        this.date = date;
        this.time = time;
        this.diff = diff;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getDiff() {
        return diff;
    }

    public void setDiff(Long diff) {
        this.diff = diff;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "type='" + type + '\'' +
                ", id=" + id +
                ", taskName='" + taskName + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", diff=" + diff +
                '}';
    }
}
