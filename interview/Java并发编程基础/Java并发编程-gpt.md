# AQS

有人说Lock、 CountDownLatch、Semaphore 等都用到了 AQS，那么在哪部分使用到了呢

AQS（AbstractQueuedSynchronizer）是 Java 并发包中的一个抽象类，它提供了一种基于队列的同步器的实现框架。Lock、CountDownLatch、Semaphore 等同步工具类都是基于 AQS 实现的。

AQS 的主要作用是管理同步状态和线程的排队，它定义了两种同步状态：独占模式（Exclusive Mode）和共享模式（Shared Mode）。具体来说，AQS 提供了一些基本的操作方法，如获取/释放同步状态、线程的排队和唤醒等。

这些同步工具类在使用 AQS 时，会通过继承 AQS 并实现特定的方法来实现自己的同步逻辑。下面是它们在 AQS 中的主要使用方式：

1. Lock：ReentrantLock 是基于 AQS 实现的互斥锁。在 AQS 中，ReentrantLock 通过继承 AQS 并实现 tryAcquire 和 tryRelease 方法来控制锁的获取和释放。
2. CountDownLatch：CountDownLatch 是一个倒计时门栓，它允许一个或多个线程等待其他线程完成操作后再继续执行。在 AQS 中，CountDownLatch 使用共享模式，即多个线程可以同时等待。通过继承 AQS 并实现 tryAcquireShared 和 tryReleaseShared 方法，CountDownLatch 实现了倒计时等待的功能。
3. Semaphore：Semaphore 是一个信号量，用于控制同时访问某个资源的线程数。在 AQS 中，Semaphore 也使用了共享模式。通过继承 AQS 并实现 tryAcquireShared 和 tryReleaseShared 方法，Semaphore 实现了对资源的控制。

这些同步工具类都通过 AQS 提供的队列和同步状态管理机制来实现线程的排队和同步控制，具体的同步逻辑会在继承 AQS 后的实现类中进行定义。这样可以减少了对线程的直接操作，提供了更高层次的抽象和灵活性。

# ReentrantLock

## 核心代码

ReentrantLock 是 Java 并发包中的可重入互斥锁，它实现了 Lock 接口。下面是 ReentrantLock 类的核心源码示例（简化版）：

```java
public class ReentrantLock implements Lock, Serializable {
    private final Sync sync;

    // 静态内部类，继承自 AQS
    private static class Sync extends AbstractQueuedSynchronizer {
        // 重写 AQS 的方法，实现获取锁的逻辑
        protected boolean tryAcquire(int arg) {
            // 获取当前线程
            Thread currentThread = Thread.currentThread();
            int c = getState();

            // 如果锁已经被当前线程持有，则重入计数增加
            if (c != 0) {
                if (currentThread == getExclusiveOwnerThread()) {
                    setState(c + 1);
                    return true;
                }
            }
            // 尝试获取锁，通过 CAS 操作修改状态值
            if (compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(currentThread);
                return true;
            }
            return false;
        }

        // 重写 AQS 的方法，实现释放锁的逻辑
        protected boolean tryRelease(int arg) {
            // 获取当前线程
            Thread currentThread = Thread.currentThread();
            // 判断当前线程是否持有锁
            if (currentThread != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException("Lock not held by current thread");
            }
            int c = getState() - arg;
            // 如果重入计数变为 0，则锁被完全释放
            if (c == 0) {
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return true;
        }
    }

    // ReentrantLock 构造函数
    public ReentrantLock() {
        sync = new NonfairSync();
    }

    // 加锁
    public void lock() {
        sync.lock();
    }

    // 解锁
    public void unlock() {
        sync.unlock();
    }
}
```

ReentrantLock 的核心是内部类 Sync，它继承了 AbstractQueuedSynchronizer（AQS），通过重写 AQS 的方法来实现锁的获取和释放逻辑。

在 `tryAcquire(int arg)` 方法中，首先获取当前线程并获取当前锁的状态值。如果锁已经被当前线程持有，则重入计数增加。如果锁没有被持有，通过 CAS（比较并交换）操作将状态值设置为 1，并设置当前线程为独占锁的所有者。

