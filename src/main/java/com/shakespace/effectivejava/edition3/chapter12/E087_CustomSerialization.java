package com.shakespace.effectivejava.edition3.chapter12;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 考虑使用自定义序列化形式
 * 1. 在没有考虑默认序列化形式是否合适之前，不要使用它。
 * 2. 即使你认为默认的序列化形式是合适的，你通常也必须提供 readObject 方法来确保不变性和安全性。
 * <p>
 * 当对象的物理表示与其逻辑数据内容有很大差异时，使用默认的序列化形式有四个缺点：
 * 1. 它将导出的 API 永久地绑定到当前的内部实现。
 * 2. 它会占用过多的空间。
 * 3. 它会消耗过多的时间。
 * 4. 它可能导致堆栈溢出。
 * <p>
 * 考虑哈希表的情况。物理表示是包含「键-值」项的哈希桶序列。一个项所在的桶是其键的散列代码的函数，
 * 通常情况下，不能保证从一个实现到另一个实现是相同的。
 * 事实上，它甚至不能保证每次运行都是相同的。
 * 因此，接受哈希表的默认序列化形式将构成严重的 bug。
 * 对哈希表进行序列化和反序列化可能会产生一个不变量严重损坏的对象。
 * <p>
 * 无论你是否接受默认的序列化形式，当调用 defaultWriteObject 方法时，没有标记为 transient 的每个实例字段都会被序列化。
 * 因此，可以声明为 transient 的每个实例字段都应该做这个声明。
 * <p>
 * 如果使用默认的序列化形式，并且标记了一个或多个字段为 transient，请记住，当反序列化实例时，
 * 这些字段将初始化为默认值：对象引用字段为 null，数字基本类型字段为 0，布尔字段为 false [JLS, 4.12.5]。
 * 如果这些值对于任何 transient 字段都是不可接受的，则必须提供一个 readObject 方法，
 * 该方法调用 defaultReadObject 方法，然后将 transient 字段恢复为可接受的值（Item-88）。
 * <p>
 * 无论你是否使用默认的序列化形式，必须对对象序列化强制执行任何同步操作，就像对读取对象的整个状态的任何其他方法强制执行的那样。
 * 例如，如果你有一个线程安全的对象（Item-82），它通过同步每个方法来实现线程安全，并且你选择使用默认的序列化形式，那么使用以下 write-Object 方法：
 * // writeObject for synchronized class with default serialized form
 * private synchronized void writeObject(ObjectOutputStream s) throws IOException {
 * s.defaultWriteObject();
 * }
 * <p>
 * 如果将同步放在 writeObject 方法中，则必须确保它遵守与其他活动相同的锁排序约束，否则将面临资源排序死锁的风险 [Goetz06, 10.1.5]。
 *
 * <p>
 * 无论选择哪种序列化形式，都要在编写的每个可序列化类中声明显式的序列版本 UID。
 * 这消除了序列版本 UID 成为不兼容性的潜在来源（Item-86）。这么做还能获得一个小的性能优势。如果没有提供序列版本 UID，则需要执行高开销的计算在运行时生成一个 UID。
 *
 * <p>
 * 如果你希望创建一个新版本的类，它与现有的类不兼容，只需要更改序列版本 UID 声明中的值即可，
 * 这将导致反序列化旧版本的序列化实例的操作引发 InvalidClassException。
 * 所以除非你想破坏与现有序列化所有实例的兼容性，否则不要更改序列版本 UID。
 *
 * <p>
 * 如果你已经决定一个类应该是可序列化的（Item-86），那么请仔细考虑一下序列化的形式应该是什么。
 * 只有在合理描述对象的逻辑状态时，才使用默认的序列化形式；
 * 否则，设计一个适合描述对象的自定义序列化形式。
 * 设计类的序列化形式应该和设计导出方法花的时间应该一样多，都应该严谨对待（Item-51）。
 * 正如不能从未来版本中删除导出的方法一样，也不能从序列化形式中删除字段；必须永远保存它们，以确保序列化兼容性。
 * 选择错误的序列化形式可能会对类的复杂性和性能产生永久性的负面影响。
 */
public class E087_CustomSerialization {
}

// 如果对象的物理表示与其逻辑内容相同，则默认的序列化形式可能是合适的。
// 例如，默认的序列化形式对于下面的类来说是合理的，它简单地表示一个人的名字：
// 从逻辑上讲，名字由三个字符串组成，分别表示姓、名和中间名。Name 的实例字段精确地反映了这个逻辑内容。
// Good candidate for default serialized form
class Name implements Serializable {
    /**
     * Last name. Must be non-null.
     *
     * @serial
     */
    private final String lastName;

    /**
     * First name. Must be non-null.
     *
     * @serial
     */
    private final String firstName;

    /**
     * Middle name, or null if there is none.
     *
     * @serial
     */
    private final String middleName;
    // Remainder omitted

    // 避免编译错误
    public Name() {
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
    }
}

// 不适合默认的序列化方式
// 从逻辑上讲，这个类表示字符串序列。在物理上，它将序列表示为双向链表。
// 如果接受默认的序列化形式，该序列化形式将不遗余力地镜像出链表中的所有项，以及这些项之间的所有双向链接。
// Awful candidate for default serialized form
final class StringList implements Serializable {
    private int size = 0;
    private Entry head = null;

    private static class Entry implements Serializable {
        String data;
        Entry next;
        Entry previous;
    }
    //... // Remainder omitted
}

// StringList 的合理序列化形式就是列表中的字符串数量，然后是字符串本身。这构成了由 StringList 表示的逻辑数据，去掉了其物理表示的细节。
// 下面是修改后的 StringList 版本，带有实现此序列化形式的 writeObject 和 readObject 方法。
// 完全可以用json替代
// 提醒一下，transient 修饰符表示要从类的默认序列化表单中省略该实例字段：
// StringList with a reasonable custom serialized form
final class StringList2 implements Serializable {
    private transient int size = 0;
    private transient Entry head = null;
    // No longer Serializable!

    private static class Entry {
        String data;
        Entry next;
        Entry previous;
    }

    // Appends the specified string to the list
    public final void add(String s) {
        // ...
    }

    /**
     * writeObject 做的第一件事是调用 defaultWriteObject, readObject 做的第一件事是调用 defaultReadObject，
     * 即使 StringList 的所有字段都是 transient 的。
     * 你可能听说过，如果一个类的所有实例字段都是 transient 的，那么你可以不调用 defaultWriteObject 和 defaultReadObject，但是序列化规范要求你无论如何都要调用它们。
     * 这些调用的存在使得在以后的版本中添加非瞬态实例字段成为可能，同时保留了向后和向前兼容性。
     * 如果实例在较晚的版本中序列化，在较早的版本中反序列化，则会忽略添加的字段。
     * 如果早期版本的 readObject 方法调用 defaultReadObject 失败，反序列化将失败，并出现 StreamCorruptedException。
     */
    /**
     * Serialize this {@code StringList} instance.
     * *
     *
     * @serialData The size of the list (the number of strings
     * it contains) is emitted ({@code int}), followed by all of
     * its elements (each a {@code String}), in the proper
     * sequence.
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(size);
        // Write out all elements in the proper order.
        for (Entry e = head; e != null; e = e.next)
            s.writeObject(e.data);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int numElements = s.readInt();
        // Read in all elements and insert them in list
        for (int i = 0; i < numElements; i++)
            add((String) s.readObject());
    }

    //... // Remainder omitted
}
