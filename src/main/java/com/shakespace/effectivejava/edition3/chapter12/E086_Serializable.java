package com.shakespace.effectivejava.edition3.chapter12;

import java.io.InvalidObjectException;

/**
 * 谨慎地实现Serializable接口
 * 1. 实现 Serializable 接口的一个主要代价是，一旦类的实现被发布，它就会降低更改该类实现的灵活性。
 * 2. 如果你接受默认的序列化形式，然后更改了类的内部实现，则会导致与序列化形式不兼容。试图使用类的旧版本序列化实例，再使用新版本反序列化实例的客户端（反之亦然）程序将会失败。
 * 3. 增加了出现 bug 和安全漏洞的可能性(item85)
 * 对象是用构造函数创建的；序列化是一种用于创建对象的超语言机制。无论你接受默认行为还是无视它，反序列化都是一个「隐藏构造函数」，其他构造函数具有的所有问题它都有。
 * 4. 增加了与发布类的新版本相关的测试负担。
 * 5. 实现 Serializable 接口并不是一个轻松的决定。
 * 6. 为继承而设计的类（Item-19）很少情况适合实现 Serializable 接口，接口也很少情况适合扩展它。
 * 在为了继承而设计的类中，Throwable 类和 Component 类都实现了 Serializable 接口。
 * 正是因为 Throwable 实现了 Serializable 接口，RMI 可以将异常从服务器发送到客户端；
 * Component 类实现了 Serializable 接口，因此可以发送、保存和恢复 GUI，但即使在 Swing 和 AWT 的鼎盛时期，这个工具在实践中也很少使用。
 *
 * 如果你实现了一个带有实例字段的类，它同时是可序列化和可继承的，那么需要注意几个风险。
 * 如果实例字段值上有任何不变量，关键是要防止子类覆盖 finalize 方法，
 * 可以通过覆盖 finalize 并声明它为 final 来做到。
 * 最后，如果类的实例字段初始化为默认值（整数类型为 0，布尔值为 false，对象引用类型为 null），那么必须添加 readObjectNoData 方法：
 *
 * 7. 内部类（Item-24）不应该实现 Serializable。 它们使用编译器生成的合成字段存储对外围实例的引用，并存储来自外围的局部变量的值。
 * 这些字段与类定义的对应关系，就和没有指定匿名类和局部类的名称一样。
 * 因此，内部类的默认序列化形式是不确定的。但是，静态成员类可以实现 Serializable 接口。
 *
 */
public class E086_Serializable {

    // readObjectNoData for stateful extendable serializable classes
    private void readObjectNoData() throws InvalidObjectException {
        throw new InvalidObjectException("Stream data required");
    }
}
