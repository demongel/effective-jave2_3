package com.shakespace.effectivejava;

import org.jetbrains.annotations.NotNull;

/**
 * x.compareTo(y) = - y.compareTo(x)
 * CompareTo 同样满足 自反性、对称性和传递性
 * 和 equals 类似， 无法在扩展新的 成员属性时 同时保持compareTo 约定
 * <p>
 * 如果 x.compareTo(y) == 0 , 应尽量保证 x.equals(y)
 * <p>
 * Set/Map 等是基于equals 方法定义等， 有序集合是按照compareTo方法定义的，如果compareTo 和 equals 判断方式不同，可能会遇到预期之外的一些情况
 * 例如使用      HashSet 来存放 BigDecimal("1.0") BigDecimal("1.00"), 集合将会包含两个元素
 * -           TreeSet 来存放 BigDecimal("1.0") BigDecimal("1.00"), 集合将会包含一个元素
 * <p>
 * CompareTo 的返回值并没有规定一定是-1/1/0， 对于int数值可以直接返回两个值相减的结果【注意int的溢出问题】
 * <p>
 * * @return  a negative integer, zero, or a positive integer as this object
 * *          is less than, equal to, or greater than the specified object.
 */
public class E012_Compare implements Comparable<E012_Compare> {

    @Override
    public int compareTo(@NotNull E012_Compare o) {
        return 0;
    }
}
