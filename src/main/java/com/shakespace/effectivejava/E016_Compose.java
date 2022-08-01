package com.shakespace.effectivejava;

import com.shakespace.effectivejava.model.Apple;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * 只有当子类真正是超类的子类型subtype 时， 才适合用继承 。 即只有 is-A 关系才适合继承
 * <p>
 * 在java 平台上，有很多违反了这一规定，例如，Stack 并不是vector ， Properties也不是Hashtable
 * 这两种情况下，复合是更恰当的。
 * <p>
 * 继承机制会把超类API 中的所有缺陷传播到子类中，而复合允许设计新的api来隐藏这些缺陷
 */
public class E016_Compose {
    public static void main(String[] args) {
        /**
         * 结果是6 ，因为addAll内部是调用add来实现的
         * 导致子类脆弱的原因是，父类有可能在后续的版本中获得新的方法，新的方法甚至可能和子类的实现具有相同的签名。
         */
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.addAll(Arrays.asList("A", "B", "C"));
        System.out.println(s.getAddCount());

        InstrumentedHashSet2<String> s2 = new InstrumentedHashSet2<>(new HashSet<>());
        s2.addAll(Arrays.asList("A", "B", "C"));
        System.out.println(s2.getAddCount());
    }
}

/**
 * inheritance ->
 * implementation inheritance : 实现继承
 * interface inheritance : 接口继承
 * <p>
 * 实现继承：打破了封装性 ，父类改变，子类也要随着改变
 */
class Inherit extends Apple {

}

class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}

class InstrumentedHashSet2<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedHashSet2(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}

/**
 * 相当于是Set 的包装类
 *
 * @param <E>
 */
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
