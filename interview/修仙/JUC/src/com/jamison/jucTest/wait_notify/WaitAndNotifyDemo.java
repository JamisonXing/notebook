package com.jamison.jucTest.wait_notify;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class WaitAndNotifyDemo {
    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1));
        tasks.add(new Task(2));
        tasks.add(new Task(3));

        Thread member1 = new Thread(new TeamMember(1, tasks));
        Thread member2 = new Thread(new TeamMember(2, tasks));
        Thread member3 = new Thread(new TeamMember(3, tasks));

        member1.start();
        member2.start();
        member3.start();

        for(Task task : tasks) {
            try {
                //等待任务完成
                task.waitForCompletetion();
                System.out.println("all team member completed Task" + task.taskId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Task {
    int taskId;
    private boolean isCompleted;

    public Task(int taskId) {
        this.taskId = taskId;
        this.isCompleted = false;
    }

    public synchronized void completeTask() {
        this.isCompleted = true;
        //通知等待的团队成员任务已经完成
        notify();
    }

    public synchronized void waitForCompletetion() throws InterruptedException {
        while(!isCompleted) {
            //等待任务完成
            wait();
        }
    }
}

class TeamMember implements Runnable {
    private int memberId;
    private List<Task> tasks;

    public TeamMember(int memberId, List<Task> tasks) {
        this.memberId = memberId;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        for (Task task : tasks) {
            try {
                System.out.println("Team member " + memberId + " is working on Task " + task.taskId);
                Thread.sleep(2000);
                // 标记任务完成
                task.completeTask();
                System.out.println("Team member " + memberId + " completed Task " + task.taskId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}