package com.wxmimperio.txt2sequencefile;

import org.junit.Test;

/**
 * Created by weiximing.imperio on 2017/4/12.
 */
public class PropTest{

    @Test
    public void prop() {
        System.out.println(PropManager.getInstance().getPropertyByString("hdfs.uri"));
        System.out.println(PropertiesUtil.getString("hdfs.uri"));
    }
}
