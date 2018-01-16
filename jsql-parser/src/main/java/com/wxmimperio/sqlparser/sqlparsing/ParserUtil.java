package com.wxmimperio.sqlparser.sqlparsing;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.SelectUtils;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ParserUtil {

    /**
     * 取出select 到 from 中的想要查询字段列表
     * 例如：select  age,name from test;
     * 返回： List<age,name>
     *
     * @param sql
     * @return
     * @throws JSQLParserException
     */
    public static List<String> getSelectParams(String sql) throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> items = plain.getSelectItems();
        List<String> params = new ArrayList<String>();
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                params.add(items.get(i).toString());
            }
        }
        return params;
    }

    public static void getSelectWhereParams(String sql) throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        Expression expression = plain.getWhere();
        for (SelectItem selectItem : plain.getSelectItems()) {
            System.out.println(selectItem);
        }


        System.out.println(expression);

    }
}
