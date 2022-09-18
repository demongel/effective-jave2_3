package com.shakespace.effectivejava.edition3.chapter8;

import java.util.Date;

/**
 * Date 已过时，不应在新代码中使用。
 * <p>
 * 为了保护 Period 实例的内部不受此类攻击，必须将每个可变参数的防御性副本复制给构造函数，并将副本用作 Period 实例的组件，而不是原始组件：
 * public Period(Date start, Date end) {
 * this.start = new Date(start.getTime());
 * this.end = new Date(end.getTime());
 * if (this.start.compareTo(this.end) > 0)
 * throw new IllegalArgumentException(this.start + " after " + this.end);
 * }
 * <p>
 * 对可被不受信任方子类化的参数类型，不要使用 clone 方法进行防御性复制。
 * <p>
 * 要防御第二次攻击，只需修改访问器，返回可变内部字段的防御副本：
 * <p>
 * // Repaired accessors - make defensive copies of internal fields
 * public Date start() {
 * return new Date(start.getTime());
 * }
 * <p>
 * public Date end() {
 * return new Date(end.getTime());
 * }
 * <p>
 * 参数的防御性复制不仅适用于不可变类。在编写方法或构造函数时，如果要在内部数据结构中存储对客户端提供的对象的引用，
 * 请考虑客户端提供的对象是否可能是可变的。如果是，请考虑该对象进入数据结构之后，
 * 你的类是否能够容忍该对象发生更改。如果答案是否定的，则必须防御性地复制对象，并将副本输入到数据结构中，而不是原始正本。
 *
 * 非零长度数组总是可变的。因此，在将内部数组返回给客户端之前，应该始终创建一个防御性的副本。或者，你可以返回数组的不可变视图。
 *
 * 在可能的情况下，应该使用不可变对象作为对象的组件，这样就不必操心防御性复制
 */
public class E050_Copy {
    public static void main(String[] args) {

        //乍一看，这个类似乎是不可变的，并且要求一个时间段的开始时间不能在结束时间之后。然而，利用 Date 是可变的这一事实很容易绕过这个约束：
        // Attack the internals of a Period instance
        Date start = new Date();
        Date end = new Date();
        Period p = new Period(start, end);
        end.setYear(78); // Modifies internals of p!
    }
}


// Broken "immutable" time period class
final class Period {
    private final Date start;
    private final Date end;

    /**
     * @param start the beginning of the period
     * @param end   the end of the period; must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException     if start or end is null
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(start + " after " + end);
        this.start = start;
        this.end = end;
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }
}
