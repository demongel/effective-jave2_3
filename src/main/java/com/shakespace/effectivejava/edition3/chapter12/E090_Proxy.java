package com.shakespace.effectivejava.edition3.chapter12;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 考虑以序列化代理代替序列化实例
 * 序列化代理模式相当简单。首先，设计一个私有静态嵌套类，它简洁地表示外围类实例的逻辑状态。
 * 这个嵌套类称为外围类的序列化代理。它应该有一个构造函数，其参数类型是外围类。
 * 这个构造函数只是从它的参数复制数据：它不需要做任何一致性检查或防御性复制。
 * 按照设计，序列化代理的默认序列化形式是外围类的完美序列化形式。
 * 外围类及其序列代理都必须声明实现 Serializable 接口。
 * <p>
 * 与防御性复制方法类似，序列化代理方法阻止伪字节流攻击和内部字段盗窃攻击。
 * 与前两种方法不同，这种方法允许 Period 的字段为 final，这是 Period 类真正不可变所必需的（Item-17）。
 * 与前两种方法不同，这一种方法不需要太多的思考。你不必指出哪些字段可能会受到狡猾的序列化攻击的危害，
 * 也不必显式地执行有效性检查作为反序列化的一部分。
 * <p>
 * 另一种序列化代理方式：参见EnumSet内部的实现 {@link java.util.EnumSet}
 * 考虑 EnumSet 的情况（Item-36）。该类没有公共构造函数，只有静态工厂。从客户端的角度来看，它们返回 EnumSet 实例，但是在当前 OpenJDK 实现中，
 * 它们返回两个子类中的一个，具体取决于底层枚举类型的大小。
 * 如果底层枚举类型有 64 个或更少的元素，则静态工厂返回一个 RegularEnumSet；否则，它们返回一个 JumboEnumSet。
 * 如果序列化一个枚举集合，它的枚举类型有 60 个元素，然后给这个枚举类型再增加 5 个元素，之后反序列化这个枚举集合。
 * 当它被序列化的时候，它是一个RegularEnumSet 实例，被反序列化时，最好是 JumboEnumSet 实例。
 *
 * <p>
 * 序列化代理模式有两个限制。
 * 它与客户端可扩展的类不兼容（Item-19）。
 * 有额外的开销
 * <p>
 * 当你发现必须在客户端不可扩展的类上编写 readObject 或 writeObject 方法时，请考虑序列化代理模式。
 * 要想稳健地将带有重要约束条件的对象序列化时，这种模式可能是最容易的方法。
 */
public class E090_Proxy {


}

class Period implements Serializable {
    private static final long serialVersionUID = 4647424730390249716L;
    private Date start;
    private Date end;

    /**
     * @param start the beginning of the period
     * @param end   the end of the period; must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException     if start or end is null
     */
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    public String toString() {
        return start + " - " + end;
    }

    // ... // Remainder omitted

    // writeReplace method for the serialization proxy pattern
    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    /**
     * 有了这个 writeReplace 方法，序列化系统将永远不会生成外围类的序列化实例，
     * 但是攻击者可能会创建一个实例，试图违反类的不变性。为了保证这样的攻击会失败，
     * 只需将这个 readObject 方法添加到外围类中：
     */
    // readObject method for the serialization proxy pattern
    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    // Serialization proxy for Period class
    private static class SerializationProxy implements Serializable {
        private final Date start;
        private final Date end;

        SerializationProxy(Period p) {
            this.start = p.start();
            this.end = p.end();
        }

        /**
         * 最后，在 SerializationProxy 类上提供一个 readResolve 方法，
         * 该方法返回外围类的逻辑等效实例。
         * 此方法的存在导致序列化系统在反序列化时将序列化代理转换回外围类的实例。
         */
        // readResolve method for Period.SerializationProxy
        private Object readResolve() {
            return new Period(start, end); // Uses public constructor
        }

        private static final long serialVersionUID = 234098243823485285L; // Any number will do (Item 87)
    }
}



