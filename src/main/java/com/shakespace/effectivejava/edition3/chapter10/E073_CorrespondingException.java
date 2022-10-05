package com.shakespace.effectivejava.edition3.chapter10;


/**
 * 抛出与抽象概念【应该是指调用的抽象对象】对应的异常
 * <p>
 * 如果方法抛出的异常与所执行的任务没有明显联系，会令人困惑。
 * 当方法从底层抛出异常时，往往会发生这种情况。
 * <p>
 * 更高层的实现应该捕获底层的异常，同时抛出可以按照高层抽象进行解释的异常，这种做法通常叫做异常转译
 * e.g.
 * // Exception Translation
 * try {
 * ... // Use lower-level abstraction to do our bidding
 * } catch (LowerLevelException e) {
 * throw new HigherLevelException(...);
 * }
 * <p>
 * <p>
 * 如果低层异常可能有助于调试高层异常的问题，则需要一种称为链式异常的特殊异常转换形式。
 * 低层异常（作为原因）传递给高层异常，高层异常提供一个访问器方法（Throwable 的 getCause 方法）来检索低层异常
 * <p>
 * //Exception Chaining
 * try {
 * ... // Use lower-level abstraction to do our bidding
 * }
 * catch (LowerLevelException cause) {
 * throw new HigherLevelException(cause);
 * }
 * <p>
 * 大多数标准异常都有接收链式异常的构造函数。对于不支持链式异常的异常，可以使用 Throwable 的 initCause 方法设置cause。
 * 异常链接不仅允许你以编程方式访问原因（使用 getCause），而且还将原因的堆栈跟踪集成到更高层异常的堆栈跟踪中。
 * <p>
 * <p>
 * 虽然异常转换优于底层异常的盲目传播，但它不应该被过度使用。
 * 如果不可能从低层防止异常，其次的做法就是让高层静默处理这些异常，使较高层方法的调用者免受低层问题的影响。
 */

public class E073_CorrespondingException<E> {

//    来自 AbstractSequentialList 类的异常转换示例
//    public E get(int index) {
//        try {
//            return listIterator(index).next();
//        } catch (NoSuchElementException exc) {
//            throw new IndexOutOfBoundsException("Index: " + index);
//        }
//    }


}



