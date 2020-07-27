package com.wxmimperio.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    @Excel(name = "ColName")
    private List<String> colName;
    @Excel(name = "ColValue")
    private List<String> colValue;

    public List<String> getColName() {
        return colName;
    }

    public void setColName(List<String> colName) {
        this.colName = colName;
    }

    public List<String> getColValue() {
        return colValue;
    }

    public void setColValue(List<String> colValue) {
        this.colValue = colValue;
    }
}
