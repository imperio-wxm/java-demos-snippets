package com.wxmimperio.sqlparser;

import com.wxmimperio.sqlparser.sqlparsing.ParserUtil;

import java.util.List;

public class SQLMain {

    public static void main(String[] args) throws Exception {
        List<String> selectParams = ParserUtil.getSelectParams("select name,age,gender from test where part_date = '2018-01-16'");
        System.out.println(selectParams);
    }
}
