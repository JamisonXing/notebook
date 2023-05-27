package com.jamison.jucTest.semaphore;
//下面是一个使用 Semaphore 的示例代码，演示了如何控制并发访问资源的数量

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    private static final int ThREAD_COUNT = 10;
    private static final int MAX_CONCURRENT_ACCESS = 5;

    private static Semaphore semaphore = new Semaphore(MAX_CONCURRENT_ACCESS);

    public static void main(String[] args) {
        for (int i = 1; i <= ThREAD_COUNT; i++) {
            Thread thread = new Thread(new Worker(i));
            thread.start();
        }
    }

    static class Worker implements Runnable {
        private int workerId;

        public Worker(int workerId) {
            this.workerId = workerId;
        }

        @Override
        public void run() {
            try {
                System.out.println("Worker" + workerId + "is trying to access the resource");
                semaphore.acquire();
                System.out.println("worker" + workerId + "has acquired the resource");

                //模拟对资源的访问
                Thread.sleep(2000);

                System.out.println("worker" + workerId + "is releasing the resource");
                semaphore.release();
                System.out.println("worker" + workerId + "has released the resource");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
