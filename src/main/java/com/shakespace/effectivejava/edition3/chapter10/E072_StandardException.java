package com.shakespace.effectivejava.edition3.chapter10;

/**
 * Exception	                    Occasion for Use
 * IllegalArgumentException	        Non-null parameter value is inappropriate（非空参数值不合适）
 * IllegalStateException	        Object state is inappropriate for method invocation（对象状态不适用于方法调用）
 * NullPointerException	            Parameter value is null where prohibited（禁止参数为空时仍传入 null）
 * IndexOutOfBoundsException	    Index parameter value is out of range（索引参数值超出范围）
 * ConcurrentModificationException	Concurrent modification of an object has been detected where it is prohibited（在禁止并发修改对象的地方检测到该动作）
 * UnsupportedOperationException	Object does not support method（对象不支持该方法调用）
 *
 * 如果有些异常用IllegalStateException和IllegalArgumentException都可以解释，可以参考以下原则
 * 如果没有参数值，抛出 IllegalStateException，否则抛出 IllegalArgumentException。
 */
public class E072_StandardException {
}
