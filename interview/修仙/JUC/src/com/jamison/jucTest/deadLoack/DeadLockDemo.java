package com.jamison.jucTest.deadLoack;

//模拟死锁

public class DeadLockDemo {
    public static void main(String[] args) {
        final Object resource1 = new Object();
        final Object resource2 = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized(resource1) {
                System.out.println("Thread1 Acquired resourced1");

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    System.out.println("Thread1 Acquired resourced2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread2 Acquired resourced2");

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource1) {
                    System.out.println("Thread2 Acquired resourced1");
                }
            }
        });

        thread1.start();
        thread2.start();
    }

}
