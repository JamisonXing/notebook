package com.jamison.jucTest.ArrayBlockingQueue;

//生产者消费者

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        //创建一个容量为3的ArrayblockingQueue
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        //创建生产者进程
        Thread producerThread = new Thread(() -> {
            try {
                //生产者插入元素到队列
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //创建消费者线程
        Thread consumerThread = new Thread(() -> {
            try {
                //消费队列中的元素
                while(true) {
                    Integer value = queue.take();
                    System.out.println("Consumed: " + value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //启动生产者线程和消费者线程
        producerThread.start();
        consumerThread.start();
    }
}
