package com.shakespace.effectivejava.edition3.chapter7;

/**
 * 1. 千万不要任意地并行Stream pipeline
 * <p>
 * 并行性带来的性能提升在 ArrayList、HashMap、HashSet 和 ConcurrentHashMap 实例上的流效果最好；int 数组和 long 数组也在其中。
 * 这些数据结构的共同之处在于，它们都可以被精确且廉价地分割成任意大小的子程序，这使得在并行线程之间划分工作变得很容易。
 * stream 库用于执行此任务的抽象是 spliterator，它由流上的 spliterator 方法返回并可迭代。
 */
public class E048_Concurrent {
}
