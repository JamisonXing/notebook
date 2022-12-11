# 	课程大纲

![大纲outline](/Users/jamison/Library/Application Support/typora-user-images/image-20221126185852563.png)

![outline2](/Users/jamison/Library/Application Support/typora-user-images/image-20221126190009265.png)

![outline](/Users/jamison/Library/Application Support/typora-user-images/image-20221128134751204.png)

# 第一章 java线程

## 1.1 创建线程

### 方法1，直接使用Thread

```java
@Slf4j(topic = "c.Text1")
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t1");
        t.start();
        log.debug("running");
    }
}
```

### 方法2，使用Runnable配合Thread

把线程和任务分开

- Thread代表线程
- Runnable可运行的任务

```java
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        };

        Thread t = new Thread(r, "t2");

        t.start();
    }
}
```

### lambda简化

只有含有**@FunctionalInterface**(其实就是接口只有一个抽象方法，看看runnable就知道了)的接口才可以被简化

```java
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Runnable r = () -> {
                log.debug("running");
            };

        Thread t = new Thread(r, "t2");

        t.start();
    }
}
```

或者

```java
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Thread t = new Thread( () -> {
            log.debug("running");
        }, "t2");

        t.start();
    }
}
```

### 原理之Thread与Runnable

分析Thread源码，理清它与Runnalbe的关系。

**小结**

- 方法1是把线程和任务合并在一起，方法2是把线程和任务分开了。
- 用Runnable更加容易与线程池等高级API配合。
- 用Runnable让任务类脱离了Thread的继承体系，更灵活。

### 方法三，FutureTask配合Thread

FutureTask能够接受Callable类型的参数，用来处理有返回结果的情况

```java
@Slf4j(topic = "c.Test3")
public class Test3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("running....");
            Thread.sleep(1000);
            return 100;
        });
        
        Thread t1 = new Thread(task, "t1");
        t1.start();
        
        log.debug("{}", task.get());
    }
}
```



## 1.2 查看运行线程的方法

一些命令：ps -ef, top, jps等

java图形化查看命令jconsole



## 1.3* 原理之线程运行

### 栈与栈帧

Java Virtual Machine Stacks (Java虚拟机栈)

jvm中由堆、栈、方法区所组成，其中栈内存是分给线程用的。

- 每个栈由多个栈帧(Frame)组成,对应着每次方法调用时候所占用的内存
- 每个线程只能有一个**活动**栈帧，对应着当前正在执行哪个方法

栈帧图解：https://www.bilibili.com/video/BV16J411h7Rd?t=1.8&p=21

![栈帧图解](/Users/jamison/Library/Application Support/typora-user-images/image-20221128202452907.png)

### 线程上下文切换

因为一下一些原因导致cpu不再执行当前线程，转而执行另一个线程的代码:

- 线程的cpu时间片用完
- 垃圾回收
- 有更高优先级的线程需要运行
- 线程自己调用了sleep, yield, wait, join, park, synchronized, lock等方法

当Context Switch发生时， 需要由操作系统保存当前线程的状态，并恢复另一个线程的状态：

- 状态包括程序计数器(Progrom Counter Register)，虚拟机栈中每个栈帧的信息，如局部变量，操作数栈，返回地址等
- Context Switch频繁发生会影响性能

![上下文切换](/Users/jamison/Library/Application Support/typora-user-images/image-20221128210511752.png)

## 1.4 常见方法

![常见方法](/Users/jamison/Library/Application Support/typora-user-images/image-20221129193631674.png)

![常用方法](/Users/jamison/Library/Application Support/typora-user-images/image-20221129194248890.png)



## 1.5 主线程和守护线程

默认情况下，java进程需要等其他线程都运行结束，才会结束。有一种特殊的线程叫做守护线程，只要其他非守护线程运行结束结束了，即使守护线程的代码没有执行完，也会被强制结束。

```java
Thread thread;
//讲普通线程设置为守护线程
thread.setDaemon(true);
```



## 1.6 线程状态

### 五种状态

这是从**操作系统**的层面来描述的：

![五种状态](/Users/jamison/Library/Application Support/typora-user-images/image-20221206115421313.png)

### 六种状态

这是从**Java API**层面来描述的：

根据Thread.State枚举，分为六种状态

![6种状态](/Users/jamison/Library/Application Support/typora-user-images/image-20221206115731395.png)

使线程出现六种状态，实例如下：

