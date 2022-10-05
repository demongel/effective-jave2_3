package com.shakespace.effectivejava.edition3.chapter11;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 根据不同的情况，过度的同步可能导致性能下降、死锁甚至不确定行为。
 * <p>
 * 1. 不要在同步方法或块中将控制权交给客户端。
 * 换句话说，在同步区域内，不要调用一个设计为被重写的方法，或者一个由客户端以函数对象的形式提供的方法。
 * 2. 应该在同步区域内做尽可能少的工作。 获取锁，检查共享数据，根据需要进行转换，然后删除锁。
 * 如果你必须执行一些耗时的活动，请设法将其移出同步区域，而不违反 Item-78 中的指导原则。
 *
 * <p>
 * 如果你正在编写一个可变的类，你有两个选择：
 * 你可以省略所有同步并允许客户端在需要并发使用时在外部进行同步，
 * 或者你可以在内部进行同步，从而使类是线程安全的（Item-82）。
 * 只有当你能够通过内部同步实现比通过让客户端在外部锁定整个对象获得高得多的并发性时，才应该选择后者。
 * java.util 中的集合（废弃的 Vector 和 Hashtable 除外）采用前一种方法，
 * 而 java.util.concurrent 中的方法则采用后者（Item-81）。
 *
 * <p>
 * 如果你在内部同步你的类，你可以使用各种技术来实现高并发性，例如分拆锁、分离锁和非阻塞并发控制。
 *
 * <p>
 * 实际上，有一种更好的方法可以将外来方法调用移出同步块。java库提供了一个名为 CopyOnWriteArrayList 的并发集合【item81】，
 * 该集合是为此目的量身定制的。
 * 此列表实现是 ArrayList 的变体，其中所有修改操作都是通过复制整个底层数组来实现的。
 * 因为从不修改内部数组，所以迭代不需要锁定，而且速度非常快。
 * 如果大量使用，CopyOnWriteArrayList 的性能会很差，但是对于很少修改和经常遍历的观察者列表来说，它是完美的。
 *
 * <p>
 * 为了避免死锁和数据损坏，永远不要从同步区域内调用外来方法。
 * 更一般地说，将你在同步区域内所做的工作量保持在最小。在设计可变类时，请考虑它是否应该执行自己的同步。
 * 在多核时代，比以往任何时候都更重要的是不要过度同步。只有在有充分理由时，才在内部同步类，并清楚地记录你的决定（Item-82）
 */
public class E079_Sync {
    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
        // 1. 正常执行
        //  set.addObserver((s, e) -> System.out.println(e));

        // 2. java.util.ConcurrentModificationException
        // 试图在遍历列表的过程中从列表中删除一个元素，这是非法的。notifyElementAdded 方法中的迭代位于一个同步块中，
        // 以防止并发修改，但是无法防止迭代线程本身回调到可观察的集合中，也无法防止修改它的 observers 列表。
//        set.addObserver(new SetObserver<Integer>() {
//            public void added(ObservableSet<Integer> s, Integer e) {
//                System.out.println(e);
//                if (e == 23)
//                    s.removeObserver(this);
//            }
//        });

        // 3. Observer that uses a background thread needlessly
        // 死锁
        set.addObserver(new SetObserver<Integer>() {
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    ExecutorService exec = Executors.newSingleThreadExecutor();
                    try {
                        // 主线程调用 addObserver 已经加了锁，自线程remove 无法获得锁
                        exec.submit(() -> s.removeObserver(this)).get();
                    } catch (ExecutionException | InterruptedException ex) { // 异常的多重捕获
                        throw new AssertionError(ex);
                    } finally {
                        exec.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 100; i++)
            set.add(i);


    }
}

// Broken - invokes alien method from synchronized block!
class ObservableSet<E> extends ForwardingSet<E> {
    public ObservableSet(Set<E> set) {
        super(set);
    }

    private final List<SetObserver<E>> observers = new ArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserver<E> observer) {
        synchronized (observers) {
            return observers.remove(observer);
        }
    }

    private void notifyElementAdded(E element) {
        // 对应 case 1/2/3
//        synchronized (observers) {
//            for (SetObserver<E> observer : observers)
//                observer.added(this, element);
//        }

        // 解决 异常 和 死锁问题
        // 将外来方法的调用移出同步的代码块即可以解决这个问题
        List<SetObserver<E>> snapshot = null;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }
        for (SetObserver<E> observer : snapshot)
            observer.added(this, element);
    }

    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if (added)
            notifyElementAdded(element);
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c)
            result |= add(element); // Calls notifyElementAdded
        return result;
    }

    /**
     * 以下是使用 CopyOnWriteArrayList 的情况，没有显式的同步代码
     * 只需要改成 CopyOnWriteArrayList
     */
    // Thread-safe observable set with CopyOnWriteArrayList
    private final List<SetObserver<E>> observers2 = new CopyOnWriteArrayList<>();

    public void addObserver2(SetObserver<E> observer) {
        observers.add(observer);
    }

    public boolean removeObserver2(SetObserver<E> observer) {
        return observers.remove(observer);
    }

    private void notifyElementAdded2(E element) {
        for (SetObserver<E> observer : observers)
            observer.added(this, element);
    }
}

class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;

    public ForwardingSet(Set<E> s) {
        this.s = s;
    }


    @Override
    public int size() {
        return s.size();
    }

    @Override
    public boolean isEmpty() {
        return s.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return s.contains(o);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return s.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return s.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return s.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return s.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return s.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return s.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return s.addAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return s.retainAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return s.removeAll(c);
    }

    @Override
    public void clear() {
        s.clear();
    }
}

@FunctionalInterface
interface SetObserver<E> {
    // Invoked when an element is added to the observable set
    void added(ObservableSet<E> set, E element);
}

