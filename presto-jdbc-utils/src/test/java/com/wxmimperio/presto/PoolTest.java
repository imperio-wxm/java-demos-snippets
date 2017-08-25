package com.wxmimperio.presto;

import org.junit.Test;

import java.util.Vector;

/**
 * Created by weiximing.imperio on 2017/8/25.
 */
public class PoolTest {

    @Test
    public void vectorTest() {
        Vector<String> pool = new Vector<String>(5);
        for (int i = 0; i < 10; i++) {
            pool.add(String.valueOf(i));
        }

        System.out.println(pool);

        for (String str : pool) {
            System.out.println(str);
        }
    }
}
