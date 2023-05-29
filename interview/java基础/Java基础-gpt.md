# 异常

## Java异常的继承关系

在Java中，异常类之间存在继承关系，形成了一个异常类的继承体系。下面是Java异常类的继承关系示意图：

```java
                Throwable
                  /      \
        Exception        Error
          /        \
   RuntimeException   其他可检查异常
```

在上述继承关系中，Throwable是所有异常类的根类，它是一个抽象类。Throwable类定义了异常的基本行为和属性，包括异常的消息、栈轨迹等。

Exception类是Throwable的子类，它表示可检查异常。Exception类下面又分为两个主要的子类：

1. RuntimeException类：RuntimeException及其子类表示不可检查异常，也称为运行时异常。这些异常通常是由程序错误引起的，例如空指针引用（NullPointerException）、数组越界（ArrayIndexOutOfBoundsException）等。
2. 其他可检查异常：除了RuntimeException类之外，Exception的其他子类表示可检查异常。这些异常通常是由外部环境或方法的调用者引起的，例如文件读写错误（IOException）、数据库操作错误（SQLException）等。这些异常类需要在代码中显式处理，否则编译器会报错。

Error类是Throwable的另一个子类，它表示错误。错误通常是由系统级问题引起的，例如内存溢出（OutOfMemoryError）、栈溢出（StackOverflowError）等。与异常不同，错误一般不需要我们处理，而是由Java虚拟机或操作系统层面进行处理。

通过理解异常类的继承关系，我们可以更好地组织和处理异常，根据需要选择合适的异常类型进行捕获和处理，提高程序的健壮性和可靠性。

## 可检查异常和不可检查异常

可检查异常（Checked Exception）和不可检查异常（Unchecked Exception）是Java中异常的两个主要分类。

1. 可检查异常（Checked Exception）： 可检查异常是指在编译时就需要进行处理的异常。这些异常通常是由外部环境或方法的调用者引起的，例如文件读写错误（IOException）、数据库操作错误（SQLException）等。可检查异常是Exception类及其子类的实例。

特点：

- 可检查异常需要在代码中显式处理，否则编译器会报错。
- 处理可检查异常的方式有两种：
  - 使用try-catch语句块捕获和处理异常。
  - 在方法签名中声明该异常的抛出，由方法的调用者来处理。

目的：

- 强制开发者处理潜在的错误情况，提高代码的健壮性和可靠性。
- 提示开发者在程序中考虑异常情况的处理，避免异常被忽略或导致不可预料的结果。

1. 不可检查异常（Unchecked Exception）： 不可检查异常是指在运行时可能出现的异常，也称为运行时异常（Runtime Exception）。这些异常通常是由程序错误引起的，例如空指针引用（NullPointerException）、数组越界（ArrayIndexOutOfBoundsException）等。不可检查异常是RuntimeException类及其子类的实例。

特点：

- 不可检查异常不需要在代码中显式处理，编译器不会强制要求进行处理。
- 尽管不需要显式处理，但我们仍然应该通过编写健壮的代码来避免这些异常的发生。

目的：

- 标识程序中的逻辑错误或不一致，提示开发者修复代码。
- 强调开发者应该预防和避免不可检查异常的发生。

需要注意的是，可检查异常和不可检查异常都属于Exception类的子类，它们都是Throwable类的实例。在异常处理中，根据异常的类型和特点，我们可以选择合适的方式来处理异常，提高程序的可靠性和稳定性。

## 常见的可检查异常和不可检查异常和Error有哪些

常见的可检查异常、不可检查异常和Error有以下示例：

1. 常见的可检查异常（Checked Exception）：

- IOException：文件读写错误。
- SQLException：数据库操作错误。
- ClassNotFoundException：无法找到指定的类。
- InterruptedException：线程被中断。
- NoSuchMethodException：找不到指定的方法。

1. 常见的不可检查异常（Unchecked Exception，也称为运行时异常）：

- NullPointerException：空指针引用。
- ArrayIndexOutOfBoundsException：数组越界。
- IllegalArgumentException：非法参数。
- ClassCastException：类转换异常。
- ArithmeticException：算术运算异常。

