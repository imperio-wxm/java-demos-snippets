package com.wxmimperio.log4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className RateLimitTest.java
 * @description This is the description of RateLimitTest.java
 * @createTime 2020-11-24 20:24:00
 */
public class RateLimitTest {
    private static final Logger LOG = LoggerFactory.getLogger(RateLimitTest.class);

    @Test
    public void test() {
        for (int i = 0; i < 50000; i++) {
            LOG.info("test = " + i + "在发的所发生的繁盛发生是否阿斯顿发阿萨德发送法师打发是");
        }
    }
}
