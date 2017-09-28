package com.wxmimperio.guava;

/**
 * Created by wxmimperio on 2017/9/28.
 */
public class CacheShutdownHook {


    static class CleanWorkThread extends Thread {
        @Override
        public void run() {
            System.out.println("clean some work.");
            try {
                Thread.sleep(10 * 1000);//sleep 10s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //将hook线程添加到运行时环境中去
        Runtime.getRuntime().addShutdownHook(new CleanWorkThread());

        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Thread Running " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        Thread.sleep(6000);

        System.exit(0);
    }
}
