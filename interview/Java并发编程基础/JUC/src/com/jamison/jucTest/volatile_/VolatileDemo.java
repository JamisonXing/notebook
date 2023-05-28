package com.jamison.jucTest.volatile_;

public class VolatileDemo {
    private volatile boolean flag = false;

    public void startTask() {
        new Thread(() -> {
            System.out.println("Task started...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            System.out.println("Task completed");
        }).start();
    }

    public void checkTaskStatus() {
        while(!flag) {
            //等待任务完成
        }
        System.out.println("Task status: Complated");
    }

    public static void main(String[] args) {
        VolatileDemo volatileDemo = new VolatileDemo();
        volatileDemo.startTask();
        volatileDemo.checkTaskStatus();
    }
}
