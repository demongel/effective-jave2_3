package com.shakespace.effectivejava.edition3.chapter11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * executor task stream 优先于线程
 * <p>
 * 对于小程序或负载较轻的服务器，Executors.newCachedThreadPool 通常是一个不错的选择，因为它不需要配置，而且通常能正确地完成工作。
 * 但是对于负载沉重的生产服务器来说，缓存的线程池不是一个好的选择！在缓存的线程池中，提交的任务不会排队，而是立即传递给线程执行。
 * 如果没有可用的线程，则创建一个新的线程。
 * 如果服务器负载过重，所有 CPU 都被充分利用，并且有更多的任务到达，就会创建更多的线程，这只会使情况变得更糟。
 * 因此，在负载沉重的生产服务器中，最好使用 Executors.newFixedThreadPool，它为你提供一个线程数量固定的池，或者直接使用 ThreadPoolExecutor 类来实现最大限度的控制。
 * <p>
 * 不仅应该避免编写自己的工作队列，而且通常还应该避免直接使用线程。当你直接使用线程时，线程既是工作单元，又是执行它的机制。
 * 在 executor 框架中，工作单元和执行机制是分开的。
 * 关键的抽象是工作单元，即任务【task】。有两种任务：Runnable 和它的近亲 Callable（与 Runnable 类似，只是它返回一个值并可以抛出任意异常）。
 * <p>
 * 在 Java 7 中，Executor 框架被扩展为支持 fork-join 任务，这些任务由一种特殊的 Executor 服务（称为 fork-join 池）运行。
 * 由 ForkJoinTask 实例表示的 fork-join 任务可以划分为更小的子任务，
 * 由 ForkJoinPool 组成的线程不仅处理这些任务，而且还从其他线程「窃取」任务，以确保所有线程都处于繁忙状态，
 * 从而提高 CPU 利用率、更高的吞吐量和更低的延迟。
 * 并发的Stream就是在ForkJoin池上编写的
 */
public class E080_ExecutorTaskStreamFirst {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("i");
            }
        });
        service.shutdown();
    }
}
