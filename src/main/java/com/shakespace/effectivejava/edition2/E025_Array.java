package com.shakespace.effectivejava.edition2;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表优先于数组
 * 数组与泛型相比， 有两个重要的不同点，
 * 1.数组是协变的，sub 是 Super 的子类型， sub[] 是 Super []的子类型 ， 泛型是不变的， List<sub> 不是 List<Super>的子类
 * 2. 数组是具体化的（reified），数组会在运行时才知道并检查他们的元素类型约束. 泛型在编译时强化类型信息。
 * <p>
 * 一般来说数组和泛型不能很好地混合使用，尽量用列表替换数组
 */
public class E025_Array {

    public static void main(String[] args) {
        Object[] objectArray = new Long[1];
        objectArray[0] = "A String"; // 运行时报错

//        List<Object> list = new ArrayList<Long>(); // 编译报错
//        list.add("String");
    }


    public <E> E reduce(List<E> list) {
//        E[] objects = list.toArray(); // 编译报错，list 转化成 array ， 无法直接转成具体类型
//        E[] es = (E[]) list.toArray(); // 强转会有uncheck warning
        Object[] toArray = list.toArray();
        return (E) toArray[0];
    }


}
