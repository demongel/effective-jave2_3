package com.shakespace.effectivejava.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;

/**
 * 𝑓(⋅)是逆变（contravariant）的，当𝐴≤𝐵时有𝑓(𝐵)≤𝑓(𝐴)成立；
 * 𝑓(⋅)是协变（covariant）的，当𝐴≤𝐵时有𝑓(𝐴)≤𝑓(𝐵)成立；
 * 𝑓(⋅)是不变（invariant）的，当𝐴≤𝐵时上述两个式子均不成立，即𝑓(𝐴)与𝑓(𝐵)相互之间没有继承关系。
 * <p>
 * <? extends>实现了泛型的协变，比如： A/B是父类子类关系，List<A> List<B> 也是父类子类关系，符合一般认知， 是协同的，
 * * List<? extends Number> list = new ArrayList<Integer>();
 * <? super>实现了泛型的逆变，比如：   A/B是父类子类关系，List<B> List<A> 是父类子类关系，和一般认知不同， 是逆向的，所以是逆变
 * List<? super Number> list = new ArrayList<Object>();
 * <p>
 * PECS总结：
 * <p>
 * 要从泛型类取数据时【生产者】，用extends；
 * 要往泛型类写数据时【泛型类是消费者】，用super；
 * 既要取又要写，就不用通配符（即extends与super都不用）。
 * <p>
 * Comparable 始终是消费者？
 * kotlin 中， out --> 表示提供数据 ， in -> 消费数据
 */
public class Stack<E> {

    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // 强制转换
    @SuppressWarnings("unchecked")
    public Stack() {
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
//        return elements[--size]; // 引起内存泄漏

        E element = elements[--size];
        elements[size] = null; // 处理引用
        return element;
    }

    public boolean isEmpty() {
        return elements.length == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    public void pushAll(Iterable<? extends E> src) {
        for (E e : src) {
            push(e);
        }
    }

    public void popAll(Collection<? super E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }
}