1. 常见的Error：

- OutOfMemoryError：内存溢出。
- StackOverflowError：栈溢出。
- NoClassDefFoundError：无法找到类定义。
- ExceptionInInitializerError：静态初始化器错误。
- LinkageError：链接错误。

需要注意的是，以上只是常见的异常和错误示例，实际上Java中还有很多其他类型的异常和错误。在开发过程中，我们可能会遇到不同的异常和错误情况，需要根据具体的问题进行处理。无论是可检查异常、不可检查异常还是Error，对于出现的异常情况，我们都应该根据具体情况进行适当的处理或修复，以保证程序的稳定性和可靠性。

## 小结

异常大致分为两类：可检和不可检异常。可检异常是Execption及其子类的实现，常见有空指针、数组越界、非法参数等，使用时要try catch，而不可检查异常是RuntimeExecption及其子类的实现，常见的有线程中断异常、IO异常、找不到类/方法等，使用时不用try catch。

与异常区别的是Error,是Throwable的子类及其实现，常见的有栈/内存溢出等。

但是异常和Error都是Throwable类的子类及其及其实现。

# Java线程池

## Java线程池是什么

Java线程池是一种用于管理和重用线程的机制。在多线程编程中，创建和销毁线程是一项开销较大的操作，而且如果线程数量不受控制，可能会导致系统资源耗尽。线程池通过预先创建一组线程，并在需要时重用它们，以提高性能和资源利用率。

Java线程池框架在java.util.concurrent包中提供，并包含了一些类和接口，用于创建和管理线程池。主要的类包括：

1. Executor：一个接口，定义了线程池执行任务的方法。
2. ExecutorService：继承自Executor接口，提供了更多的方法来管理线程池，比如提交任务、关闭线程池等。
3. ThreadPoolExecutor：实现了ExecutorService接口，是Java线程池的主要实现类，提供了丰富的配置选项来控制线程池的行为，如核心线程数、最大线程数、线程空闲时间等。
4. ScheduledExecutorService：继承自ExecutorService接口，用于支持延迟任务和周期性任务的执行。

通过使用线程池，可以将任务提交给线程池执行，线程池会根据配置的线程数量和任务负载来调度线程的执行。线程池会自动管理线程的生命周期，包括创建线程、执行任务、线程空闲时的保持活动状态、线程过多时的重用和销毁等。这种方式可以避免频繁地创建和销毁线程，从而提高系统的性能和响应能力。

使用Java线程池可以有效地管理多线程任务，控制线程数量，防止资源耗尽，并提高应用程序的性能和可伸缩性。

## Executor，ExecutorService，ThreadPoolExecutor和ScheduledExecutorService的核心源码，并解释

以下是Java中Executor、ExecutorService、ThreadPoolExecutor和ScheduledExecutorService的核心源码解释：

1. Executor 接口源码解释：

```java
public interface Executor {
    void execute(Runnable command);
}
```

Executor接口是一个简单的接口，只有一个execute方法，用于执行给定的命令（Runnable）。它定义了最基本的任务执行机制，可以将任务提交给执行器以异步执行。

1. ExecutorService 接口源码解释：

```java
javaCopy code
public interface ExecutorService extends Executor {
    void shutdown();
    List<Runnable> shutdownNow();
    boolean isShutdown();
    boolean isTerminated();
    boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
    <T> Future<T> submit(Callable<T> task);
    <T> Future<T> submit(Runnable task, T result);
    Future<?> submit(Runnable task);
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException;
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException;
    <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;
    <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
}
```

ExecutorService接口扩展了Executor接口，并提供了更多的方法来管理和控制线程池。除了execute方法外，它还提供了提交任务、关闭线程池、判断线程池状态等方法。

1. ThreadPoolExecutor 类源码解释：

