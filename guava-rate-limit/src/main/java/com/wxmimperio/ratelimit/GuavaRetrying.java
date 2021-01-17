package com.wxmimperio.ratelimit;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author weiximing
 * @version 1.0.0
 * @className GuavaTetrying.java
 * @description This is the description of GuavaTetrying.java
 * @createTime 2020-12-04 17:53:00
 */
public class GuavaRetrying {

    public static void main(String[] args) throws Exception {
        AtomicInteger retryCounter = new AtomicInteger(10);

        for (int i = 0; i < 15; i++) {
            //System.out.println(retryCounter.decrementAndGet());
            System.out.println("get = " + retryCounter.get());
        }

        Retryer<Boolean> reTry = RetryerBuilder.<Boolean>newBuilder()
                //设置异常重试源
                .retryIfExceptionOfType(Exception.class)
                //设置根据结果重试
                .retryIfResult(aBoolean -> Objects.equals(aBoolean, false))
                //设置等待间隔时间
                .withWaitStrategy(WaitStrategies.fixedWait(2, TimeUnit.SECONDS))
                //设置最大重试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();

        reTry.call(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.println("运行");
                return false;
            }
        });
    }
}
