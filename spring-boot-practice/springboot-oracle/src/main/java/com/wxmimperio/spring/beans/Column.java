package com.wxmimperio.spring.beans;

import com.wxmimperio.spring.common.DataType;

public class Column {
    private String name;
    private DataType dataType;
    private String comment;

    public Column() {
    }

    public Column(String name, DataType dataType, String comment) {
        this.name = name;
        this.dataType = dataType;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", dataType=" + dataType +
                ", comment='" + comment + '\'' +
                '}';
    }
}