在 `tryRelease(int arg)` 方法中，首先获取当前线程并检查当前线程是否持有锁。如果不是持有锁的线程调用解锁操作，则抛出 IllegalMonitorStateException 异常。然后根据传入的参数减少状态值，如果状态值变为 0，则完全释放锁，将独占锁的所有者设置为 null。

ReentrantLock 类中还包含了其他方法和内部类，用于支持公平和非公平锁的实现以及条件变量的使用，但上述源码示例是其核心部分。

## 怎么理解reentrantlock的可重入性

ReentrantLock 的可重入性是指同一个线程可以多次获取该锁，而不会导致线程自己被阻塞。换句话说，如果一个线程已经获取了 ReentrantLock 的锁，那么它可以多次调用锁的获取操作，而无需等待自己释放锁。

这种可重入性的实现是通过 ReentrantLock 内部维护的一个计数器来实现的，通常称为锁的重入计数。每当一个线程成功获取到锁时，重入计数会增加1。当该线程再次请求获取锁时，计数器会再次增加，而不是被阻塞。只有当重入计数归零时，锁才会被完全释放。

可重入性带来了一些重要的优势和便利性：

1. 避免死锁：可重入性使得同一个线程可以在持有锁的情况下继续请求获取锁，从而避免了因为同一个线程多次请求同一个锁而导致的死锁情况。
2. 增强封装性：通过使用 ReentrantLock，可以在同一个线程内部将锁保护的代码块分解为多个方法，而无需担心线程自身的锁获取和释放问题。
3. 支持递归调用：如果在某个方法中已经获取了锁，而该方法内部又递归调用了自身，由于可重入性的存在，线程可以继续获取锁，而不会被阻塞。

需要注意的是，线程在释放锁时必须相应地减少重入计数。只有当重入计数归零时，锁才会完全释放，其他线程才能够获取到该锁。

总之，ReentrantLock 的可重入性使得同一个线程能够多次获取锁，从而避免了死锁情况，增强了封装性，并支持递归调用，提供了更灵活和便利的锁控制机制。

## ReentrantLock在生产环境中的应用场景

ReentrantLock 在生产环境中有广泛的应用场景，特别是在复杂的并发情况下，它提供了更高级的同步控制和灵活性。下面是一些常见的应用场景：

1. 临界区保护：当多个线程需要同时访问某个共享资源时，可以使用 ReentrantLock 来保护临界区代码，确保只有一个线程可以进入临界区。这种情况下，ReentrantLock 提供了更细粒度的控制，可以灵活地指定哪些代码需要互斥访问。

2. 递归锁：在某些场景下，可能存在同一个线程递归调用某个方法，而该方法需要获取锁。ReentrantLock 的可重入性使得同一个线程可以多次获取该锁，保证线程的递归调用不会被阻塞，从而避免死锁的发生。

3. 公平锁和非公平锁：ReentrantLock 可以提供公平锁和非公平锁两种模式。公平锁按照线程的请求顺序来分配锁，保证先来先得的原则。非公平锁允许线程插队，当锁被释放时，等待时间最长的线程有机会先获取到锁。根据具体需求选择合适的锁模式。

4. 条件变量的使用：ReentrantLock 可以与条件变量（Condition）一起使用，实现更复杂的线程通信和等待/通知机制。条件变量可以将线程分组，使得线程可以选择性地等待或被唤醒，从而更精细地控制线程的执行顺序和并发操作。

5. 中断支持：ReentrantLock 提供了对线程中断的支持。当线程获取锁时，可以响应中断信号，即使线程在等待锁的过程中被中断，也可以通过捕获中断异常来及时退出，避免长时间的等待。

需要注意的是，ReentrantLock 是一种高级的同步工具，在使用时需要谨慎考虑锁的获取和释放，避免死锁和性能问题。在特定的应用场景下，通过合理地使用 ReentrantLock 可以提高系统的并发性能和线程安全性。

## 举例说明业务方面的使用场景

