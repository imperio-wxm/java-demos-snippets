package com.wxmimperio.xstream.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.wxmimperio.xstream.converter.MapEntryConverter;

import java.io.File;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className CommonUtils.java
 * @description This is the description of CommonUtils.java
 * @createTime 2020-09-07 10:48:00
 */
public class CommonUtils {

    public static <T> T toBean(String xmlPath, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        xstream.alias("params", Map.class);
        xstream.registerConverter(new MapEntryConverter());
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(new File(xmlPath));
        return t;
    }
}
