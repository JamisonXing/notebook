package com.jamison.jucTest.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExample {
    static class Task implements Runnable {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Executing task: " + name);
            //执行业务逻辑
        }
    }

    public static void main(String[] args) {
        //创建任务列表
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(new Task("task 1"));
        tasks.add(new Task("task 2"));
        tasks.add(new Task("task 3"));
        tasks.add(new Task("task 4"));
        tasks.add(new Task("task 5"));

        //创建ThreadPoolExecutor
        int corePoolSize = 3;
        int maxNumPoolSize = 5;
        long keepAliveTime = 10;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxNumPoolSize,
                keepAliveTime,
                timeUnit,
                workQueue
        );

        //提交任务给线程池
        for(Runnable task : tasks) {
            executor.execute(task);
        }

        //关闭线程池
        executor.shutdown();
    }
}

