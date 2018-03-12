package com.wxmimperio.java8.demo2;

//注解函数式接口
@FunctionalInterface
public interface FilterPerson<T> {
    boolean filer(T t);
}
