package com.wxmimperio.thread.scheduled;

import java.util.concurrent.*;

public class CompletionService {

    /**
     * ExecutorCompletionService 可以让Future也并发执行的实现
     * 通过submit提交的任务在线程池中并发执行，将finish的放入LinkedBlockingQueue中等待take
     * 不像直接使用Future的get方法会阻塞后面Future的结果，是根据Future的顺序获取
     * 及时后面的Future比前面的Future先结束，也要等待当前慢的Future get到结果
     *
     * ExecutorCompletionService， 则用LinkedBlockingQueue解耦这个过程，Future结束相当于生产者，
     * 从ExecutorCompletionService take相当于消费者，所以ExecutorCompletionService是乱序的
     *
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<Integer> completionService =
                new ExecutorCompletionService<>(executorService);

        int taskNum = 10;
        for (int i = 0; i < taskNum; i++) {
            completionService.submit(new Task(i));
        }

        try {
            for (int i = 0; i < taskNum; i++) {
                Future<Integer> future = completionService.take();
                int result = future.get();
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public static class Task implements Callable<Integer> {
        private Integer num;

        public Task(Integer num) {
            this.num = num;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(50);
            System.out.println(Thread.currentThread().getName());
            return num;
        }
    }
}
