package com.shakespace.effectivejava.edition2;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 如果需要给源文件添加信息，注解是一个好的选择
 */
public class E035_Annotation {
    public static void main(String[] args) throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;
//        Class<?> clazz = Class.forName("com.shakespace.effectivejava.edition2.Sample");
//        for (Method m : clazz.getDeclaredMethods()) {
//            if (m.isAnnotationPresent(Test.class)) {
//                tests++;
//                int modifiers = m.getModifiers();
//                try {
//                    if ((modifiers & 8) != 0) {// 静态方法
//                        m.invoke(null);
//                    } else {
//                        // 非静态方法需要传入实例对象
//                        m.invoke(clazz.newInstance());
//                    }
//                    passed++;
//                } catch (InvocationTargetException e) {
//                    System.out.println(e.getCause());
//                } catch (Exception e) {
//                    System.out.println("invalid @Test" + m);
//                }
//            }
//        }
//        System.out.printf("passed %d , Failed %d%n", passed, tests - passed);

        // 允许符合定义的异常
        Class<?> clazz = Class.forName("com.shakespace.effectivejava.edition2.Sample2");
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                int modifiers = m.getModifiers();
                try {
                    if ((modifiers & 8) != 0) {// 静态方法
                        m.invoke(null);
                    } else {
                        // 非静态方法需要传入实例对象
                        m.invoke(clazz.newInstance());
                    }
                    passed++;
                } catch (InvocationTargetException e) {
                    // 获取注解的参数
                    if (m.getAnnotation(ExceptionTest.class).value().isInstance(e.getCause())) {
                        passed++;
                    }
                    System.out.println(e.getCause());
                } catch (Exception e) {
                    System.out.println("invalid @Test" + m);
                }
            }
        }
        System.out.printf("passed %d , Failed %d%n", passed, tests - passed);
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTest {
    // 定义参数类型
    Class<? extends Throwable> value();
}

// refer dagger 的 Subcomponent
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTest2 {
    // 定义参数类型
    Class<? extends Throwable>[] value();
}

class Sample {
    @Test
    public static void m1() {
    }

    public static void m2() {
    }

    @Test
    public static void m3() {
        throw new RuntimeException("");
    }


    public static void m4() {
    }

    @Test
    public void m5() {
    }

    public static void m6() {
    }

    @Test
    public static void m7() {
        throw new RuntimeException("7");
    }

}

class Sample2 {
    @ExceptionTest(ArithmeticException.class)
    public static void m1() {
        int i = 0;
        i = i / i;
    }

    public static void m2() {
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m3() {
        int[] a = new int[0];
        a[1] = 1;
    }


    public static void m4() {
    }

    @ExceptionTest(ArithmeticException.class)
    public void m5() {
    }

    @ExceptionTest3(ArithmeticException.class)
    @ExceptionTest3(IndexOutOfBoundsException.class)
    public static void m6() {
    }

    @ExceptionTest2({ArithmeticException.class, IndexOutOfBoundsException.class})
    public static void m7() {
        throw new RuntimeException("7");
    }
}

/**
 * java 1.8 , 可以使用 @Repeatable 进行多值注解
 * <p>
 * Invalid container annotation 'com.shakespace.effectivejava.edition2.ExceptionTestContainer': 'value' method should have type 'com.shakespace.effectivejava.edition2.ExceptionTest3[]'
 *
 * @Repeatable 参数是一个注解， 这个注解的 value 应该是被 Repeatable 注解的注解
 * <p>
 * 在同一个方法上可以多次注解， 来支持不同的操作
 * <p>
 * m.isAnnotationPresent(ExceptionTest3.class) 会返回false
 * m.getAnnotationsByType(ExceptionTest3.class) 会返回 注解参数的数组 ，可以分别进行验证
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class)
@interface ExceptionTest3 {
    // 定义参数类型
    Class<? extends Throwable>[] value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTestContainer {
    // 定义参数类型
    ExceptionTest3[] value();
}