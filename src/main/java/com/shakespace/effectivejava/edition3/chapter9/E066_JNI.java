package com.shakespace.effectivejava.edition3.chapter9;


/**
 *Java 本地接口（JNI）允许 Java 程序调用本地方法，这些方法是用 C 或 C++ 等本地编程语言编写的。从历史上看，本地方法主要有三种用途。
 * 它们提供对特定于平台的设施（如注册中心）的访问。
 * 它们提供对现有本地代码库的访问，包括提供对遗留数据访问。
 * 可以通过本地语言编写应用程序中注重性能的部分，以提高性能。
 *
 * 使用本地方法来访问特定平台的机制是合法的，但是几乎没有必要。
 *
 * 使用本地方法来提高性能的做法不值得提倡
 * 使用本地方法有一些严重的缺陷
 * 1. 本地语言不安全，使用本地方法的应用程序不再能免受内存毁坏错误的影响
 * 2. 由于本地语言比 Java 更依赖于平台，因此使用本地方法的程序的可移植性较差
 * 3. 也更难调试。如果不小心，本地方法可能会降低性能，因为垃圾收集器无法自动跟踪本地内存使用情况
 * 4. 本地方法需要「粘合代码」[可能是桥接方法？]，这很难阅读，而且编写起来很乏味。
 *
 * 在使用本地方法前务必三思
 */
public class E066_JNI {
    public static void main(String[] args) {

    }
}

