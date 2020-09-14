package com.wxmimperio.xstream;

import com.wxmimperio.xstream.bean.Fetchers;
import com.wxmimperio.xstream.utils.CommonUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className XmlToBean.java
 * @description This is the description of XmlToBean.java
 * @createTime 2020-09-07 10:42:00
 */
public class XmlToBean {

    public static void main(String[] args) {
        String xmlPath = "/Users/weiximing/code/github/java-demos-snippets/xstream/src/main/resources/FetcherConf.xml";
        Fetchers fetchers = CommonUtils.toBean(xmlPath, Fetchers.class);
        fetchers.getFetcherList().forEach(System.out::println);
    }
}
