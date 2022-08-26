package com.shakespace.effectivejava;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 对于为继承而设计的类，唯一的测试方法就是编写子类
 * <p>
 * 被继承的对象，构造器不能调用可被覆盖的方法。
 * 实现继承的类，实现Cloneable和Serializable时，要注意 clone 方法和 readObject方法， 这两个方法行为上也类似于构造器。
 * <p>
 * 对于并非为了安全地进行子类化而设计和编写文档的类，要禁止子类化
 * <p>
 * 手动的消除可覆盖方法的自用特性：将需要被继承的代码移到另一个辅助方法中，在可覆盖方法中调用。
 * 子类覆盖这个辅助方法实现自己的功能
 */
public class E017_Inherit implements Serializable {

    public static void main(String[] args) {
        @SuppressWarnings("rawtypes") List<String> list = new ArrayList();
    }

    public int numElements(Set<?> s1, Set<?> s2) {
//        List<?> list3 = new ArrayList<>();
//        list3.add("1"); // 报错  ， 不能直接使用，通常用在参数中

        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(o1)) {
                result++;
            }
        }
        return result;
    }
}


