package com.shakespace.effectivejava.edition3.chapter11;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * 并发工具优先于wait和notify
 * 几乎没有理由再使用wait notify
 * 自 Java 5 以来，该平台提供了更高级别的并发实用工具，可以执行以前必须在 wait 和 notify 上手工编写代码的操作。
 * 考虑到正确使用 wait 和 notify 的困难，你应该使用更高级别的并发实用工具
 *
 * <p>
 * java.util.concurrent 中级别较高的实用工具可分为三类：
 * 1. Executor Framework，Item-80 简要介绍了该框架；
 * 2. 并发集合[Concurrent Collection]；
 * 3. 同步器[Synchronizer]。
 *
 * <p>
 * 本条目简要介绍并发集合和同步器。
 * 1. 并发集合是标准集合接口，如 List、Queue 和 Map 的高性能并发实现。
 * 为了提供高并发性，这些实现在内部管理它们自己的同步（Item-79）。因此，不可能从并发集合中排除并发活动；锁定它只会使程序变慢。
 * 2. 因为不能排除并发集合上的并发活动，所以也不能原子地组合对它们的方法调用。 【多个操作不是原子操作，所以不能直接组合】
 * 因此，并发集合接口配备了依赖于状态的修改操作，这些操作将多个基本操作组合成单个原子操作。
 *
 * <p>
 * 发集合使得同步集合在很大程度上过时。例如，使用 ConcurrentHashMap 而不是 Collections.synchronizedMap。
 * 只要用并发 Map 替换同步 Map 就可以显著提高并发应用程序的性能。
 *
 *
 * <p> 同步器
 * 同步器是允许线程彼此等待的对象，允许它们协调各自的活动。
 * 最常用的同步器是 CountDownLatch 和 Semaphore。
 * 较不常用的是 CyclicBarrier 和 Exchanger。
 * 最强大的同步器是 Phaser。
 * <p>
 * <p>
 * // notify 和 wait 使用
 * 始终使用 wait 习惯用法，即循环来调用 wait 方法；永远不要在循环之外调用它。 循环用于在等待之前和之后测试条件。
 * 在等待之前测试条件，如果条件已经存在，则跳过等待，以确保活性。
 * 如果条件已经存在，并且在线程等待之前已经调用了 notify（或 notifyAll）方法，则不能保证线程将从等待中唤醒。
 * <p>
 * 需要在等待之后再测试条件，如果条件不成立，则再次等待。
 * 如果线程在条件不成立的情况下继续执行该操作，它可能会破坏由锁保护的不变性。当条件不成立时，有一些理由唤醒线程：
 * 1. 另一个线程可能已经得到了锁，并且从一个线程调用notify方法那一刻起，到等待线程苏醒过来的这段时间里，得到锁的线程已经改变了受保护的状态
 * 2. 当条件不成立时，另一个线程可能意外地或恶意地调用 notify。类通过等待公共可访问的对象来暴露自己。公共可访问对象的同步方法中的任何 wait 都容易受到这个问题的影响。
 * 3. 通知线程在唤醒等待线程时可能过于「慷慨」。例如，即使只有一些等待线程的条件得到满足，通知线程也可能调用 notifyAll。
 * 4. 在没有通知的情况下，等待的线程可能（很少）醒来。这被称为伪唤醒 [POSIX, 11.4.3.6.1; Java9-api]。
 * <p>
 * 有时人们会说，应该始终使用 notifyAll。这是合理的、保守的建议。
 * 它总是会产生正确的结果，因为它保证你将唤醒需要唤醒的线程。
 * 你可能还会唤醒其他一些线程，但这不会影响程序的正确性。这些线程将检查它们正在等待的条件，如果发现为条件不满足，将继续等待。
 * 作为一种优化，如果在等待状态的所有线程都在等待相同的条件，并且每次只有一个线程可以从条件中获益，那么你可以选择调用 notify 而不是 notifyAll。
 * <p>
 * 与 java.util.concurrent 提供的高级语言相比，直接使用 wait 和 notify 就像使用「并发汇编语言」编程一样原始。
 * 在新代码中很少有理由使用 wait 和 notify。
 * 如果维护使用 wait 和 notify 的代码，请确保它始终使用标准的习惯用法，即在 while 循环中调用 wait。
 * 另外，notifyAll 方法通常应该优先于 notify。如果使用 notify，则必须非常小心以确保其活性。
 */
