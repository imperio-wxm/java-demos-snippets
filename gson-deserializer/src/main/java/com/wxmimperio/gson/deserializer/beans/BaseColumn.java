package com.wxmimperio.gson.deserializer.beans;

import com.wxmimperio.gson.deserializer.common.DataType;

import java.util.List;

public class BaseColumn {
    private String name;
    private DataType dataType;
    private String comment;
    private List<BaseColumn> baseColumns;

    public BaseColumn() {
    }

    public BaseColumn(String name, DataType dataType, String comment, List<BaseColumn> baseColumns) {
        this.name = name;
        this.dataType = dataType;
        this.comment = comment;
        this.baseColumns = baseColumns;
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

    public List<BaseColumn> getBaseColumns() {
        return baseColumns;
    }

    public void setBaseColumns(List<BaseColumn> baseColumns) {
        this.baseColumns = baseColumns;
    }

    @Override
    public String toString() {
        return "BaseColumn{" +
                "name='" + name + '\'' +
                ", dataType=" + dataType +
                ", comment='" + comment + '\'' +
                ", baseColumns=" + baseColumns +
                '}';
    }
}
