package com.shakespace.effectivejava.edition3.chapter7;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 对于公共的，返回序列的方法，Collection 或者适当的自类型通常是最佳的 返回类型
 */
public class E047_ReturnType {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Kotlin");
        list.add("Groovy");
        list.add("Swift");
        list.add("Scala");
        list.add("Python");

        Stream<String> stream = list.stream();
        Iterator<String> iterator = stream.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Stream<String> stream2 = list.stream();
        // 需要强转进行迭代
        for (String s : (Iterable<? extends String>) stream2::iterator) {
            System.out.println(s);
        }

        // 自定义通用方法 ，这样既可以用for-each遍历任何Stream
        for (String s : iteratorOf(list.stream())) {
            System.out.println(s);
        }

        Stream<List<String>> of = of(list);
        for(List<String> stringList:iteratorOf(of)){
            System.out.println(stringList);
        }
    }

    public static <E> Iterable<E> iteratorOf(Stream<E> stream) {
        return stream::iterator;
    }

    // 寻找子集？
    public static final <E> Collection<Set<E>> of(Set<E> s) {
        List<E> src = new ArrayList<>();
        if (src.size() > 30) {
            throw new IllegalArgumentException("too big");
        }

        return new AbstractList<Set<E>>() {
            @Override
            public Set<E> get(int index) {
                Set<E> result = new HashSet<>();
                // >> 有符号右移
                for (int i = 0; index != 0; i++, index >>= 1) {
                    if ((index & 1) == 1) {
                        result.add(src.get(i));
                    }
                }
                return result;
            }

            @Override
            public boolean contains(Object o) {
                return o instanceof Set && src.containsAll((Set) o);
            }

            @Override
            public int size() {
                return 1 << src.size();
            }
        };
    }


    /**
     * 实现 幂集 的另一种方式
     * @param list
     * @param <E>
     * @return
     */
    public static <E> Stream<List<E>> of(List<E> list) {
        return Stream.concat(Stream.of(Collections.emptyList()), prefixes(list).flatMap(E047_ReturnType::suffixed));
    }

    public static <E> Stream<List<E>> prefixes(List<E> list) {
        return IntStream.rangeClosed(1, list.size()).mapToObj(end -> list.subList(0, end));
    }

    public static <E> Stream<List<E>> suffixed(List<E> list) {
        return IntStream.rangeClosed(1, list.size()).mapToObj(start -> list.subList(start, list.size()));
    }
}
