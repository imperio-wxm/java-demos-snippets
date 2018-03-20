package com.wxmimperio.java8.demo7;

public interface FatherInterface {
    default String getName() {
        return "FatherInterface";
    }
}
