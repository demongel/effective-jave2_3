package com.shakespace.effectivejava.edition3;

import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.function.*;

/**
 * 1. UnaryOperator : 接收一个参数，即调用者， 返回类型和调用者相同
 * 2. BinaryOperator: 是特殊的 BiFunction ， 调用者、参数、返回值都是同一种类型, 调用时传入调用者/参数
 * 3. Predicate ：接收一个调用者， 返回一个 boolean
 * 4. Function : 参数与返回类型不一致的情况
 * 5. Supplier : 没有参数并返回/提供一个值
 * 6. Consumer ： 接收调用者，但是不返回值
 * <p>
 * 如果自定义函数接口 需要用 @FunctionalInterface 注解
 */
public class E044_StandardInterface {

    public static void main(String[] args) {
        /*
         * 重载方法可以返回不同的类型
         */
        UnaryOperator<String> stringLocaleStringBiFunction = String::toLowerCase;
        System.out.println(stringLocaleStringBiFunction.apply("String"));
        // toLowerCase的重载方法
        BiFunction<String, Locale, String> localeStringBiFunction = String::toLowerCase;

        BiFunction<BigInteger, BigInteger, BigInteger> bigIntegerBigIntegerBigIntegerBiFunction = BigInteger::add;
        BinaryOperator<BigInteger> operator = BigInteger::add;

        Predicate<Collection<String>> checkEmpty = Collection::isEmpty;
        // 默认返回 DirectoryStream.Filter？？？？
//        DirectoryStream.Filter<Collection> isEmpty = Collection::isEmpty;
        System.out.println(checkEmpty.test(new ArrayList<>())); // true

        Function<String[], List<String>> runnable = Arrays::asList;
        // 默认返回一个 Runnable
//        Runnable runnable1 = Arrays::asList;
        String[] strings = new String[]{"A", "B", "C"};
        System.out.println(runnable.apply(strings));

        // 默认返回也是 Runnable
        Supplier<Instant> instantSupplier = Instant::now;
        Instant instant = instantSupplier.get();
        System.out.println(instantSupplier.get());


        Consumer<String> consumer = System.out::println;
        consumer.accept("consumer");

    }
}


class MyMap<K, V> extends LinkedHashMap<K, V> implements BiPredicate<K, V> {

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.removeEldestEntry(eldest);
    }

    @Override
    public boolean test(K k, V v) {
        return false;
    }

}
