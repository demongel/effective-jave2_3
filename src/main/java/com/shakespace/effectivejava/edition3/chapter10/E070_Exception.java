package com.shakespace.effectivejava.edition3.chapter10;

/**
 * 对可恢复情况使用受检（checked）异常，对编程错误使用运行时异常
 * Java 提供了三种可抛出项：checked 异常（checked exception）、运行时异常（run-time exception）和错误（Error）。
 * <p>
 * 1. 如果期望调用者能够从适当地恢复，对于这种情况就应该使用受检异常
 * 2. 用运行时异常来表示编程错误，绝大多数运行时异常都表示操作违反了先决条件。
 * 3. 虽然 Java 语言规范没有要求，但有一个约定俗成的约定，即错误保留给 JVM 使用，以指示：资源不足、不可恢复故障或其他导致无法继续执行的条件。
 * 考虑到这种约定被大众认可，所以最好不要实现任何新的 Error 子类。
 * 因此，你实现的所有 unchecked 可抛出项都应该继承 RuntimeException（直接或间接）。
 * 不仅不应该定义 Error 子类，而且除了 AssertionError 之外，不应该抛出它们。
 * 4.可以自定义一种可抛出的异常，它不是 Exception、RuntimeException 或 Error 的子类。JLS 不直接处理这些可抛出项，而是隐式地指定它们作为普通 checked 异常（普通 checked 异常是 Exception 的子类，但不是 RuntimeException 的子类）。
 * 那么，什么时候应该使用这样的「猛兽」呢？总之，永远不要。
 * 与普通 checked 异常相比，它们没有任何好处，只会让 API 的用户感到困惑。
 * <p>
 * 总而言之，为可恢复条件抛出 checked 异常，为编程错误抛出 unchecked 异常。
 * 当有疑问时，抛出 unchecked 异常。不要定义任何既不是 checked 异常也不是运行时异常的自定义异常。
 * 应该为 checked 异常设计相关的方法，如提供异常信息，以帮助恢复。
 */
public class E070_Exception {
}
