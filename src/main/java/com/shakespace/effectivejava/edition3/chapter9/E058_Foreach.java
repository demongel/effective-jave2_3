package com.shakespace.effectivejava.edition3.chapter9;

/**
 * 使用foreach 替代 for 循环
 *
 *有三种常见的情况你不应使用 for-each：
 * 1、解构过滤，如果需要遍历一个集合并删除选定元素，则需要使用显式的迭代器，以便调用其 remove 方法。
 * 通过使用 Collection 在 Java 8 中添加的 removeIf 方法，通常可以避免显式遍历。
 * 2、转换，如果需要遍历一个 List 或数组并替换其中部分或全部元素的值，那么需要 List 迭代器或数组索引来替换元素的值。
 * 3、并行迭代，如果需要并行遍历多个集合，那么需要显式地控制迭代器或索引变量，以便所有迭代器或索引变量都可以同步执行。
 * 如果发现自己处于这些情况中的任何一种，请使用普通的 for 循环，并警惕本条目中提到的陷阱。
 */
public class E058_Foreach {
}
