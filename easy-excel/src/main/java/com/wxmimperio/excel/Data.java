package com.wxmimperio.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    @Excel(name = "ColName")
    private List<Model> colName;
    @Excel(name = "ColValue")
    private List<Model> colValue;


}
