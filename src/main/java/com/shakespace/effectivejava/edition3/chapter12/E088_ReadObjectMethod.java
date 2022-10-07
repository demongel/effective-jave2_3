package com.shakespace.effectivejava.edition3.chapter12;

import com.shakespace.effectivejava.edition3.chapter12.model.MutablePeriod;
import com.shakespace.effectivejava.edition3.chapter12.model.Period;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 * 防御性地编写 readObject 方法
 * readObject 方法实际上是另一个公共构造函数，它与任何其他构造函数有相同的注意事项。
 * 例如构造函数必须检查其参数的有效性（Item-49）并在适当的地方制作防御性副本（Item-50）一样，readObject 方法也必须这样做。
 * 如果 readObject 方法没有做到这两件事中的任何一件，那么攻击者就很容易违反类的不变性。
 * <p>
 * 不严格地说，readObject 是一个以字节流为唯一参数的构造函数。
 * 当面对一个人造的字节流时，readObject产生的对象就会违反它所属的类的约束条件。
 * 这种字节流可以用来创建一个不可能的对象，这是利用普通的构造器无法创建的。
 * <p>
 * <p>
 * 无论何时编写 readObject 方法，都要采用这样的思维方式，即编写一个公共构造函数，该构造函数必须生成一个有效的实例，而不管给定的是什么字节流。不要假设字节流表示实际的序列化实例。虽然本条目中的示例涉及使用默认序列化形式的类，但是所引发的所有问题都同样适用于具有自定义序列化形式的类。下面是编写 readObject 方法的指导原则：
 * 1. For classes with object reference fields that must remain private, defensively copy each object in such a field. Mutable components of immutable classes fall into this category.
 * 对象引用字段必须保持私有的的类，应防御性地复制该字段中的每个对象。不可变类的可变组件属于这一类。
 * 2. Check any invariants and throw an InvalidObjectException if a check fails. The checks should follow any defensive copying.
 * 检查任何不变量，如果检查失败，则抛出 InvalidObjectException。检查动作应该跟在任何防御性复制之后。
 * 3.If an entire object graph must be validated after it is deserialized, use the ObjectInputValidation interface (not discussed in this book).
 * 如果必须在反序列化后验证整个对象图，那么使用 ObjectInputValidation 接口（在本书中没有讨论）。
 * 4. Do not invoke any overridable methods in the class, directly or indirectly.
 * 不要直接或间接地调用类中任何可被覆盖的方法。
 */
public class E088_ReadObjectMethod {

    public static void main(String[] args) {
        // FIXME 把Period移到根目录后不能导包
        Object p2 = deserialize(serializedForm);
        System.out.println(p2);

        MutablePeriod mp = new MutablePeriod();
        Period p = mp.period;
        Date pEnd = mp.end;

        // Let's turn back the clock
        pEnd.setYear(78);
        System.out.println(p);

        // Bring back the 60s!
        pEnd.setYear(69);
        System.out.println(p);
    }

    // 伪造的字节流
    // FIXME 注意书中这个例子并不能直接运行 ，首先会报错  java.lang.ClassNotFoundException: Period
    // FIXME 说明这个 byte[] 对应的 Period 类是在根目录的
    // FIXME 将Period 移到根目录后运行， 报错： Caused by: java.io.InvalidClassException: Period; local class incompatible: stream classdesc serialVersionUID = 4647424730390249716, local class serialVersionUID = 2750051086590208417
    // FIXME 添加 private static final long serialVersionUID = 4647424730390249716L; 后，运行得到 Sat Jan 02 04:00:00 MYT 1999 - Mon Jan 02 04:00:00 MYT 1984
    // Byte stream couldn't have come from a real Period instance!
    private static final byte[] serializedForm = {
            (byte) 0xac, (byte) 0xed, 0x00, 0x05, 0x73, 0x72, 0x00, 0x06,
            0x50, 0x65, 0x72, 0x69, 0x6f, 0x64, 0x40, 0x7e, (byte) 0xf8,
            0x2b, 0x4f, 0x46, (byte) 0xc0, (byte) 0xf4, 0x02, 0x00, 0x02,
            0x4c, 0x00, 0x03, 0x65, 0x6e, 0x64, 0x74, 0x00, 0x10, 0x4c,
            0x6a, 0x61, 0x76, 0x61, 0x2f, 0x75, 0x74, 0x69, 0x6c, 0x2f,
            0x44, 0x61, 0x74, 0x65, 0x3b, 0x4c, 0x00, 0x05, 0x73, 0x74,
            0x61, 0x72, 0x74, 0x71, 0x00, 0x7e, 0x00, 0x01, 0x78, 0x70,
            0x73, 0x72, 0x00, 0x0e, 0x6a, 0x61, 0x76, 0x61, 0x2e, 0x75,
            0x74, 0x69, 0x6c, 0x2e, 0x44, 0x61, 0x74, 0x65, 0x68, 0x6a,
            (byte) 0x81, 0x01, 0x4b, 0x59, 0x74, 0x19, 0x03, 0x00, 0x00,
            0x78, 0x70, 0x77, 0x08, 0x00, 0x00, 0x00, 0x66, (byte) 0xdf,
            0x6e, 0x1e, 0x00, 0x78, 0x73, 0x71, 0x00, 0x7e, 0x00, 0x03,
            0x77, 0x08, 0x00, 0x00, 0x00, (byte) 0xd5, 0x17, 0x69, 0x22,
            0x00, 0x78
    };

    // Returns the object with the specified serialized form
    static Object deserialize(byte[] sf) {
        try {
            return new ObjectInputStream(new ByteArrayInputStream(sf)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

}

