package com.shakespace.effectivejava;

/**
 * 静态内部类的单例
 * 不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
 * <p>
 * 缺点是不方便传入参数
 */
public class Singleton {
    private static class SingletonHolder {
        private static Singleton getInstance() {
            return new Singleton();
        }
    }

    public static Singleton getInstance() {
        return SingletonHolder.getInstance();
    }
}
