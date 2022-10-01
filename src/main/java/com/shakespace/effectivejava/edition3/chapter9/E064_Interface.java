package com.shakespace.effectivejava.edition3.chapter9;


import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 养成使用接口作为类型的习惯，那么你的程序将更加灵活。
 * <p>
 * 如果存在合适的接口类型，那么应该使用接口类型声明参数、返回值、变量和字段。
 * 如果没有合适的接口，就使用类层次结构中提供所需功能的最底层的类
 */
public class E064_Interface {
    public static void main(String[] args) {
        // Good - uses interface as type
        Set<Son> sonSet = new LinkedHashSet<>();

        // Bad - uses class as type!
        LinkedHashSet<Son> sonSet2 = new LinkedHashSet<>();
    }
}

class Son {

}