```java
@Slf4j(topic = "c.TestState")
public class TestState {
    public static void main(String[] args) throws InterruptedException {
        //new
        Thread t1 = new Thread(() -> {

        });

        //runnable
        Thread t2 = new Thread(() -> {
            while(true) {

            }
        });
        t2.start();

        //terminated
        Thread t3 = new Thread(() -> {
            log.debug("what");
        });
        t3.start();

        //timed_waiting
        Thread t4 = new Thread(() -> {
            synchronized(TestState.class) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t4.start();

        //waiting
        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t5.start();

        //block
        Thread t6 = new Thread(() -> {
           synchronized (TestState.class) {
               try {
                   TimeUnit.SECONDS.sleep(1);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });
        t6.start();


        TimeUnit.SECONDS.sleep(1);
        log.debug("t1 state {}", t1.getState());
        log.debug("t2 state {}", t2.getState());
        log.debug("t3 state {}", t3.getState());
        log.debug("t4 state {}", t4.getState());
        log.debug("t5 state {}", t5.getState());
        log.debug("t6 state {}", t6.getState());
    }
}

/**
result:
11:51:35:0322 [Thread-2] c.TestState - what
11:51:36:0326 [main] c.TestState - t1 state NEW
11:51:36:0327 [main] c.TestState - t2 state RUNNABLE
11:51:36:0327 [main] c.TestState - t3 state TERMINATED
11:51:36:0327 [main] c.TestState - t4 state TIMED_WAITING
11:51:36:0327 [main] c.TestState - t5 state WAITING
11:51:36:0327 [main] c.TestState - t6 state BLOCKED
*/
```

## 本章小结

本章重点在于掌握

- 线程创建
- 线程重要api，如start, run, sleep, join, interrupt等
- 线程状态
- <font color='green'>应用方面</font>
  - 异步调用：主线程执行期间，其他线程异步执行耗时操作
  - 提高效率：并行计算，缩短运算时间
  - 同步等待：join
  - 统筹规划：合理使用线程，得到最优效果
- <font color='blue'>原理方面</font>
  - 线程运行流程：栈，栈帧，上下文切换，程序计数器
  - Thread两种创建线程的方式
- 模式方面
  - 两阶段终止



# 第二章 共享模型之管程

<center><font size='4px'><i>本章内容</i></font></center>

- 共享问题
- Synchronized
- 线程安全分析
- Monitor
- Wait/notify
- 线程状态转换
- 活跃性
- Lock

## 2.1 共享带来的问题

### **小故事**

<img src="/Users/jamison/Library/Application Support/typora-user-images/image-20221210205452130.png" alt="小故事" style="zoom:67%;" />

### Java体现

![java体现](/Users/jamison/Library/Application Support/typora-user-images/image-20221211134001636.png)

## 2.2 synchronized解决方案

**应用之互斥**

为了避免临界区的静态条件发生，有多种手段可以达到目的

- 阻塞式解决方案：synchronized, Lock
- 非阻塞式的解决方案：原子变量

本次课使用使用阻塞式的解决方案：synchronized，来解决以上问题，即俗称的【对象锁】，他采用互斥的方式让至多同一时刻只有一个线程能持有对象锁，其它线程再想获取这个对象锁就会被阻塞。这样就能保证拥有锁的线程能够安全执行临界区的代码，不用担心线程上下文切换

**注意**：

虽然java中互斥和同步都可以采用synchronized关键字来完成，但他们还是有区别的：

- 互斥是保证临界区的竞态条件发生，同一时刻只有一个线程能够执行临界区代码

- 同步是线程执行的先后顺序不同，需要一个线程等待其他线程运行到某个点

### synchronized

语法

```java
synchronized(对象) {
		临界区
}
```

解决

```java
package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jamison
 */
public class Test9 {
    static int counter = 0;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5000; i++) {
                synchronized(lock) {
                    counter++;
                }
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5000; i++) {
                synchronized(lock) {
                    counter--;
                }
            }
        },"t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter);
    }
}
```

理解

![synchronized理解](/Users/jamison/Library/Application Support/typora-user-images/image-20221211164347147.png)

> 不要误以为拿到了锁就能一直执行下去，时间片用完还得切换道理一个线程，不过另一个线程会阻塞直到时间片用完，切回去继续执行任务
>

用图来表示

