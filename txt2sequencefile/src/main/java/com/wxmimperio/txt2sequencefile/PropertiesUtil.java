package com.wxmimperio.txt2sequencefile;

import java.util.ResourceBundle;

/**
 * Created by weiximing.imperio on 2017/4/12.
 */
public class PropertiesUtil {

    public PropertiesUtil() {
    }

    public static String getString(String key) {
        return getValue(key);
    }

    public static Integer getInt(String key) {
        return Integer.valueOf(Integer.parseInt(getValue(key)));
    }

    private static String getValue(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        return bundle.getString(key);
    }
}
