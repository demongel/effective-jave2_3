package com.shakespace.effectivejava.edition2;

import com.shakespace.effectivejava.model.Stack;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 通配符
 */
public class E028_Generic {
    public static void main(String[] args) {
        Stack<Number> numberStack = new Stack<>();
        Iterable<Integer> integers = new HashSet<>();
        numberStack.pushAll(integers);


        Set<Integer> integers1 = new HashSet<>();
        Set<Double> doubles = new HashSet<>();
        // 默认类型推导 显示一个 extends
        Set<? extends Number> union = union(integers1, doubles);
        // 现在已经支持？ 不需要显式设置
        Set<Number> union2 = E028_Generic.<Number>union(integers1, doubles);
    }

    public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        Iterator<? extends T> iterator = list.iterator();
        T result = iterator.next();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (t.compareTo(result) > 0) {
                result = t;
            }
        }
        return result;
    }
}


