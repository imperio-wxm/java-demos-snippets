package com.wxmimperio.sqlparser;

import com.wxmimperio.sqlparser.sqlparsing.ParserUtil;
import com.wxmimperio.sqlparser.visitor.SelectSqlVisitor;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.Select;

import java.io.StringReader;
import java.util.List;

public class SQLMain {

    public static void main(String[] args) throws Exception {
        String sql = "select name,age,gender from test where part_date = '2018-01-16' and age = 6 and gender = '456' and name between 'wxm' and 'wxmimperio' limit 10";
        String sql1 = "select * from test where name in ('wxm','fdsf','fsdf') and age is null";
        List<String> selectParams = ParserUtil.getSelectParams(sql);
        System.out.println(selectParams);

        //ParserUtil.getSelectWhereParams(sql);

        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql1));
        new SelectSqlVisitor(select);
    }
}
