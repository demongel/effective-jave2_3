package com.shakespace.effectivejava;

import java.util.Arrays;
import java.util.EmptyStackException;

public class E006_EliminateObject {
    public static void main(String[] args) {

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

    }
}

/**
 * 如果一个Stack 先增长，再收缩，从Stack中弹出的对象不会被回收，Stack 内部维护着这些过期对象的引用 obsolete reference
 * <p>
 * 无意识的对象保持 --> unintentional object retention
 * <p>
 * 清空对象引用应该是一种例外，而不是一种规范行为
 * <p>
 * 只要类是自己管理内存，就应该警惕内存泄漏问题
 */
class Stack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object b) {
        ensureCapacity();
        elements[size++] = b;
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
//        return elements[--size]; // 引起内存泄漏

        Object element = elements[--size];
        elements[size] = null; // 处理引用
        return element;
    }


    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
