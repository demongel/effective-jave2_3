package com.shakespace.effectivejava;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class E013_Accessibility {

    /**
     * This rule checks for static members in classes. Any global variables should not be used if they are Objects.
     * Any primitives must be marked as final.
     * Any Collections/Maps must be Immutable and final similar to below :
     * <p>
     * 静态的final 数组实际上还是可变的
     * <p>
     * 公有类的成员变量不应该是公开的， java.awt中的Point 和 Dimension 违反了这一原则
     */
    public static final String[] strings = new String[]{"Life", "Work", "Code"};

    // 方法一
    private static final String[] strings1 = {"life", "work", "code"};
    public static final List<String> list1 = Collections.unmodifiableList(Arrays.asList(strings1));

    //方法二
    public static String[] values() {
        return strings1.clone();
    }

    // 方法三
    public static List<String> getList1() {
        return Arrays.asList("life", "work", "code");
    }
}
