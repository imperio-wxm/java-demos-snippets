package com.wxmimperio.thread.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolDemo {

    public static void main(String[] args) {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

        // 需要2s完成
        TimerTask timerTask = new TimerTask(2000);

        timer.scheduleAtFixedRate(timerTask, 5000, 10000, TimeUnit.MILLISECONDS);
    }

    private static class TimerTask implements Runnable {

        private final int sleepTime;
        private final SimpleDateFormat dateFormat;

        public TimerTask(int sleepTime) {
            this.sleepTime = sleepTime;
            dateFormat = new SimpleDateFormat("HH:mm:ss");
        }

        @Override
        public void run() {
            System.out.println("任务开始，当前时间：" + dateFormat.format(new Date()));

            try {
                System.out.println("模拟任务运行...");
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }

            System.out.println("任务结束，当前时间：" + dateFormat.format(new Date()));
            System.out.println();
        }
    }
}
