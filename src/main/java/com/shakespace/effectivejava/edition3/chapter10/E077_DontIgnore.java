package com.shakespace.effectivejava.edition3.chapter10;

/**
 * 空 catch 块违背了异常的目的， 它的存在是为了强制你处理异常情况。
 * 如果你选择忽略异常，catch 块应该包含一条注释，解释为什么这样做是合适的，并且应该将变量命名为 ignore。
 * <p>
 * <p>
 * 不管异常是表示可预测的异常条件还是编程错误，用空 catch 块忽略它将导致程序在错误面前保持静默。
 * 然后，程序可能会在未来的任意时间点，在与问题源没有明显关系的代码中失败。正确处理异常可以完全避免失败。
 */
public class E077_DontIgnore {
    public static void main(String[] args) {
        // Empty catch block ignores exception - Highly suspect!
        try {
            //    ...
        } catch (Exception e) {
        }
    }
}
