package com.wxmimperio.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;

import java.io.Serializable;
import java.util.List;

public class MyDate implements Serializable {
    @Excel(name = "LogId", needMerge = true)
    private String logId;
    @Excel(name = "DB", needMerge = true)
    private String dbName;
    @Excel(name = "TableName", needMerge = true)
    private String tableName;
    @ExcelCollection(name = "")
    private List<Model> data;


    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
