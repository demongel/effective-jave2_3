package com.shakespace.effectivejava.edition2;

public class E036_Override {

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    // 应该用 @Override 要用， 避免 想覆盖却没有正确覆盖的情况
    public boolean equals(String s) {
        return "".equals(s);
    }
}
