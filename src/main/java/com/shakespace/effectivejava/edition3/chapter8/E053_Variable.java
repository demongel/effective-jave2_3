package com.shakespace.effectivejava.edition3.chapter8;

/**
 * 在性能关键的情况下使用可变参数时要小心。每次调用可变参数方法都会导致数组分配和初始化。
 * 如果你已经从经验上确定你负担不起这个成本，但是你仍需要可变参数的灵活性，那么有一种模式可以让你鱼与熊掌兼得。
 * 假设你已经确定对方法 95% 的调用只需要三个或更少的参数。可以声明该方法的 5 个重载，
 * 每个重载 0 到 3 个普通参数，当参数数量超过 3 个时引入可变参数：
 * <p>
 * 当你需要定义具有不确定数量参数的方法时，可变参数是非常有用的。
 * 在可变参数之前加上任何必需的参数，并注意使用可变参数可能会引发的性能后果。
 */
public class E053_Variable {

    public void foo() {
    }

    public void foo(int a1) {
    }

    public void foo(int a1, int a2) {
    }

    public void foo(int a1, int a2, int a3) {
    }

    public void foo(int a1, int a2, int a3, int... rest) {
    }

}