以下是一些常见的业务方面的使用场景，其中使用了 ReentrantLock：

1. 资源池管理：在连接池、线程池等资源池管理中，可以使用 ReentrantLock 来保护共享资源的访问。例如，在连接池中，当一个线程需要获取一个可用的连接时，它可以使用 ReentrantLock 来确保只有一个线程可以同时获取连接并进行操作，从而避免资源的竞争和冲突。

2. 缓存更新控制：在缓存更新的场景中，可能存在多个线程同时触发缓存的更新操作。为了避免多个线程同时更新缓存导致的数据不一致性，可以使用 ReentrantLock 来保护缓存的更新操作，确保同一时刻只有一个线程可以执行更新，而其他线程需要等待。

3. 计数器控制：在某些计数器的场景中，可能需要对计数进行原子操作并保证线程安全。例如，在网站的并发访问统计中，可以使用 ReentrantLock 来保护计数器的增加和减少操作，确保计数的准确性和一致性。

4. 并发任务调度：在并发任务调度的场景中，可能需要限制同时执行的任务数量。使用 ReentrantLock 可以实现对任务执行的互斥控制，确保同时执行的任务数量不超过指定的阈值。

5. 数据结构的线程安全：在某些需要线程安全的数据结构中，如线程安全的集合类、队列等，可以使用 ReentrantLock 来实现对数据结构的并发访问控制，从而保证数据的一致性和线程安全性。

这些场景只是举例，实际应用中的使用场景可能更加多样化和复杂化。在选择使用 ReentrantLock 时，需要结合具体业务需求和并发情况来进行评估和设计。

## 给我一个ReentrantLock例子，我来练习

当多个线程同时访问一个共享资源时，使用 ReentrantLock 可以实现线程安全的访问控制。下面是一个简单的示例，展示了如何使用 ReentrantLock 来保证线程安全访问一个计数器：

```java
javaCopy code
import java.util.concurrent.locks.ReentrantLock;

public class CounterExample {
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
        CounterExample counterExample = new CounterExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counterExample.increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final count: " + counterExample.getCount());
    }
}
```

在上面的例子中，`CounterExample` 类包含一个计数器和一个 ReentrantLock 对象。`increment()` 方法使用 `lock()` 获取锁，然后执行 `count++` 操作，最后使用 `unlock()` 释放锁。

在 `main()` 方法中，创建了两个线程分别执行 `increment()` 方法来增加计数器的值。通过使用 ReentrantLock，确保了每个线程在访问计数器时的互斥性，从而实现了线程安全的操作。

运行该示例代码后，你可以观察到最终的计数器值应该为 2000，因为每个线程都执行了1000次的增加操作。

# CountDownLatch

## CountDownLatch是什么

CountDownLatch 是 Java 并发包中提供的一种同步工具，用于协调多个线程之间的执行。它可以用于一个或多个线程等待一组事件的完成，然后再同时开始执行。

CountDownLatch 内部维护一个计数器，该计数器的初始值由用户指定。每当一个线程完成了一个事件，它可以调用 `countDown()` 方法将计数器减一。其他线程可以通过调用 `await()` 方法来等待计数器的值变为零，一旦计数器的值变为零，所有等待的线程将被唤醒，可以继续执行后续的操作。

CountDownLatch 的使用场景包括但不限于以下情况：

1. 主线程等待多个子线程完成：主线程创建并启动多个子线程执行任务，然后调用 `await()` 方法等待所有子线程完成任务后再继续执行。
2. 多个子线程等待主线程的信号：多个子线程同时等待主线程发出的信号，一旦主线程发出信号，所有子线程可以同时开始执行任务。
3. 并发测试：在并发测试中，可以使用 CountDownLatch 来协调测试线程和被测试线程的执行。测试线程等待被测试线程完成特定的操作后再开始断言和结果验证。
4. 并行任务的合并：将一个大任务拆分成多个子任务，多个子任务并行执行，然后使用 CountDownLatch 等待所有子任务完成后合并结果。

