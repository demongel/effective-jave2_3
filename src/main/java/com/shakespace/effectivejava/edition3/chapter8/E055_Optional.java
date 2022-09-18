package com.shakespace.effectivejava.edition3.chapter8;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Optional<T> 类表示一个不可变的容器，它可以包含一个非空的 T 引用，也可以什么都不包含。
 * 不包含任何内容的 Optional 被称为空。一个值被认为存在于一个非空的 Optional 中。
 * Optional 的本质上是一个不可变的集合，它最多可以容纳一个元素。
 * Optional<T> 不实现 Collection<T>，但原则上可以。
 * <p>
 * Optional 返回值的方法比抛出异常的方法更灵活、更容易使用，并且比返回 null 的方法更不容易出错
 * <p>
 * <p>
 * 与返回基本数据类型相比，返回包含包装类的 Optional 类型的代价高得惊人，因为 Optional 类型有两个装箱级别，而不是零。
 * 因此，库设计人员认为应该为基本类型 int、long 和 double 提供类似的 Optional<T>。
 * 这些可选类型是 OptionalInt、OptionalLong 和 OptionalDouble。
 * 它们包含 Optional<T> 上的大多数方法，但不是所有方法。
 * 因此，永远不应该返包装类的 Optional，可能除了「次基本数据类型」，
 * 如 Boolean、Byte、Character、Short 和 Float 之外。
 * <p>
 * 在集合或数组中使用 Optional 作为键、值或元素几乎都是不合适的。
 * <p>
 * 如果你发现自己编写的方法不能总是返回确定值，并且你认为该方法的用户在每次调用时应该考虑这种可能性，那么你可能应该让方法返回一个 Optional。
 * 但是，你应该意识到，返回 Optional 会带来实际的性能后果；对于性能关键的方法，最好返回 null 或抛出异常。
 * 最后，除了作为返回值之外，你几乎不应该以任何其他方式使用 Optional。
 */
public class E055_Optional {

    public static void main(String[] args) {
        // 传入null 等于创建了一个 empty
        // 远不要通过返回 Optional 的方法返回 null : 它违背了这个功能的设计初衷
        Optional<Object> optional = Optional.ofNullable(null);

        // 有值返回 true ， 没有返回 false
        System.out.println(optional.isPresent());

    }

    // Returns maximum value in collection as an Optional<E>
    public static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
        if (c.isEmpty())
            return Optional.empty();
        E result = null;
        for (E e : c)
            if (result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
        return Optional.of(result);
    }
}


