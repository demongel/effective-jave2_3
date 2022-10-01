package com.shakespace.effectivejava.edition3.chapter9;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

/**
 * 反射允许一个类使用另一个类，即使在编译前者时后者并不存在。然而，这种能力是有代价的
 * 1. 失去了编译时类型检查的所有好处，包括异常检查
 * 2. 执行反射访问所需的代码既笨拙又冗长。写起来很乏味，读起来也很困难
 * 3. 性能降低。反射方法调用比普通方法调用慢得多。
 * <p>
 * 通过非常有限的形式使用反射，你可以获得反射的许多好处，同时花费的代价很少。
 * <p>
 * 反射是一种功能强大的工具，对于某些复杂的系统编程任务是必需的，但是它有很多缺点。
 * 如果编写的程序必须在编译时处理未知的类，则应该尽可能只使用反射实例化对象，【重点】
 * 并使用在编译时已知的接口或超类访问对象
 */
public class E065_InterfaceBetterThanReflect {
    public static void main(String[] args) {

//        这是一个创建 Set<String> 实例的程序，类由第一个命令行参数指定。
//        程序将剩余的命令行参数插入到集合中并打印出来。不管第一个参数是什么，程序都会打印剩余的参数，并去掉重复项。
//        打印这些参数的顺序取决于第一个参数中指定的类。
//        如果你指定 java.util.HashSet，它们显然是随机排列的；
//        如果你指定 java.util.TreeSet，它们是按字母顺序打印的，
//        因为 TreeSet 中的元素是有序的

        // 尽管非常简单，但这个程序可以变成一个通用的集合测试器

//这个例子也说明了反射的两个缺点。首先，该示例可以在运行时生成六个不同的异常，如果没有使用反射实例化，所有这些异常都将是编译时错误。
// 第二个缺点是，根据类的名称生成类的实例需要 25 行冗长的代码，而构造函数调用只需要一行。
        // Translate the class name into a Class object
        Class<? extends Set<String>> cl = null;
        try {
            cl = (Class<? extends Set<String>>) // Unchecked cast!
                    Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            fatalError("Class not found.");
        }

        // Get the constructor
        Constructor<? extends Set<String>> cons = null;
        try {
            cons = cl.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fatalError("No parameterless constructor");
        }

        // Instantiate the set
        Set<String> s = null;
        try {
            s = cons.newInstance();
        } catch (IllegalAccessException e) {
            fatalError("Constructor not accessible");
        } catch (InstantiationException e) {
            fatalError("Class not instantiable.");
        } catch (InvocationTargetException e) {
            fatalError("Constructor threw " + e.getCause());
        } catch (ClassCastException e) {
            fatalError("Class doesn't implement Set");
        }

        // Exercise the set
        s.addAll(Arrays.asList(args).subList(1, args.length));
        System.out.println(s);
    }

    private static void fatalError(String msg) {
        System.err.println(msg);
        System.exit(1);
    }

}



