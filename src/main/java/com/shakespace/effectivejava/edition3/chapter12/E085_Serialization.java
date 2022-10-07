package com.shakespace.effectivejava.edition3.chapter12;

import java.util.HashSet;
import java.util.Set;

/**
 * Prefer alternatives to Java serialization
 * 优先选择 Java 序列化的替代方案
 * <p>
 * 当序列化在 1997 年添加到 Java 中时，它被认为有一定的风险。这种方法曾在研究语言（Modula-3）中尝试过，但从未在生产语言中使用过。虽然程序员不费什么力气就能实现分布式对象，这一点很吸引人，但代价也不小，如：不可见的构造函数、API 与实现之间模糊的界线，还可能会出现正确性、性能、安全性和维护方面的问题。支持者认为收益大于风险，但历史证明并非如此。
 * 序列化的一个根本问题是它的可攻击范围太大，且难以保护，而且问题还在不断增多：通过调用 ObjectInputStream 上的 readObject 方法反序列化对象图。
 * 这个方法本质上是一个神奇的构造函数，可以用来实例化类路径上几乎任何类型的对象，只要该类型实现 Serializable 接口。
 * 在反序列化字节流的过程中，此方法可以执行来自任何这些类型的代码，因此所有这些类型的代码都在攻击范围内。
 * <p>
 * 攻击可涉及 Java 平台库、第三方库（如 Apache Commons collection）和应用程序本身中的类。
 * 即使坚持履行实践了所有相关的最佳建议，并成功地编写了不受攻击的可序列化类，应用程序仍然可能是有漏洞的。
 * <p>
 * 攻击者和安全研究人员研究 Java 库和常用的第三方库中的可序列化类型，寻找在反序列化过程中调用的潜在危险活动的方法称为 gadget【指令片段】。多个小工具可以同时使用，形成一个小工具链。
 * 偶尔会发现一个小部件链，它的功能足够强大，允许攻击者在底层硬件上执行任意的本机代码，允许提交精心设计的字节流进行反序列化。
 * <p>
 * 不使用任何 gadget，你都可以通过对需要很长时间才能反序列化的短流进行反序列化，轻松地发起拒绝服务攻击。这种流被称为反序列化炸弹【deserialization bomb】
 *
 * 1. 避免序列化利用的最好方法是永远不要反序列化任何东西。
 * 2. 没有理由在你编写的任何新系统中使用 Java 序列化。
 * 3. 有其他一些机制可以在对象和字节序列之间进行转换，从而避免了 Java 序列化的许多危险，同时还提供了许多优势，比如跨平台支持、高性能、大量工具和广泛的专家社区。
 * 在本书中，我们将这些机制称为跨平台结构数据表示[cross-platform structured-data representations.]。
 * 例如 json/protocol buffers
 * 4. 如果你不能完全避免 Java 序列化，可能是因为你需要在遗留系统环境中工作，那么你的下一个最佳选择是 永远不要反序列化不可信的数据。
 * 特别要注意，你不应该接受来自不可信来源的 RMI 通信
 * 5. 如果无法避免序列化，并且不能绝对确定反序列化数据的安全性，那么可以使用 Java 9 中添加的对象反序列化筛选，并将其移植到早期版本（java.io.ObjectInputFilter）。
 * 该工具允许你指定一个过滤器，该过滤器在反序列化数据流之前应用于数据流。
 * 它在类粒度上运行，允许你接受或拒绝某些类。默认接受所有类，并拒绝已知潜在危险类的列表称为黑名单；
 * 在默认情况下拒绝其他类，并接受假定安全的类的列表称为白名单。优先选择白名单而不是黑名单， 因为黑名单只保护你免受已知的威胁。
 *
 *
 */
public class E085_Serialization {

    public static byte[] bomb() {
        Set<Object> root = new HashSet<>();
        Set<Object> s1 = root;
        Set<Object> s2 = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Set<Object> t1 = new HashSet<>();
            Set<Object> t2 = new HashSet<>();
            t1.add("foo"); // Make t1 unequal to t2
            s1.add(t1);
            s1.add(t2);
            s2.add(t1);
            s2.add(t2);
            s1 = t1;
            s2 = t2;
        }
        // 对象图【the object graph 】由 201 个 HashSet 实例组成，每个实例包含 3 个或更少的对象引用。整个流的长度为 5744 字节，但是在你对其进行反序列化之前，资源就已经耗尽了。问题在于，反序列化 HashSet 实例需要计算其元素的哈希码。
        // 根哈希集的 2 个元素本身就是包含 2 个哈希集元素的哈希集，每个哈希集元素包含 2 个哈希集元素，以此类推，深度为 100。
        // 因此，反序列化 Set 会导致 hashCode 方法被调用超过 2100 次。
        // 除了反序列化会持续很长时间之外，反序列化器没有任何错误的迹象。生成的对象很少，并且堆栈深度是有界的。
        return serialize(root); // Method omitted for brevity
    }

    private static byte[] serialize(Set<Object> root) {
        // mock 通过编译，实际应该进行反序列化操作
        return new byte[16];
    }
}
