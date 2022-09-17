package com.shakespace.effectivejava.edition3.chapter7;

import java.nio.file.DirectoryStream;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.*;

/**
 * 只要方法引用更简洁高效，就使用方法引用
 */
public class E043_MethodRefer {
    public static void main(String[] args) {
        Function<String, Integer> function = String::length;
        String test = "test";
        function.apply(test);

        Runnable runnable = test::length;
        runnable.run();

        // 静态方法
        BiFunction<String, Integer, Integer> stringIntegerIntegerBiFunction = Integer::parseInt;

        // 有限制，接受对象是方法中指定的
        DirectoryStream.Filter<Instant> isAfter = Instant.now()::isAfter;
        // 无限制  ？
        UnaryOperator<String> stringLocaleStringBiFunction = String::toLowerCase;

        // 类构造器
        Runnable runnable1 = TreeMap<String, String>::new;
        runnable1.run();
        // 数组构造器
        IntConsumer aNew = int[]::new;
        aNew.accept(1);
    }

    /**
     * 使用方法引用替换 Lambda 表达式
     */
    public void handleMap(Map<String, Integer> map, String key) {
        map.merge(key, 1, Integer::sum);
        map.merge(key, 1, (x, y) -> x + y);
    }
}
