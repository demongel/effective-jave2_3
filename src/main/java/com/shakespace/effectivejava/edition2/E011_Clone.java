package com.shakespace.effectivejava.edition2;

import com.shakespace.effectivejava.model.Orange;

public class E011_Clone {
    public static void main(String[] args) {
        Orange orange = new Orange();
        try {
            Orange clone = orange.clone();
            System.out.println(orange.equals(clone)); // false
            System.out.println(orange.getClass().equals(clone.getClass())); // true
            System.out.println(orange == clone);//false
            System.out.println(clone.a);

            clone.list.add("apple");
            System.out.println(orange.list); // [Orange, apple]
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
