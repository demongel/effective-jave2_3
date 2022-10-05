package com.shakespace.effectivejava.edition3.chapter11;

/**
 * 要启用安全的并发使用，类必须清楚地记录它支持的线程安全级别。
 * 1. immutable 不可变的。这个类的实例看起来是常量。不需要外部同步。示例包括 String、Long 和 BigInteger
 * 2. unconditionally thread-safe 无条件线程安全。该类的实例是可变的，但是该类具有足够的内部同步，因此无需任何外部同步即可并发地使用该类的实例。例如 AtomicLong 和 ConcurrentHashMap。
 * 3. conditionally thread-safe 有条件的线程安全。与无条件线程安全类似，只是有些方法需要外部同步才能安全并发使用。
 * 示例包括 Collections.synchronized 包装器返回的集合，其迭代器需要外部同步。
 * 4. not thread-safe 非线程安全。该类的实例是可变的。要并发地使用它们，客户端必须使用外部同步来包围每个方法调用（或调用序列）。
 * 这样的例子包括通用的集合实现，例如 ArrayList 和 HashMap。
 * 5. thread-hostile 线程对立。即使每个方法调用都被外部同步包围，该类对于并发使用也是不安全的。线程对立通常是由于在不同步的情况下修改静态数据而导致的。
 * 没有人故意编写线程对立类；此类通常是由于没有考虑并发性而导致的。当发现类或方法与线程不相容时，通常将其修复或弃用。
 * <p>
 * 在文档中记录一个有条件的线程安全类需要小心。你必须指出哪些调用序列需要外部同步，以及执行这些序列必须获得哪些锁（在极少数情况下是锁）。通常是实例本身的锁，但也有例外。例如，Collections.synchronizedMap 的文档提到：
 * It is imperative that the user manually synchronize on the returned map when iterating over any of its collection views:
 * 当用户遍历其集合视图时，必须手动同步返回的 Map：
 * Map<K, V> m = Collections.synchronizedMap(new HashMap<>());
 * Set<K> s = m.keySet(); // Needn't be in synchronized block
 * ...
 * synchronized(m) { // Synchronizing on m, not s!
 * for (K key : s)
 * key.f();
 * }
 *
 *
 * <p>
 * 客户端可以通过长时间持有可公开访问的锁来发起拒绝服务攻击。这可以是无意的，也可以是有意的。
 * 为了防止这种拒绝服务攻击，你可以使用一个私有锁对象，而不是使用同步方法（隐含一个公共可访问的锁）：
 * Lock 字段应该始终声明为 final。 无论使用普通的监视器锁（如上所示）还是 java.util.concurrent 包中的锁，都是这样。
 * 私有锁对象用法只能在无条件的线程安全类上使用。有条件的线程安全类不能使用这种用法，因为它们必须在文档中记录，在执行某些方法调用序列时要获取哪些锁。
 * 私有锁对象用法特别适合为继承而设计的类（Item-19）。如果这样一个类要使用它的实例进行锁定，那么子类很容易在无意中干扰基类的操作，反之亦然。
 *
 * <p>
 * 每个类都应该措辞严谨的描述或使用线程安全注解清楚地记录其线程安全属性。
 * synchronized 修饰符在文档中没有任何作用。有条件的线程安全类必须记录哪些方法调用序列需要外部同步，以及在执行这些序列时需要获取哪些锁。
 * 如果你编写一个无条件线程安全的类，请考虑使用一个私有锁对象来代替同步方法。这将保护你免受客户端和子类的同步干扰，并为你提供更大的灵活性，以便在后续的版本中采用复杂的并发控制方式。
 */
public class E082_SyncDoc {

    // Private lock object idiom - thwarts denial-of-service attack
    private final Object lock = new Object();

    public void foo() {
        synchronized (lock) {
            System.out.println("");
        }
    }
}
