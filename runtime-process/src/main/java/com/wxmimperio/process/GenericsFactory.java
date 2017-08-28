package com.wxmimperio.process;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public class GenericsFactory {
    public Generics createGenerics(String type) {
        if (type.equalsIgnoreCase("test1"))
            return new Generics.GenericsTest();
        else if (type.equalsIgnoreCase("test2")) {
            String params = "cmd ipconfig /all";
            return new Generics.GenericsTest2(params);
        } else {
            return null;
        }
    }
}
