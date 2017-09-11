package com.wxmimperio.resources.demo_two;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by weiximing.imperio on 2017/8/24.
 */
public class XMLResourceBundle extends ResourceBundle {

    private Properties props;

    XMLResourceBundle(InputStream stream) throws IOException {
        props = new Properties();
        props.loadFromXML(stream);
    }

    protected Object handleGetObject(String key) {
        return props.getProperty(key);
    }

    public Enumeration<String> getKeys() {
        Set<String> handleKeys = props.stringPropertyNames();
        return Collections.enumeration(handleKeys);
    }
}
