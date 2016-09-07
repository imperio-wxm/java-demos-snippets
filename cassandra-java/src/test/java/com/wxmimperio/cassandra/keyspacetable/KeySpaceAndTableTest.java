package com.wxmimperio.cassandra.keyspacetable;

import org.junit.Test;

/**
 * Created by weiximing.imperio on 2016/9/7.
 */
public class KeySpaceAndTableTest {

    @Test
    public void createKeySpaceAndTabel() {
        boolean done = KeySpaceAndTable.createKandT();
        System.out.println(done);
    }
}
