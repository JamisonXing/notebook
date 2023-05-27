package com.jamison.jucTest.countDownLatch;

import java.util.concurrent.CountDownLatch;

//当多个运动员准备参加比赛时，比赛不能开始直到所有运动员都准备好。这是一个典型的使用 CountDownLatch 的场景。

public class CountDownLatchDemo {
    public static void main(String[] args) {
        int numberOfRunners = 5;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch readySignal = new CountDownLatch(numberOfRunners);
        CountDownLatch finishSignal = new CountDownLatch(numberOfRunners);

        //创建并启动线程
        for (int i = 1; i <= numberOfRunners; i++) {
            Thread runner = new Thread(new Runner(startSignal, readySignal, finishSignal, i));
            runner.start();
        }

        try {
            System.out.println("race will start in 5min");
            //模拟准备时间
            Thread.sleep(2000);
            //发出开始信号
            startSignal.countDown();
            System.out.println("race start");

            //等待所有运动员跑完
            finishSignal.await();
            System.out.println("race has finished");
        } catch(InterruptedException e) {
            e.printStackTrace();
        }



    }

    static class Runner implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch readySignal;
        private final CountDownLatch finishSignal;
        private final int runnerNumber;

        Runner(CountDownLatch startSignal, CountDownLatch readySignal, CountDownLatch finishSignal, int runnerNumber) {
            this.startSignal = startSignal;
            this.readySignal = readySignal;
            this.finishSignal = finishSignal;
            this.runnerNumber = runnerNumber;
        }

        public void run() {
            try {
                System.out.println("jock" + runnerNumber + "has ready");
                //准备好了，通知等待的进程（裁判）
                readySignal.countDown();
                //等待开始信号
                startSignal.await();

                //运动员开始跑步
                System.out.println("jock" + runnerNumber + "starting run");
                //模拟跑步过程
                Thread.sleep((long)(Math.random() * 5000));
                System.out.println("jock" + runnerNumber + "has finished running");

                //跑步结束通知主线程
                finishSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
