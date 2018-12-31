package com.wxmimperio.spring;

import com.google.common.collect.Lists;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;
import java.util.stream.Stream;

public class SimpaleEl {
    private static ExpressionParser parser = new SpelExpressionParser();

    public static void main(String[] args) {
        Expression expression = parser.parseExpression("'1, 55'.contains('" + 5 + "')");
        Object result = (Object) expression.getValue();
        System.out.println(result);
        expression = parser.parseExpression("'Hello world!'.toUpperCase()");
        result = expression.getValue(String.class);
        System.out.println(result);

        EvaluationContext context = new StandardEvaluationContext();
        List<Integer> nums = Lists.newArrayList();
        Stream.iterate(0, x -> x + 1).limit(101).forEach(nums::add);
        context.setVariable("nums", nums);
        int allCount = 100000;

        long start = System.currentTimeMillis();
        for (int i = 0; i < allCount; i++) {
            parser.parseExpression("#nums.contains(2)").getValue(context);
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println(String.format("cost = %s ms", cost));

        long listStart = System.currentTimeMillis();
        for (int i = 0; i < allCount; i++) {
            boolean isContains = nums.contains(2);
        }
        long listCost = System.currentTimeMillis() - listStart;
        System.out.println(String.format("listCost = %s ms", listCost));
    }
}
