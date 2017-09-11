package com.wxmimperio.process;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiximing.imperio on 2017/8/28.
 */
public class GenericsFactory<T> {
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

    public List<T> addAll() {
        List<T> resultList = new ArrayList<T>();

        GenericsClass genericsClassList = new GenericsFactory().createGenericsClass("list");
        List<String> list = genericsClassList.getResult();
        genericsClassList.exec(list);

        GenericsClass genericsClassProcess = new GenericsFactory().createGenericsClass("process");
        List<Process> processList = genericsClassProcess.getResult();
        genericsClassProcess.exec(processList);

        resultList.addAll((List<T>)list);
        resultList.addAll((List<T>)processList);
        return resultList;
    }
}
