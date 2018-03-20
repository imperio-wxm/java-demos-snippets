package com.wxmimperio.java8.demo7;

// 继承又实现接口
public class SubClass /*extends MyClass*/ implements InterfaceFun,FatherInterface {

    @Override
    public String getName() {
        return FatherInterface.super.getName();
    }

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        System.out.println(subClass.getName());
        InterfaceFun.staticFun();
    }
}
