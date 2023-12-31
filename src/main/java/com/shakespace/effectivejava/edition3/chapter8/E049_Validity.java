package com.shakespace.effectivejava.edition3.chapter8;

/**
 * 对于公共方法和受保护的方法，如果在方法说明使用 Javadoc 的 @throws 标签记录异常，表明如果违反了对参数值的限制，将会引发该异常。
 * <p>
 * 类级别注释适用于类的所有公共方法中的所有参数。
 * <p>
 * 非公共方法可以使用断言检查它们的参数
 * <p>
 * 特别重要的是，应检查那些不是由方法使用，而是存储起来供以后使用的参数的有效性
 * <p>
 * 对参数的任意限制都是一件好事。相反，你应该把方法设计得既通用又实用。对参数施加的限制越少越好，假设该方法可以对它所接受的所有参数值进行合理的处理。
 */
public class E049_Validity {
}
