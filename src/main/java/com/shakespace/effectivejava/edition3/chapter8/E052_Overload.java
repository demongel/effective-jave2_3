package com.shakespace.effectivejava.edition3.chapter8;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 安全、保守的策略是永远不导出具有相同数量参数的两个重载。
 * 如果一个方法使用了可变参数，
 * 保守策略是根本不重载它，除非如 Item-53 所述。
 * 如果遵守这些限制，程序员就不会怀疑一组参数应该调用哪一种方法重载。
 * 这些限制并不十分繁琐，因为 你总是可以为方法提供不同的名称，而不是重载它们。
 * 就像 ObjectOutputStream 的 writeBoolean(boolean)、writeInt(int) 和 writeLong(long) 。。。
 *
 * 构造器可以考虑使用静态工厂
 *
 * 一般情况下，对于多个具有相同参数数目的方法来说，应该尽量避免重载。
 *
 */
public class E052_Overload {
    public static void main(String[] args) {
        Collection<?>[] collections = {
                new HashSet<String>(), new ArrayList<BigInteger>(), new HashMap<String, String>().values()
        };
        // 全部打印 "Unknown Collection"
        // 调用哪个方法是 编译时就决定的，在IDE里我们可以看到只有 参数是 Collection 的方法名变了颜色 【被调用】
        // 因为编译的类型是 Collection
        for (Collection<?> c : collections) {
            System.out.println(CollectionClassifier.classify(c));
        }

        // 对象的编译时类型对调用覆盖方法时执行的方法没有影响
        // 最为具体的的那个覆盖方法最是会得到执行。即运行时会按照真实类型来执行
        List<Wine> wineList = new ArrayList<>();
        wineList.add(new Wine());
        wineList.add(new SparklingWine());
        wineList.add(new Champagne());
        for (Wine wine : wineList) {
            System.out.println(wine.name());
        }

        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();
        for (int i = -3; i < 3; i++) {
            set.add(i);
            list.add(i);
        }
        // 注意存入的是装箱类型，remove时直接传入int值，变成了按照索引移除
        for (int i = 0; i < 3; i++) {
            set.remove(i);
            list.remove(i);
        }
        System.out.println(set +""+list);


        new Thread(System.out::println).start();
        ExecutorService exec = Executors.newCachedThreadPool();
        // 编译报错
        // System.out::println 是一个不精确的方法引用
        // 不要在相同的参数位置调用带有不同函数接口的方法，不同的函数接口并非根本不同
//        exec.submit(System.out::println);
    }
}


class CollectionClassifier {
    public static String classify(Set<?> s) {
        return "Set";
    }

    public static String classify(List<?> lst) {
        return "List";
    }

    public static String classify(Collection<?> c) {
        return "Unknown Collection";
    }

}

class Wine {
    String name() {
        return "wine";
    }
}

class SparklingWine extends Wine {
    @Override
    String name() {
        return "sparkling wine";
    }
}

class Champagne extends SparklingWine {
    @Override
    String name() {
        return "champagne";
    }
}