![用图来表示synchronized](/Users/jamison/Library/Application Support/typora-user-images/image-20221211164747774.png)

### 思考

synchronized实际是用对象锁保证了临界区内的原子性，临界区内的代码是不可分割的，不会被线程切换所打断

![image-20221211170931549](/Users/jamison/Library/Application Support/typora-user-images/image-20221211170931549.png)

## 面向对象改进

```java
package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jamison
 */
public class Test9 {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5000; i++) {
                room.increment();
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5000; i++) {
                room.decrement();
            }
        },"t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(Room.getCounter());
    }
}

class Room {
    private static int counter = 0;
    public void increment() {
        synchronized(this) {
            counter++;
        }
    }
    public void decrement() {
        synchronized(this) {
            counter--;
        }
    }

    public static int getCounter() {
        return counter;
    }
}
```

## 2.3 方法上的synchronized

加在非静态方法上锁当前对象：

```java
class Test{
  public synchronized void test() {
    
  }
}
//等价于
class Test{
   	public void test() {
    		synchronized(this) {
          
        }
  	}
}
```

加在静态方法上锁类对象:

```java
class Test{
  public synchronized static void test() {
    
  }
}
//等价于
class Test{
   	public void test() {
    		synchronized(Test.class) {
          
        }
  	}
}
```

### 对面向对象进一步改进

```java
package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jamison
 */
public class Test9 {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5000; i++) {
                room.increment();
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5000; i++) {
                room.decrement();
            }
        },"t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(Room.getCounter());
    }
}

class Room {
    private static int counter = 0;
    public synchronized void increment() {
            counter++;
    }
    public synchronized void decrement() {
            counter--;
    }
    public static int getCounter() {
        return counter;
    }
}
```

### 不加synchronized的方法

不加synchronized的方法就好比不遵守规则的人，不去老实排队（好比翻窗户进去的）

### 所谓的"线程八锁"

其实就是考察synchronized锁住的是哪个对象

情况一：12或者21

![情况一](/Users/jamison/Library/Application Support/typora-user-images/image-20221211195404190.png)

情况二：一秒后1然后2，或者2然后一秒后1

![情况二](/Users/jamison/Library/Application Support/typora-user-images/image-20221211195758679.png)

情况三：3一秒后1然后2，或者3, 2然后一秒后1，或者2, 3然后一秒后1

![情况三](/Users/jamison/Library/Application Support/typora-user-images/image-20221211195824247.png)

情况四：2然后一秒后1，锁住了两个不同对象因此不存在互斥

![情况四](/Users/jamison/Library/Application Support/typora-user-images/image-20221211200056768.png)

情况五：2然后一秒后1，锁住了两个不同对象因此不存在互斥

![情况五](/Users/jamison/Library/Application Support/typora-user-images/image-20221211200229804.png)

情况六：一秒后1然后2，或者2然后一秒后1

![情况六](/Users/jamison/Library/Application Support/typora-user-images/image-20221211200351269.png)

情况七：2然后一秒后1，锁住了两个不同对象因此不存在互斥

![情况七](/Users/jamison/Library/Application Support/typora-user-images/image-20221211200441275.png)

情况八：一秒后1然后2，或者2然后一秒后1

![情况八](/Users/jamison/Library/Application Support/typora-user-images/image-20221211200648503.png)

## 2.4 变量的线程安全分析

### 成员变量和静态变量

- 如果他们没有共享，则线程安全
- 如果他们被共享了，根据他们的状态是否能够改变，又分为两种情况
  - 如果只有读操作，则线程安全
  - 如果有读写操作，则这段代码是临界区，需要考虑线程安全



### 局部变量

- 局部变量是线程安全的
- 但是局部变量引用的对象则未必（比如如果引用了堆中的对象而这个对象又被其他线程引用）
  - 如果该对象没有逃离方法的作用访问，他是线程安全的
  - 如果该对象逃离方法的作用范围，需要考虑线程安全

**局部变量的线程安全**

![image-20221211202529164](/Users/jamison/Library/Application Support/typora-user-images/image-20221211202529164.png)

每个线程调用test1()方法时，局部变量i会在每个线程中被创建一份，因此不存在共享

![image-20221211202720413](/Users/jamison/Library/Application Support/typora-user-images/image-20221211202720413.png)

如图

![image-20221211202746569](/Users/jamison/Library/Application Support/typora-user-images/image-20221211202746569.png)

局部变量的引用稍有不同