```java
javaCopy code
public class ThreadPoolExecutor extends AbstractExecutorService {
    // 构造方法
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        // 省略构造方法实现
    }

    // 省略了其他方法和字段

    // execute方法实现
    public void execute(Runnable command) {
        // 省略部分代码
        if (workerCountOf(ctl.get()) < corePoolSize) {
            if (addWorker(command, true))
                return;
        }
        if (isRunning() && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (!isRunning() && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }

    // 省略了其他方法和字段
}
```

ThreadPoolExecutor是实现ExecutorService接口的主要类。它提供了一个灵活的线程池实现，可以根据配置的参数来管理线程池的行为，如核心线程数、最大线程数、线程空闲时间等。

1. ScheduledExecutorService 接口源码解释：

```java
javaCopy code
public interface ScheduledExecutorService extends ExecutorService {
    ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);
    <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);
    ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);
    ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);
}
```

ScheduledExecutorService接口继承自ExecutorService接口，提供了支持延迟任务和周期性任务的执行。它定义了schedule、scheduleAtFixedRate和scheduleWithFixedDelay等方法，用于按计划执行任务。

这些核心源码解释了Java线程池的基本实现机制和提供的功能。通过这些类和接口，可以创建和管理线程池，提交任务，并控制线程池的行为。

## 常见的两种创建线程池的方式

在Java中，可以使用以下方式来创建线程池：