CountDownLatch 提供了一种简单而强大的线程协调机制，可以实现线程间的同步和等待。它的使用方式相对简单，适用于需要等待其他线程完成特定操作后再继续执行的场景。

## CountDownLatch的核心源码并解释

下面是一个简化版本的 CountDownLatch 的核心源码解释：

```
javaCopy code
public class CountDownLatch {
    private int count;

    public CountDownLatch(int count) {
        this.count = count;
    }

    public void await() throws InterruptedException {
        synchronized (this) {
            while (count > 0) {
                wait();
            }
        }
    }

    public void countDown() {
        synchronized (this) {
            if (count > 0) {
                count--;
                if (count == 0) {
                    notifyAll();
                }
            }
        }
    }
}
```

- `count` 是 CountDownLatch 内部维护的计数器，用于表示需要等待的事件数量。
- `await()` 方法用于阻塞当前线程，直到计数器变为零。在调用 `await()` 时，线程将进入等待状态，直到计数器减为零。如果计数器当前值大于零，则调用 `wait()` 方法使线程等待，直到其他线程调用 `countDown()` 方法将计数器减至零，并调用 `notifyAll()` 方法唤醒所有等待的线程。
- `countDown()` 方法用于将计数器减一。每次调用 `countDown()` 都会使计数器减一，当计数器变为零时，会唤醒所有等待的线程。如果计数器已经为零，则不会有任何影响。

CountDownLatch 的核心思想是，在一个或多个线程中等待一组事件的完成。线程调用 `await()` 方法后会被阻塞，直到计数器变为零。其他线程执行任务，并在完成各自任务后调用 `countDown()` 方法将计数器减一。当计数器减至零时，所有等待的线程都会被唤醒，继续执行后续的操作。

CountDownLatch 在并发编程中常用于线程间的协调与同步，特别适用于一组线程需要等待另一组线程完成某些操作后再同时开始执行的场景。例如，主线程需要等待所有子线程完成任务后再进行下一步操作，或者多个子线程需要等待主线程的信号才能同时开始执行任务等等。

## CountDownLatch的实际应用场景

CountDownLatch 在实际应用中有多种场景和用途。以下是一些常见的实际应用场景：

1. 启动器：在某些系统或应用程序中，可能需要等待多个组件或服务都启动完成后再启动主程序。可以使用 CountDownLatch 来实现等待启动器，主程序在启动前等待所有组件或服务的启动完成。
2. 数据加载：在某些系统中，需要在应用程序启动时加载一些初始数据，而这些数据的加载可能需要一定的时间。可以使用 CountDownLatch 来等待数据加载完成后再继续应用程序的执行。
3. 多线程协调：当一个任务需要分配给多个线程同时执行时，可以使用 CountDownLatch 来等待所有线程完成任务后再进行后续操作。例如，当需要对一个大型文件进行处理时，可以将文件分成多个块，每个线程处理一块，然后使用 CountDownLatch 等待所有线程完成处理后再进行合并操作。
4. 性能测试：在进行性能测试时，可能需要等待一组并发请求都完成后再进行结果统计和分析。可以使用 CountDownLatch 来等待所有并发请求完成后再进行后续的性能分析和报告生成。
5. 并行任务的合并：当一个任务需要分解成多个子任务并行执行时，可以使用 CountDownLatch 来等待所有子任务完成后合并结果。例如，将一个大型图像处理任务分成多个子任务进行并行处理，然后使用 CountDownLatch 来等待所有子任务完成后合并生成最终的图像结果。
6. 等待外部事件：在某些场景下，可能需要等待外部事件的发生后再进行下一步操作。可以使用 CountDownLatch 来等待外部事件的发生，一旦外部事件发生，就可以继续执行后续的操作。

CountDownLatch 的实际应用场景非常广泛，特别适用于需要等待多个线程或事件完成后再进行下一步操作的情况。它提供了一种简单而有效的线程协调机制，可以实现并发控制和同步等待。

## CountDownLatch的业务应用场景

CountDownLatch 在业务应用中有多种实际场景，下面是一些常见的业务应用场景：