先看一个成员变量的例子

![image-20221211203756549](/Users/jamison/Library/Application Support/typora-user-images/image-20221211203756549.png)

执行

![image-20221211203831018](/Users/jamison/Library/Application Support/typora-user-images/image-20221211203831018.png)

其中一种情况是，如果线程2还未add，线程1 remove就会报错：

![image-20221211203926555](/Users/jamison/Library/Application Support/typora-user-images/image-20221211203926555.png)

分析：

- 无论哪个线程中的method2/3都引用的是同一个对象

如图

![image-20221211204137124](/Users/jamison/Library/Application Support/typora-user-images/image-20221211204137124.png)

若将list修改为局部变量，那么就不会出现上面的问题了

![image-20221211204232635](/Users/jamison/Library/Application Support/typora-user-images/image-20221211204232635.png)



分析：

- list 是局部变量，每个线程调用时会创建其不同实例，没有共享

![image-20221211204324254](/Users/jamison/Library/Application Support/typora-user-images/image-20221211204324254.png)	









# 番外 应用篇

## 效率

<center>案例-防止cpu占用到100%</center>

### sleep实现

在没有利用CPU来计算时，不要让while(true)空转浪费cpu，这时可以使用yield或sleep来让出cpu的使用权。

```java
public class Demo1 {
    public static void main(String[] args) {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(5);
                } catch(Exception e) {}
            }
        });
    }
}
```



- 可以用wait或者条件变量达到类似的效果
- 不同的是，后两种都需要加速，并且需要相应的唤醒操作，一般使用于要进行同步的场景。
- sleep适用于无序锁的场景。

## 统筹

<center>案例-烧水泡茶问题</center>

![烧水泡茶问题](/Users/jamison/Library/Application Support/typora-user-images/image-20221210181203765.png)

### 1. 解法一：join

```java
package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

import com.jamison.util.Sleeper;

import static com.jamison.util.Sleeper.sleep;

/**
 * @author jamison
 */
@Slf4j(topic = "c.Test8")
public class Test8 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            sleep(1);
            log.debug("烧水");
            sleep(5);
        }, "老王");


        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶");
            sleep(1);
            log.debug("洗茶杯");
            sleep(2);
            log.debug("拿茶叶");
            sleep(1);
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("泡茶");
        }, "小王");

        t1.start();
        t2.start();
    }
}
```

结果

```java
18:20:41:0163 [老王] c.Test8 - 洗水壶
18:20:41:0163 [小王] c.Test8 - 洗茶壶
18:20:42:0169 [老王] c.Test8 - 烧水
18:20:42:0169 [小王] c.Test8 - 洗茶杯
18:20:44:0174 [小王] c.Test8 - 拿茶叶
18:20:47:0175 [小王] c.Test8 - 泡茶
```

解法1的缺陷：

- 上面模拟的是小王等老王的水烧开了，小王泡茶，如果反过来要实现老王等小王的茶叶拿过来了，老王泡茶呢？代码最好能适应两种情况。
- 上面的两个线程其实是各执行各的，如果要模拟老王讲水壶交给小王泡茶，或模拟小王将茶叶交给老王泡茶呢



# 番外 并发编程设计模式

## 两阶段终止模式

在T1线程中如何优雅地终止T2？这里的优雅指的是给T2一个料理后事的机会。

### 1. 错误思路

![错误思路](/Users/jamison/Library/Application Support/typora-user-images/image-20221204180816022.png)

### 2. 两阶段终止模式

一个监控案例

![监控案例](/Users/jamison/Library/Application Support/typora-user-images/image-20221204180915338.png)

```java
@Slf4j(topic = "c.Demo2")
public class Demo2 {
    public static void main(String[] args) throws InterruptedException{
        TwoPhaseTermination t = new TwoPhaseTermination();
        t.start();
        Thread.sleep(4000);
        t.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    private Thread moniter;
    void start() {
        moniter = new Thread(() -> {
            Thread t1 = Thread.currentThread();
            while(true) {
                if(t1.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控程序");
                }catch (InterruptedException e) {
                    log.debug("重置标记");
                    //重置标记，因为sleep过程中被打断，标记为false,不重置的话，下一次循环不会退出。
                    t1.interrupt();
                    e.printStackTrace();
                }
            }
        });
        moniter.start();
    }

    void stop() {
        moniter.interrupt();
    }
}
```

