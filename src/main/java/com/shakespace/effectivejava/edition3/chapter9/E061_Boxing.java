package com.shakespace.effectivejava.edition3.chapter9;

import java.util.Comparator;

/**
 * 1. 装箱类型除了值意外还有其他标识。--》 不要用 == 比较装箱类型
 * 2. 装箱类型可以为null  --》 避免NPE
 * 3. 基本类型通常更节省时间和空间 --》 避免反复拆装箱
 * <p>
 * 1 将 == 操作符应用于包装类型几乎都是错误的，会对地址进行比较，即比较是不是同一个对象
 */
public class E061_Boxing {
    public static void main(String[] args) {
        Integer integer = 1;

        // 结果正确
//        Comparator<Integer> naturalOrder = Integer::compareTo;
        // 结果错误
        Comparator<Integer> naturalOrder = (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);
        System.out.println(naturalOrder.compare(new Integer(42), new Integer(42)));

        Long sum = 0L;// 反复自动拆装箱 会造成性能问题
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