1. 并行任务等待：在某些业务场景中，需要并行执行多个任务，并且需要等待所有任务完成后再执行后续操作。例如，在一个电商平台的订单处理系统中，可以将订单拆分为多个子任务，每个子任务负责处理一部分订单，使用 CountDownLatch 等待所有子任务处理完成后再进行下一步操作，如生成订单报表。
2. 数据导入和处理：在数据导入和处理的业务中，可能需要等待数据导入完成后再进行后续的处理。例如，在一个数据分析平台中，用户上传数据文件后，可以使用 CountDownLatch 等待数据导入操作完成后再进行数据清洗、转换和分析等后续处理操作。
3. 流程控制：在复杂的业务流程中，可能需要等待多个子任务完成后才能进行下一步操作。例如，在一个工作流引擎中，可以使用 CountDownLatch 来等待所有参与者完成自己的工作任务后，再进行流程的下一步操作。
4. 批量操作的并发控制：在批量操作的业务中，可能需要等待多个并发操作完成后再进行下一批操作。例如，在一个电信运营商的账单生成系统中，可以将账单生成任务分成多个批次并发执行，每个批次由多个线程处理，使用 CountDownLatch 等待每个批次的线程全部完成后再开始下一批次的账单生成操作。
5. 多阶段任务协同：在一些业务场景中，可能存在多阶段的任务协同执行。每个阶段的任务需要等待前一阶段的任务完成后才能开始执行。使用 CountDownLatch 可以在每个阶段的任务中等待前一阶段的任务完成，从而实现多阶段任务的协同执行。

这些是一些常见的业务应用场景，其中使用了 CountDownLatch 来实现线程协调和同步等待。具体的应用场景会根据业务需求的不同而有所差异，使用 CountDownLatch 可以实现更灵活、高效的线程协同和并发控制。

## 给我一个CountDownLatch例子，我来练习

当多个运动员准备参加比赛时，比赛不能开始直到所有运动员都准备好。这是一个典型的使用 CountDownLatch 的场景。

```java
javaCopy code
import java.util.concurrent.CountDownLatch;

public class RaceExample {
    public static void main(String[] args) {
        int numberOfRunners = 5;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch readySignal = new CountDownLatch(numberOfRunners);
        CountDownLatch finishSignal = new CountDownLatch(numberOfRunners);

        // 创建并启动运动员线程
        for (int i = 1; i <= numberOfRunners; i++) {
            Thread runner = new Thread(new Runner(startSignal, readySignal, finishSignal, i));
            runner.start();
        }

        try {
            System.out.println("比赛即将开始...");
            Thread.sleep(2000); // 模拟准备时间
            startSignal.countDown(); // 发出开始信号
            System.out.println("比赛开始！");

            finishSignal.await(); // 等待所有运动员跑完
            System.out.println("比赛结束！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Runner implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch readySignal;
        private final CountDownLatch finishSignal;
        private final int runnerNumber;

        public Runner(CountDownLatch startSignal, CountDownLatch readySignal, CountDownLatch finishSignal, int runnerNumber) {
            this.startSignal = startSignal;
            this.readySignal = readySignal;
            this.finishSignal = finishSignal;
            this.runnerNumber = runnerNumber;
        }

        @Override
        public void run() {
            try {
                System.out.println("运动员 " + runnerNumber + " 准备好了");
                readySignal.countDown(); // 准备好了，通知等待的线程
                startSignal.await(); // 等待开始信号

                // 运动员开始跑步
                System.out.println("运动员 " + runnerNumber + " 开始跑步");
                Thread.sleep((long) (Math.random() * 5000)); // 模拟跑步过程
                System.out.println("运动员 " + runnerNumber + " 跑步结束");

                finishSignal.countDown(); // 跑步结束，通知主线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

在上面的例子中，有5名运动员参加比赛。`startSignal` 是用于发出比赛开始信号的 CountDownLatch，初始计数为1。`readySignal` 是每个运动员准备好的 CountDownLatch，初始计数为5。`finishSignal` 是每个运动员跑完的 CountDownLatch，初始计数为5。

当主线程准备好开始比赛时，它会调用 `startSignal.countDown()` 发出开始信号，然后等待所有运动员跑完比赛。每个运动员会在准备好后调用 `readySignal.countDown()` 通知主线程，然后等待开始信号调用 `startSignal.await()`。当开始信号发出后，所有运动员同时开始跑步，每个运动员跑完后

# semaphore

## semaphore是什么

Semaphore 是 Java 并发包中提供的一种同步工具，用于控制对资源的访问数量。它基于计数器的原理，用于限制同时访问某个资源的线程数量。

Semaphore 维护了一个计数器，该计数器表示当前可用的许可数。线程在访问资源之前必须先获取许可，如果许可数大于 0，则线程可以获取许可并减少许可数；如果许可数为 0，则线程必须等待，直到有其他线程释放许可为止。

Semaphore 提供了两个主要的操作：

- `acquire()`: 获取许可。如果许可数大于 0，则线程获取许可并将许可数减一；如果许可数为 0，则线程被阻塞，直到有其他线程释放许可。
- `release()`: 释放许可。线程调用该方法将许可数加一，并唤醒一个等待的线程（如果有）。

Semaphore 的典型应用场景包括：

- 控制资源的并发访问数量：例如，数据库连接池中可以使用 Semaphore 来限制同时获取连接的线程数量。
- 实现有限资源池：例如，线程池中的任务可以使用 Semaphore 来限制可并发执行的任务数量。
- 解决生产者-消费者问题：通过 Semaphore 可以控制生产者和消费者的并发执行，确保生产者不会过度生产或消费者不会过度消费。

Semaphore 是一个非常有用的并发工具，可以帮助控制并发访问资源的数量，避免资源的过度竞争和冲突。

## semaphore的核心源码并解释

Semaphore 是 Java 并发包中的一个类，其核心源码实现如下：

```java
public class Semaphore {
    private final Sync sync;

