package com.wxmimperio.process;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public class GenericsFactory {
    public GenericsMethod createGenerics(String type) {
        if (type.equalsIgnoreCase("test1"))
            return new GenericsMethod.GenericsTest();
        else if (type.equalsIgnoreCase("test2")) {
            String params = "cmd /c dir";
            return new GenericsMethod.GenericsTest2(params);
        } else {
            return null;
        }
    }

    public GenericsClass createGenericsClass(String type) {
        switch (type) {
            case "list":
                return new GenericsClass.GenericsClassList();
            case "process":
                String params = "cmd /c dir";
                return new GenericsClass.GenericsClassProcess(params);
            default:
                return null;
        }
    }
}
