package com.shakespace.effectivejava.edition3.chapter11;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 语言规范保证读取或写入变量是原子性的，除非变量的类型是 long 或 double [在32位设备和64位设备上是不一样的]
 * <p>
 * 为了在线程之间进行可靠的通信，也为了互斥访问，同步是必要的
 * <p>
 * 注意以下代码会被提升【hoist 指令重排？】
 * while (!stopRequested)
 * i++;
 * into this code:
 * if (!stopRequested)
 * while (true)
 * i++;
 * <p>
 * volatile ： 保证可见性、有序性，但不保证原子性 ，不能互斥访问【即有可能A现成读取后，B线程也读取，然后分别写入】
 * synchronized : 保证原子性 有序性 和原子性
 * <p>
 * 使用concurrent下的原子对象, 原子对象内部使用的volatile 关键字，所以可以保证可见性/有序性和原子性
 * <p>
 * 或者不共享数据，只在同一线程处理数据【例如android的UI管理】
 * <p>
 * 当多个线程共享可变数据时，每个读取或写入数据的线程都必须执行同步。
 * 在缺乏同步的情况下，不能保证一个线程的更改对另一个线程可见。同步共享可变数据失败的代价是活性失败和安全失败。这些故障是最难调试的故障之一。
 * 它们可能是间歇性的，并与时间相关，而且程序行为可能在不同 VM 之间发生根本的变化。
 * 如果只需要线程间通信，而不需要互斥，那么 volatile 修饰符是一种可接受的同步形式，但是要想正确使用它可能会比较棘手。
 */
public class E078_SharedData {
    // Broken! - How long would you expect this program to run?
    // 不加 volatile 则无法正常运行
    private static volatile boolean stopRequested;
    private static final AtomicBoolean aBoolean = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!aBoolean.get()) {
                i++;
            }
        });

        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        aBoolean.getAndSet(true);
        stopRequested = true;
    }
}
