package com.shakespace.effectivejava;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class E009_HashCode {
    public static void main(String[] args) {
        Map<PhoneNumber, String> map = new HashMap<>();
        map.put(new PhoneNumber(123, 345, 456), "Tom");

        String s = map.get(new PhoneNumber(123, 345, 456));
        System.out.println(s);
    }
}

/**
 * 如果不重写hashCode , 那么逻辑相同（equals 返回 true） 两个对象将会有不通的hashcode值，
 * 在以hash值为准的集合中，将无法或者正确的值
 * <p>
 * 在很多 hashcode 的计算中， 使用 31 作为一个被乘数， 31*i =(i<<5)-i , jvm 可以自动优化计算
 * result = 31 * result + (element == null ? 0 : element.hashCode());
 */
class PhoneNumber {
    private final short areaCode;
    private final short prefix;
    private final short lineNumber;

    public PhoneNumber(int areaCode, int prefix, int lineNumber) {
        this.areaCode = (short) areaCode;
        this.prefix = (short) prefix;
        this.lineNumber = (short) lineNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return areaCode == that.areaCode &&
                prefix == that.prefix &&
                lineNumber == that.lineNumber;
    }


    // equals 中没有用到的 成员变量， hashcode 方法中也不要使用
    @Override
    public int hashCode() {
        return Objects.hash(areaCode, prefix, lineNumber);
    }
}