package com.shakespace.effectivejava;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;

/**
 * equals 方法
 * 1. 只有当需要实现逻辑相等时才需要重写 equals 方法
 * 2. 遵循原则
 * 自反性  reflexive :  任何非null对象 x.equals(x)  一定是true
 * 对称性  symmetric   对于任何非null x 和 y,  if x.equals(y) 则 y.equals(x)
 * 传递性  transitive  对于非null 的 x,y,z , if x.equals(y) 且  y.equals(z) ， 则x.equals(z)
 * 一致性  consistent  对于任何非null x 和 y
 */
public class E008_Common {
    public static void main(String[] args) {
//        checkCase1();
//        checkCase2();

//        checkCase3();

//        compareFloatAndDouble();

    }

    /**
     * 对于简单的运算，使用==或者compare会得到理想的结果，随着运算次数但增加，不确定性就会增大
     * Float / Double .compare 相对是准确的，但是在多次运算时，float值已经出现了偏差，故结果也不在预期内
     */
    private static void compareFloatAndDouble() {
        float a = 0.1f;
        float b = 0.2f + 0.1f + 0.1f + 0.1f + 0.1f;
        float c = a + b;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(c == 0.700f);
        System.out.println(Float.compare(c, 0.700f));

        double d1 = .0;
        for (int i = 1; i <= 11; i++) {
            d1 += .1;
            System.out.println(d1);
        }
        double d2 = .1 * 11;
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d1 == d2);
        System.out.println(Double.compare(d1, d2));
    }

    /**
     * 正常来说， equals方法不应该依赖于不可靠的资源， 即equals方法中不应该有 会因为其他因素【例如网络】而改变的值
     * <p>
     * URL 的 equals 方法依赖于 URL中主机IP地址的比较 ， 可能需要访问网络。 可能存在一些意料之外的结果
     * 【实际没有测出来，可能需要特定的条件】
     */
    private static void checkCase3() {
        HashSet<URL> set = new HashSet<>();
        try {
            set.add(new URL("https://www.baidu.com"));
            System.out.println(set.contains(new URL("https://www.baidu.com")));
            Thread.sleep(6000);
            System.out.println(set.contains(new URL("https://www.baidu.com")));
        } catch (MalformedURLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Case2
     * 在ColorPoint的 equals 方法中，假如 遇到Point 只比较坐标值，遇到ColorPoint比较颜色和坐标值
     * 会出现一种情况， A ColorPoint = B Point , C ColorPoint = B Point , 但是 A ColorPoint != B ColorPoint
     * 无法满足传递性
     * 这是面向对象语言中关于等价关系的一个基本问题，无法在扩展可实例化类的同时，既增加新的属性，又保持equals 规定。
     * 有可能违反里式替换原则。。。
     * workaround ： 使用组合替代继承会更好
     * java.sql.TimeStamp  对 java.util.Date 做了扩展， 增加了nanoseconds , 是一种不太好的做法
     */
    private static void checkCase2() {
        Point p = new Point(2, 3);
        ColorPoint cp = new ColorPoint(2, 3, Color.BLACK);
        ColorPoint cp2 = new ColorPoint(2, 3, Color.RED);
        System.out.println(p.equals(cp));
        System.out.println(p.equals(cp2));
        System.out.println(cp.equals(cp2));
        System.out.println(cp.equals(p));
    }

    private static void checkCase1() {
        // Case 1 :
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        //  违反了对称性原则， String 并没有对大小写做特殊处理
        System.out.println(cis.equals(s));
        System.out.println(s.equals(cis));
    }
}

class Common {

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

/**
 * 测试传递性 ： 无法在扩展可实例化类的同时，既增加新的属性，又保持equals 规定。
 */
class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        if (!(o instanceof ColorPoint)) {
            return o.equals(this);
        }
        ColorPoint that = (ColorPoint) o;
        return super.equals(o) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}

/**
 * 测试对称性
 */
class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        }
        if (o instanceof String) {
            return s.equalsIgnoreCase((String) o);
        }
        return false;
    }
}