    public Semaphore(int permits) {
        sync = new NonfairSync(permits);
    }

    public Semaphore(int permits, boolean fair) {
        sync = fair ? new FairSync(permits) : new NonfairSync(permits);
    }

    public void acquire() throws InterruptedException {
        sync.acquireShared(1);
    }

    public void release() {
        sync.releaseShared(1);
    }

    private static abstract class Sync extends AbstractQueuedSynchronizer {
        Sync(int permits) {
            setState(permits);
        }

        final int getPermits() {
            return getState();
        }

        final int nonfairTryAcquireShared(int acquires) {
            for (;;) {
                int available = getState();
                int remaining = available - acquires;
                if (remaining < 0 || compareAndSetState(available, remaining)) {
                    return remaining;
                }
            }
        }

        protected final boolean tryReleaseShared(int releases) {
            for (;;) {
                int current = getState();
                int next = current + releases;
                if (next < current) { // overflow
                    throw new Error("Maximum permit count exceeded");
                }
                if (compareAndSetState(current, next)) {
                    return true;
                }
            }
        }
    }

    private static final class NonfairSync extends Sync {
        NonfairSync(int permits) {
            super(permits);
        }

        protected int tryAcquireShared(int acquires) {
            return nonfairTryAcquireShared(acquires);
        }
    }

    private static final class FairSync extends Sync {
        FairSync(int permits) {
            super(permits);
        }

