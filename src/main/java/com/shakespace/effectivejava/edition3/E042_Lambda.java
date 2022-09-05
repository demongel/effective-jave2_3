package com.shakespace.effectivejava.edition3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 对于 Lambda ， 一行是最理想的， 三行是最大极限
 * 如果Lambda 很长或者难以阅读，要么找一种方法简化，要么重构程序来消除
 * <p>
 * Lambda 只适用于接口， 抽象类的实例还是要用匿名类来完成
 * Lambda 无法获得自身的引用，Lambda内部的 this 指向的是外部
 */
public class E042_Lambda {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("kotlin");
        list.add("scala");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });

        Collections.sort(list, (o1, o2) -> 0);

        list.sort(Comparator.comparing(String::length));
    }
}
