package com.wxmimperio.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className RateLimiter.java
 * @description This is the description of RateLimiter.java
 * @createTime 2020-11-23 11:32:00
 */
public class RateLimiterMain {

    public static void main(String[] args) throws Exception {
        String[] ret = null;
        System.out.println(Arrays.asList(null == ret ? new String[]{} : ret));
        double qps = 2.0D;
        RateLimiter rateLimiter = RateLimiter.create(qps);

        for (int i = 0; i < 10000; i++) {
            // 等待1秒钟如果未能获取到许可证就返回false，否则返回true
            if (rateLimiter.tryAcquire(3, 1, TimeUnit.SECONDS)) {
                System.out.println("获取到令牌... rate = " + rateLimiter.getRate());
            } else {
                System.out.println("被限流");
                Thread.sleep(200);
            }
        }
    }
}
