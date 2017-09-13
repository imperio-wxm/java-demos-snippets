package com.wxmimpeiro.kafka;

/**
 * Created by weiximing.imperio on 2017/9/13.
 */
public class ConsumerMain {

    public static void main(String[] args) {

        String[] topics = "test1,test2,test3".split(",", -1);

        try {
            new ConsumeByTime(topics).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
