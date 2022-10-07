package com.shakespace.effectivejava.edition3.chapter12.model;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {
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

    //    问题的根源在于 Period 的 readObject 方法没有进行足够的防御性复制。
//    当对象被反序列化时，对任何客户端不能拥有的对象引用的字段进行防御性地复制至关重要。
//    因此，对于每个可序列化的不可变类，如果它包含了私有的可变组件，那么在它的 readObjec 方法中，必须要对这些组件进行防御性地复制。
//    下面的 readObject 方法足以保证周期的不变性，并保持其不变性：
    // 为了实现这一点 需要把 start / end变成非final
// readObject method with defensive copying and validity checking
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        // Defensively copy our mutable components
        start = new Date(start.getTime());
        end = new Date(end.getTime());
        // Check that our invariants are satisfied
        if (start.compareTo(end) > 0)
            throw new InvalidObjectException(start + " after " + end);
    }
}
