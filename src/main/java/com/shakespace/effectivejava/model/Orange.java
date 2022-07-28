package com.shakespace.effectivejava.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Cloneable : 接口的非典型应用
 * 本身没有方法需要实现
 * 如果一个对象实现了该接口， 则调用 clone 方法时会返回逐域拷贝
 * 否则抛出 CloneNotSupportedException
 * <p>
 * 通常实现接口，是为了表明类可以做什么， 但是Cloneable改变了超类中受保护的方法的行为
 * <p>
 * 如果成员变量包含可变内容， 例如数组/栈/集合等，clone 时要做对应等处理，避免影响原对象
 * <p>
 * 对于复杂对象的成员，可能需要逐一拷贝
 * <p>
 * 所有实现Cloneable 的类都应该用一个公有方法覆盖clone ，此公有方法首先调用 super.clone() , 然后修正任何需要的域。
 * 注意内部可变对象的处理
 * <p>
 * 另一个实现对象拷贝的方法是 提供一个拷贝构造器 或者 拷贝工厂
 */
public class Orange implements Cloneable {
    public int a;
    public List<String> list = new ArrayList<>();
    public Map.Entry<String, String>[] buckets = new Map.Entry[16];

    public Orange() {
        a = 42;
        list.add("Orange");
        buckets.clone();
    }

    /**
     * 转换构造器
     *
     * @param orange
     */
    public Orange(Orange orange) {
        // 直接从 orange 赋值
    }

    @Override
    public Orange clone() throws CloneNotSupportedException {
        Orange clone = (Orange) super.clone();
//        buckets.clone();
        // 不做处理，则clone 后会影响原对象
//        clone.list = new ArrayList<>(list);
        return clone;
    }

}