public class E081_WaitNotify {
    // Concurrent canonicalizing map atop ConcurrentMap - not optimal
    private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    private static final Object object = new Object();

    public static void main(String[] args) {
        // 始终使用 wait 习惯用法，即循环来调用 wait 方法；永远不要在循环之外调用它。 循环用于在等待之前和之后测试条件。
        synchronized (object) {
            while (true) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String intern(String s) {
        String previousValue = map.putIfAbsent(s, s);
        return previousValue == null ? s : previousValue;
    }

    // Concurrent canonicalizing map atop ConcurrentMap - faster!
    // ConcurrentHashMap 针对 get 等检索操作进行了优化。因此，只有在 get 表明有必要时，才值得首先调用 get 再调用 putIfAbsent:
    public static String intern2(String s) {
        String result = map.get(s);
        if (result == null) {
            result = map.putIfAbsent(s, s);
            if (result == null)
                result = s;
        }
        return result;
    }

    /**
     * 该方法使用三个倒计时锁。第一个是 ready，工作线程使用它来告诉 timer 线程它们什么时候准备好了。
     * 工作线程然后等待第二个锁存器，即 start。当最后一个工作线程调用 ready.countDown 时。
     * timer 线程记录开始时间并调用 start.countDown，允许所有工作线程继续。
     * 然后计时器线程等待第三个锁存器 done，直到最后一个工作线程运行完操作并调用 done.countDown。一旦发生这种情况，timer 线程就会唤醒并记录结束时间。
     *
     *
     * 传递给 time 方法的 executor 必须允许创建至少与给定并发级别相同数量的线程，否则测试将永远不会完成。
     * 这被称为线程饥饿死锁 [Goetz06, 8.1.1]。
     * 如果工作线程捕捉到 InterruptedException，它使用习惯用法 Thread.currentThread().interrupt() 重申中断，并从它的 run 方法返回。
     * 这允许执行程序按照它认为合适的方式处理中断。
     * 请注意，System.nanoTime 是用来计时的。对于间隔计时，始终使用 System.nanoTime 而不是 System.currentTimeMillis。
     * System.nanoTime 不仅更准确，而且更精确，而且不受系统实时时钟调整的影响。
     * 请注意，本例中的代码不会产生准确的计时，除非 action 做了相当多的工作，比如一秒钟或更长时间。准确的微基准测试是出了名的困难，最好是借助诸如 jmh 这样的专业框架来完成。
     *
     * 前面示例中的三个倒计时锁存器可以替换为单个 CyclicBarrier 或 Phaser 实例。生成的代码可能更简洁，但可能更难于理解。
     */
    /**
     * 假设你想要构建一个简单的框架来为一个操作的并发执行计时。
     * 所有工作线程都准备在 timer 线程启动时钟之前运行操作。当最后一个工作线程准备好运行该操作时，计时器线程「发令枪」，允许工作线程执行该操作。
     * 一旦最后一个工作线程完成该操作，计时器线程就停止时钟。
     *
     * @param executor    用来执行操作的executor
     * @param concurrency 表示并发级别【并发执行的次数】
     * @param action      具体的操作
     * @return 执行的时间
     * @throws InterruptedException
     */
    // Simple framework for timing concurrent execution
    public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown(); // Tell timer we're ready
                try {
                    start.await(); // Wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown(); // Tell timer we're done
                }
            });
        }

        ready.await(); // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown(); // And they're off!
        done.await(); // Wait for all workers to finish
        return System.nanoTime() - startNanos;
    }
}
