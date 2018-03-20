package com.wxmimperio.java8.demo7;

public interface InterfaceFun {

    default String getName() {
        return "interface";
    }

    static void staticFun() {
        System.out.println("interface static fun");
    }
}
