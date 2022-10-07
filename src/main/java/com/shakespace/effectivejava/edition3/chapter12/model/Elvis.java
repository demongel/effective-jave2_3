package com.shakespace.effectivejava.edition3.chapter12.model;

import java.io.Serializable;
import java.util.Arrays;

// Broken singleton - has nontransient object reference field!
public class Elvis implements Serializable {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() { }
    private String[] favoriteSongs ={ "Hound Dog", "Heartbreak Hotel" };
    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
    private Object readResolve() {
    return INSTANCE;
    }
}