        protected int tryAcquireShared(int acquires) {
            for (;;) {
                if (hasQueuedPredecessors()) {
                    return -1;
                }
                int available = getState();
                int remaining = available - acquires;
                if (remaining < 0 || compareAndSetState(available, remaining)) {
                    return remaining;
                }
            }
        }
    }
}
```

Semaphore 类的核心实现是通过继承 AbstractQueuedSynchronizer（AQS）类来实现的。AQS 提供了同步器的基本功能和框架，Semaphore 则在此基础上实现了信号量的特定功能。

Semaphore 类中有两个静态内部类 `NonfairSync` 和 `FairSync`，分别用于非公平模式和公平模式的信号量。它们都继承了 `Sync` 类，`Sync` 类是 Semaphore 实际同步的核心类，继承了 AQS。

在 `Sync` 类中，通过维护一个状态变量 `state` 来表示可用的许可数量。`Sync` 的构造函数初始化了初始许可数量。`tryAcquireShared` 方法用于尝试获取许可，`tryReleaseShared` 方法用于释放许可。

在 `NonfairSync` 中，`tryAcquireShared` 方法使用了非公平的方式尝试获取许可。它首先尝试减少许可数量，如果剩余的许可数量小于 0 或者成功减少许可数量，则返回剩余的许可数量。

在 `FairSync` 中，`tryAcquireShared` 方法使用了公平的方式尝试获取许可。它首先检查是否有排在自己前面的等待线程，如果有，则返回 -1 表示获取许可失败。如果没有排在自己前面的等待线程，则尝试减少许可数量，如果剩余的许可数量小于 0 或者成功减少许可数量，则返回剩余的许可数量。

Semaphore 类提供了 `acquire()` 和 `release()` 方法，分别用于获取和释放许可。`acquire()` 方法会调用 `sync.acquireShared(1)`，而 `release()` 方法会调用 `sync.releaseShared(1)`，实际上是通过调用 AQS 的相应方法来实现的。

Semaphore 的核心思想是使用 AQS 提供的同步功能和状态管理来实现对许可的控制和管理。通过控制许可数量，Semaphore 能够限制同时访问资源的线程数量。

## 应用场景有哪些

Semaphore 在实际应用中有很多场景，下面列举几个常见的应用场景：

1. 限流：Semaphore 可以用来控制同时访问某个资源的线程数量，从而实现对系统的流量进行限制。比如，一个接口每秒只能处理一定数量的请求，可以使用 Semaphore 来限制并发访问的请求数量，防止系统被过多的请求压垮。
2. 资源池管理：Semaphore 可以用来管理有限资源的访问，比如连接池。在数据库连接池、线程池等场景中，可以使用 Semaphore 来控制可用资源的数量，确保资源的合理分配和管理。
3. 并发任务控制：Semaphore 可以用来控制并发执行的任务数量。在某些场景下，需要控制同时执行的任务数量，比如并发下载文件、并发处理数据等。Semaphore 可以用来限制并发执行的任务数量，避免系统资源被过度占用。
4. 解决生产者-消费者问题：Semaphore 可以用来解决生产者-消费者模型中的同步问题。比如，生产者线程和消费者线程之间需要进行同步操作，确保生产者不会过度生产或消费者不会过度消费。Semaphore 可以控制生产者和消费者的并发执行数量，实现同步和平衡。
5. 多线程协调：Semaphore 可以用来实现多个线程之间的协调和通信。比如，某个线程需要等待其他线程执行完毕后才能继续执行，可以使用 Semaphore 来进行线程的等待和唤醒操作，实现线程之间的同步和协调。

这些只是 Semaphore 的一些常见应用场景，实际上，Semaphore 在并发编程中的应用非常广泛，可以根据具体的业务需求进行灵活的应用和扩展。

## 业务场景有哪些

Semaphore 在实际业务场景中有很多应用，以下是一些常见的业务场景：

1. 并发限制：在某些情况下，需要限制同时执行某个操作的线程数量。例如，一个文件上传服务器需要限制同时上传的文件数量，可以使用 Semaphore 控制并发上传的线程数量。
2. 资源池管理：在连接池、线程池等场景中，Semaphore 可以用于管理可用资源的数量。例如，数据库连接池可以使用 Semaphore 来限制可用连接的数量，确保连接的合理分配和管理。
3. 任务调度和执行：在任务调度和执行的场景中，Semaphore 可以控制并发执行的任务数量。例如，一个定时任务调度器需要限制同时执行的任务数量，可以使用 Semaphore 进行任务调度和控制。
4. 缓存管理：在缓存管理中，Semaphore 可以用于控制对缓存资源的并发访问。例如，一个缓存系统中，某个缓存块只能同时被一个线程访问，可以使用 Semaphore 控制对该缓存块的访问数量。
5. 控制流量：在网络通信或接口访问中，需要限制请求的并发量以保护系统免受过多的负载。Semaphore 可以用于控制请求的并发数量，限制流量并保持系统的稳定性。
6. 生产者-消费者模型：在生产者-消费者模型中，Semaphore 可以用于实现生产者和消费者之间的协调。例如，生产者需要等待消费者处理完某些数据后才能继续生产，可以使用 Semaphore 进行线程间的等待和通信。

这些是 Semaphore 在业务场景中常见的应用之一，具体的应用场景取决于业务需求和设计。Semaphore 的灵活性和可扩展性使其适用于各种并发编程场景，能够提供并发控制和资源管理的解决方案。

Semaphore的业务场景有哪些



ChatGPT



Semaphore 在实际业务场景中有很多应用，以下是一些常见的业务场景：

1. 并发限制：在某些情况下，需要限制同时执行某个操作的线程数量。例如，一个文件上传服务器需要限制同时上传的文件数量，可以使用 Semaphore 控制并发上传的线程数量。
2. 资源池管理：在连接池、线程池等场景中，Semaphore 可以用于管理可用资源的数量。例如，数据库连接池可以使用 Semaphore 来限制可用连接的数量，确保连接的合理分配和管理。
3. 任务调度和执行：在任务调度和执行的场景中，Semaphore 可以控制并发执行的任务数量。例如，一个定时任务调度器需要限制同时执行的任务数量，可以使用 Semaphore 进行任务调度和控制。
4. 缓存管理：在缓存管理中，Semaphore 可以用于控制对缓存资源的并发访问。例如，一个缓存系统中，某个缓存块只能同时被一个线程访问，可以使用 Semaphore 控制对该缓存块的访问数量。
5. 控制流量：在网络通信或接口访问中，需要限制请求的并发量以保护系统免受过多的负载。Semaphore 可以用于控制请求的并发数量，限制流量并保持系统的稳定性。
6. 生产者-消费者模型：在生产者-消费者模型中，Semaphore 可以用于实现生产者和消费者之间的协调。例如，生产者需要等待消费者处理完某些数据后才能继续生产，可以使用 Semaphore 进行线程间的等待和通信。

这些是 Semaphore 在业务场景中常见的应用之一，具体的应用场景取决于业务需求和设计。Semaphore 的灵活性和可扩展性使其适用于各种并发编程场景，能够提供并发控制和资源管理的解决方案。

## 给我一个Semaphore例子，我来练习

下面是一个使用 Semaphore 的示例代码，演示了如何控制并发访问资源的数量

```java
javaCopy code
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final int THREAD_COUNT = 10;
    private static final int MAX_CONCURRENT_ACCESS = 5;

    private static Semaphore semaphore = new Semaphore(MAX_CONCURRENT_ACCESS);

    public static void main(String[] args) {
        for (int i = 1; i <= THREAD_COUNT; i++) {
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
                System.out.println("Worker " + workerId + " is trying to access the resource");
                semaphore.acquire();
                System.out.println("Worker " + workerId + " has acquired the resource");

                // 模拟对资源的访问
                Thread.sleep(2000);

                System.out.println("Worker " + workerId + " is releasing the resource");
                semaphore.release();
                System.out.println("Worker " + workerId + " has released the resource");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

在上面的代码中，定义了一个最大并发访问数量为 5 的 Semaphore 对象 `semaphore`。

`Worker` 类实现了 `Runnable` 接口，在 `run()` 方法中，线程首先尝试通过 `semaphore.acquire()` 获取许可。如果许可数大于 0，则线程可以获取许可并减少许可数；如果许可数为 0，则线程将被阻塞，直到有其他线程释放许可。

线程成功获取许可后，模拟对资源的访问（在这里用 `Thread.sleep(2000)` 模拟）。然后通过 `semaphore.release()` 方法释放许可，许可数加一，并唤醒等待的线程（如果有）。

运行该示例代码时，你会看到最多同时有 5 个线程在访问资源，其他线程将被阻塞直到有许可可用。

你可以尝试修改 `MAX_CONCURRENT_ACCESS` 的值，观察并发访问的数量是否受到限制。