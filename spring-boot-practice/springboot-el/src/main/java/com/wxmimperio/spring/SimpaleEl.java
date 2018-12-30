package com.wxmimperio.spring;

import com.google.common.collect.Lists;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.List;

public class SimpaleEl {
    private static ExpressionParser parser = new SpelExpressionParser();

    public static void main(String[] args) {
        Expression expression = parser.parseExpression("'1,2,3'.split(',',-1).contains('" + 5 + "')");
        String result = (String) expression.getValue();
        System.out.println(result);
        expression = parser.parseExpression("'Hello world!'.toUpperCase()");
        result = expression.getValue(String.class);
        System.out.println(result);
    }
}