1. 使用ThreadPoolExecutor类手动创建：可以通过实例化ThreadPoolExecutor类来创建线程池，并指定核心线程数、最大线程数、线程空闲时间等参数来配置线程池的行为。例如：

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    corePoolSize, // 核心线程数
    maximumPoolSize, // 最大线程数
    keepAliveTime, // 线程空闲时间
    TimeUnit.SECONDS, // 空闲时间单位
    new LinkedBlockingQueue<Runnable>() // 任务队列
);
```

1. 使用Executors类提供的静态方法创建：Java提供了Executors类，该类提供了一些静态方法用于创建不同类型的线程池。例如：

- newFixedThreadPool(int nThreads)：创建固定大小的线程池，核心线程数和最大线程数都是指定的数量。
- newCachedThreadPool()：创建可缓存的线程池，核心线程数为0，最大线程数为整数最大值，线程空闲时间为60秒。
- newSingleThreadExecutor()：创建单线程的线程池，只有一个核心线程，任务按照顺序执行。
- newScheduledThreadPool(int corePoolSize)：创建支持定时和周期性任务的线程池，核心线程数为指定数量。

```java
ExecutorService executor = Executors.newFixedThreadPool(nThreads);
```

## Exectors是什么，Exectors提供了哪些默认线程池

我发现之前给出的回答有误，请接受我诚挚的道歉。在Java中，正确的类名是`Executors`，而不是`Exectors`。

`Executors`是Java提供的一个工具类，位于`java.util.concurrent`包中，用于创建和管理线程池。它提供了一些静态方法来创建不同类型的线程池。

以下是`Executors`提供的一些默认线程池：

1. `newFixedThreadPool(int nThreads)`：创建固定大小的线程池。它会创建一个具有固定线程数的线程池，当线程池中的线程空闲时，它们会保持存活状态，等待新的任务。如果任务数量超过线程池大小，任务会被放入任务队列等待执行。
2. `newCachedThreadPool()`：创建可缓存的线程池。该线程池的线程数量是根据需要进行动态调整的。如果有空闲的线程可用，会重用它们；如果没有可用的线程，则会创建新的线程。线程空闲一段时间后会被回收，从而适应任务数量的变化。
3. `newSingleThreadExecutor()`：创建单线程的线程池。该线程池只有一个核心线程，用于顺序执行所有任务。如果线程因异常而终止，会创建一个新的线程来替代它。
4. `newScheduledThreadPool(int corePoolSize)`：创建支持定时和周期性任务的线程池。它可以执行延迟任务和周期性任务。线程池大小固定为指定的核心线程数，可以按需创建额外的线程来处理任务。

这些默认线程池通过`Executors`类提供了方便的创建方式，适用于不同的任务调度和执行需求。可以根据具体的场景选择合适的线程池类型来创建。

## ThreadPoolExecutor创建线程池时，各个参数的详细解释

当使用 `ThreadPoolExecutor` 创建线程池时，可以通过构造方法来传递不同的参数来配置线程池的行为。以下是各个参数的详细解释：

1. `corePoolSize`（核心线程数）：指定线程池中的核心线程数量。核心线程会一直存活，即使没有任务需要执行。默认情况下，核心线程会一直保持活动状态，即使线程处于空闲状态。
2. `maxPoolSize`（最大线程数）：指定线程池中允许创建的最大线程数量。当线程池的任务队列已满且核心线程数已达到上限时，线程池会创建新的线程来执行任务，直到达到最大线程数。
3. `keepAliveTime`（线程空闲时间）：指定线程的空闲时间，即当线程处于空闲状态并且当前线程池中的线程数大于核心线程数时，超过该空闲时间的线程将被终止并从线程池中移除。
4. `unit`（时间单位）：指定线程空闲时间的单位，可以是秒、毫秒、微秒等等。
5. `workQueue`（任务队列）：用于保存等待执行的任务的队列。线程池的任务队列可以选择不同的实现，如 `ArrayBlockingQueue`、`LinkedBlockingQueue`、`SynchronousQueue` 等。根据具体需求和场景，可以选择不同的队列类型。
6. `threadFactory`（线程工厂）：用于创建新线程的工厂类。通过自定义的线程工厂，可以对线程进行一些额外的配置，如线程的名称、优先级等。
7. `handler`（拒绝策略）：当线程池无法接受新的任务时，用于处理新任务的拒绝策略。线程池的任务队列已满且线程池中的线程数已达到最大线程数时，会触发拒绝策略。常用的拒绝策略包括抛出异常、丢弃任务、丢弃最早的任务和将任务回退给调用者等。

这些参数的配置可以根据具体的需求来进行调整，以满足业务场景的要求。需要根据任务的特性、并发量、资源限制等因素来权衡参数的设置，以达到最佳性能和资源利用的平衡。

请注意，`ThreadPoolExecutor` 还提供了一些其他的构造方法和方法来支持更多的配置选项和操作，如设置线程池的拒绝策略、预启动核心线程等。可以根据具体的需求查阅相关文档来进行更深入的学习和使用。

## 给我一个使用ThreadPoolExecutor创建线程池的业务例子，我来练习

当使用 `ThreadPoolExecutor` 创建线程池时，可以根据实际业务需求来定义线程池的行为。以下是一个简单的示例，假设我们有一个任务列表，需要使用线程池来并发执行这些任务：

```java
javaCopy code
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {
        // 创建任务列表
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1"));
        tasks.add(new Task("Task 2"));
        tasks.add(new Task("Task 3"));
        tasks.add(new Task("Task 4"));
        tasks.add(new Task("Task 5"));

        // 创建 ThreadPoolExecutor
        int corePoolSize = 3;
        int maxPoolSize = 5;
        long keepAliveTime = 10;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                timeUnit,
                workQueue
        );

        // 提交任务给线程池执行
        for (Runnable task : tasks) {
            executor.execute(task);
        }

        // 关闭线程池
        executor.shutdown();
    }

    // 任务示例
    static class Task implements Runnable {
        private final String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Executing task: " + name);
            // 执行具体的业务逻辑
            // ...
        }
    }
}
```

在上述示例中，我们创建了一个包含 5 个任务的任务列表。然后，使用 `ThreadPoolExecutor` 创建了一个线程池，其中核心线程数为 3，最大线程数为 5，线程空闲时间为 10 秒，使用有界的任务队列 `ArrayBlockingQueue`。

接下来，我们使用 `execute()` 方法将任务提交给线程池执行。每个任务都会被分配一个线程来执行。在任务的 `run()` 方法中，可以编写具体的业务逻辑。

最后，我们调用 `shutdown()` 方法关闭线程池，等待所有任务执行完毕。

请注意，此示例仅用于演示如何使用 `ThreadPoolExecutor` 创建线程池和提交任务，并没有涉及实际的业务逻辑。您可以根据自己的需求和业务场景，修改和扩展示例代码。