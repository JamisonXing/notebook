package com.jamison.jucTest.reentrantLock;

//当多个线程同时访问一个共享资源时，使用 ReentrantLock 可以实现线程安全的访问控制。下面是一个简单的示例，展示了如何使用 ReentrantLock 来保证线程安全访问一个计数器

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                reentrantLockDemo.increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("final count: " + reentrantLockDemo.count);
    }
}
