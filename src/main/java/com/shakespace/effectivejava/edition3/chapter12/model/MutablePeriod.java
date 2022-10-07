package com.shakespace.effectivejava.edition3.chapter12.model;

import java.io.*;
import java.util.Date;

/**
 * 可以通过字节流来创建一个可变的 Period 实例，该字节流以一个有效的 Period 实例开始，然后向 Period 实例内部的私有日期字段追加额外的引用。
 * 攻击者从 ObjectInputStream 中读取 Period 实例，
 * 然后读取附加到流中的「恶意对象引用」。
 * 这些引用使攻击者能够访问 Period 对象中的私有日期字段引用的对象。通过修改这些日期实例，攻击者可以修改 Period 实例。
 * 下面的类演示了这种攻击：
 */
public class MutablePeriod {
    // A period instance
    public final Period period;

    // period's start field, to which we shouldn't have access
    public final Date start;

    // period's end field, to which we shouldn't have access
    public final Date end;

    public MutablePeriod() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);

            // Serialize a valid Period instance
            out.writeObject(new Period(new Date(), new Date()));

            /*
             * Append rogue "previous object refs" for internal
             * Date fields in Period. For details, see "Java
             * Object Serialization Specification," Section 6.4.
             */
            byte[] ref = {0x71, 0, 0x7e, 0, 5}; // Ref #5
            bos.write(ref); // The start field
            ref[4] = 4; // Ref # 4
            bos.write(ref); // The end field

            // Deserialize Period and "stolen" Date references
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            period = (Period) in.readObject();
            start = (Date) in.readObject();
            end = (Date) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }
}

