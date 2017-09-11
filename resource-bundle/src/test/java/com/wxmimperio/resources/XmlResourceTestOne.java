package com.wxmimperio.resources;

import com.wxmimperio.resources.demo_one.XMLResourceBundleControl;
import org.junit.Test;

import java.util.ResourceBundle;

/**
 * Created by weiximing.imperio on 2017/8/24.
 */
public class XmlResourceTestOne {

    @Test
    public void resourceTest() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundle", new XMLResourceBundleControl());
        for (String key : bundle.keySet()) {
            System.out.println(key + " = " + bundle.getString(key));
        }
    }
}
