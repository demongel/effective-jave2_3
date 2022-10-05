package com.shakespace.effectivejava.edition3.chapter11;

/**
 * 不要依赖于线程调度器
 * 当许多线程可以运行时，线程调度器决定哪些线程可以运行以及运行多长时间。
 * 任何合理的操作系统都会尝试公平地做出这个决定，但是策略可能会有所不同。
 * 因此，编写良好的程序不应该依赖于此策略的细节。
 * 任何依赖线程调度器来保证正确性或性能的程序都可能是不可移植的。
 * 1. 编写健壮、响应快、可移植程序的最佳方法是确保可运行线程的平均数量不显著大于处理器的数量。这使得线程调度器几乎没有选择
 * 2. 如果线程没有做有用的工作，它们就不应该运行。
 * 3. 通过调用 Thread.yield 来「修复」程序 你也许能勉强让程序运行起来，但它是不可移植的。
 * 在一个 JVM 实现上提高性能的相同的 yield 调用，在一些JVM 实现上可能会使性能变差，而在其他 JVM 实现上可能没有任何影响。
 * Thread.yield 没有可测试的语义。 更好的做法是重构应用程序，以减少并发运行线程的数量。
 * 4. 另一个相关的技术是调整线程优先级，类似的警告也适用于此技术，即，线程优先级是 Java 中最不可移植的特性之一。
 * 通过调整线程优先级来调优应用程序的响应性并非不合理，但很少情况下是必要的，而且不可移植。
 * 试图通过调整线程优先级来解决严重的活性问题是不合理的。
 *
 * <p>
 * 总之，不要依赖线程调度器来判断程序的正确性。生成的程序既不健壮也不可移植。
 * 因此，不要依赖 Thread.yield 或线程优先级。这些工具只是对调度器的提示。
 * 线程优先级可以少量地用于提高已经工作的程序的服务质量，但绝不应该用于「修复」几乎不能工作的程序。
 */
public class E084_Scheduler {
}

// Awful CountDownLatch implementation - busy-waits incessantly!
class SlowCountDownLatch {
    private int count;

    public SlowCountDownLatch(int count) {
        if (count < 0)
            throw new IllegalArgumentException(count + " < 0");
        this.count = count;
    }

    public void await() {
        while (true) {
            synchronized (this) {
                if (count == 0)
                    return;
            }
        }
    }

    public synchronized void countDown() {
        if (count != 0)
            count--;
    }
}
