package com.shakespace.effectivejava.edition2;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 1. 不要用通配符类型作为返回类型
 * 提供数据「从泛型对象中读取数据」 --》 生产者？
 * 消费数据 往泛型中添加数据 --》 消费者
 */
public class E029_GenericType {

    public static void main(String[] args) {
        //Exception in thread "main" java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
        //	at com.shakespace.effectivejava.edition2.E029_GenericType.main(E029_GenericType.java:16)
//        String[] strings = pickTwo("a", "b", "c");

        Favorites fav = new Favorites();
        fav.putFav(String.class, "java");
        fav.putFav(Integer.class, 0xcafebabe);
        fav.putFav(Class.class, Favorites.class);

        String s = fav.getFav(String.class);
        Integer in = fav.getFav(Integer.class);
        Class c = fav.getFav(Class.class);
        System.out.printf("%s %x %s %n", s, in, c.getSimpleName());
    }

    public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        Iterator<? extends T> iterator = list.iterator();
        T result = iterator.next();
        while (iterator.hasNext()) {
            T next2 = iterator.next();
            if (result.compareTo(next2) > 0) {
                result = next2;
            }
        }
        return result;
    }

    // 如果泛型参数在方法中只出现一次，那么可以用通配符替代
    // 泛型参数 与 通配符
    public static <E> void swap(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    public static void swap2(List<?> list, int i, int j) {
        // 编译错误，取出的对象不能放回去 ,  因为 List<?> 只能 添加 null
        // Required type: capture of ?  Provided: capture of ?
//        list.set(i, list.set(j,list.get(i)));
        swapHelper(list, i, j);
    }

    // 转换一下就可以使用
    public static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    /**
     * 显式创建泛型数组是非法的，但是用泛型声明可变参数确实合法的
     * 因为带有泛型的可变参数或者参数化类型在实践中很有用 refer Arrays.asList
     * 新增一个 @SafeVarargs 注解，由方法的设计者承诺，声明这些类型是安全的
     *
     * @SafeVarargs 注解 只能用在final/static/【java9】private
     * <p>
     * 如果不想用可变参数+ @SafeVarargs 注解 ， 可以尝试使用List替换
     * <p>
     * 可变参数， 如果只用来将数量可变的参数从调用程序传到方法，就是安全的
     * <p>
     * 1、任何往可变参数数组中保存值的方法都是危险的
     * 2、允许另一个方法访问一个泛型可变参数数组是不安全的
     */
    static void dangerous(List<String>... lists) {
        List<Integer> integers = new ArrayList<>();
        Object[] objects = lists;
        objects[0] = integers; // 堆污染
        String s = lists[0].get(0);
    }

    // Possible heap pollution from parameterized vararg type
    //In the Java programming language, heap pollution is a situation that arises when a variable of a parameterized type refers to an object that is not of that parameterized type.  当一个 可变泛型参数 指向一个 无泛型参数 时
    // This situation is normally detected during compilation and indicated with an unchecked warning.[1] Later, during runtime heap pollution will often cause a ClassCastException.[2]
    static <T> T[] toArray(T... args) {
        return args;
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return toArray(a, b);
            case 1:
                return toArray(a, c);
            case 2:
                return toArray(c, b);
        }
        throw new AssertionError();
    }

    /**
     * <T extends Annotation> T getAnnotation(Class<T> annotationClass);
     * T 类型受限 必须是Annotation的子类
     * <p>
     * 通过asSubclass转成 <? extends U>
     */
    static Annotation getAnnotation(AnnotatedElement element, String typeName) {
        Class<?> annotationType = null;
        try {
            // 如果 annotationType 是 Annotation 的子类，就会转换成  (Class<? extends Annotation>) this;
            annotationType = Class.forName(typeName);
        } catch (Exception e) {
            throw new IllegalArgumentException((e));
        }
        return element.getAnnotation(annotationType.asSubclass(Annotation.class));
    }
}

/**
 * 把容器的key泛型化 （参数化？）
 * 局限性
 * 1、 putFav 传入一个非泛型类  可以增加预检查
 * 2、 传入一个带泛型带类型 例如 List<String> , List<Integer> 得到的class 都是 List.class， 目前还无法解决
 */
class Favorites {
    /**
     * ？不是Map key的类型，而是Class的类型，所以Map可以放置任意Class类型
     */
    private Map<Class<?>, Object> favs = new HashMap<>();

    public <T> void putFav(Class<T> type, T instance) {
//        favs.put(type,type.cast(instance))
        favs.put(type, instance);
    }

    public <T> T getFav(Class<T> type) {
        // cast --> check the instance
        return type.cast(favs.get(type));
    }
}



