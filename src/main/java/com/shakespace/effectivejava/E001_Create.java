package com.shakespace.effectivejava;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

/**
 * 静态构造方法：
 * 1. 可以有名称
 * 2. 不是每次调用都创建一个新对象（条件限制）
 * 3. 可以返回任何子类型
 *
 * refer： {@link #(EnumSet)}
 *
 * 根据不同的length 返回不同的子类，客户端不必关心
 *      public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
 *         Enum<?>[] universe = getUniverse(elementType);
 *         if (universe == null)
 *             throw new ClassCastException(elementType + " not an enum");
 *
 *         if (universe.length <= 64)
 *             return new RegularEnumSet<>(elementType, universe);
 *         else
 *             return new JumboEnumSet<>(elementType, universe);
 *     }
 *
 * 不足
 * 1、如果是私有的构造器，就不能实例化
 * 2、和其他静态方法没有区别
 *
 * 常用名称：
 *      valueOf        ： 相当于是类型转换， 例如把 int 1 变成 "1"
 *      of             ： 类似于valueOf
 *      getInstance     ：返回唯一实例
 *      newInstance     ：每次都返回新都实例
 *      getType
 *      newType
 */
public class E001_Create {

    @Contract(value = " -> new", pure = true)
    public static @NotNull People newPeople() {
        return new People();
    }

}


class People {

}
