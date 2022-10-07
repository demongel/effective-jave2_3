package com.shakespace.effectivejava.edition3.chapter11;

/**
 * 慎用延迟初始化
 * <p>
 * 与大多数优化一样，延迟初始化的最佳建议是「除非需要，否则不要这样做」(第67项)。延迟初始化是一把双刃剑。
 * 它降低了初始化类或创建实例的成本，代价是增加了访问延迟初始化字段的成本。
 * 根据这些字段中最终需要初始化的部分、初始化它们的开销以及初始化后访问每个字段的频率，延迟初始化实际上会损害性能
 *
 * <p>
 * 如果一个字段只在类的一小部分实例上访问，并且初始化该字段的代价很高，那么延迟初始化可能是值得的。
 * 要确定是否值得，唯一的办法就是测定类在使用延迟和不使用延迟的情况下的性能差别
 *
 * <p>
 * 如果需要使用延迟初始化来提高实例字段的性能，请使用双重检查模式。这个模式避免了初始化后访问字段时的锁定成本（Item-79）。
 *
 * <p>
 * 总之，您应该正常初始化大多数字段，而不是延迟初始化。如果必须延迟初始化字段以实现性能目标或 break a harmful initialization circularity，则使用适当的延迟初始化技术。
 * 对于普通字段，使用双重检查模式；
 * 对于静态字段，the lazy initialization holder class idiom.
 * 对于可以接受重复初始化的普通字段， 还可以考虑单检查模式。
 */
public class E083_LazyInit {

    // Lazy initialization of instance field - synchronized accessor
    private String field;

    private synchronized String getField() {
        if (field == null)
            field = "";
        return field;
    }

    // 如果需要在静态字段上使用延迟初始化来提高性能，使用 the lazy initialization holder class idiom. 这个用法可保证一个类在使用之前不会被初始化
    // 静态内部类单例？
    // Lazy initialization holder class idiom for static fields
    private static class FieldHolder {
        static final String field = "";
    }

    private static String getField2() {
        return FieldHolder.field;
    }

    // Double-check idiom for lazy initialization of instance fields
    private volatile String doubleCheck;

    private String getField3() {
        String result = doubleCheck;
        if (result == null) { // First check (no locking)
            synchronized (this) {
                if (doubleCheck == null) // Second check (with locking)
                    doubleCheck = result = "";
            }
        }
        return result;
    }
}
