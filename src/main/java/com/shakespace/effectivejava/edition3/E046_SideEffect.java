package com.shakespace.effectivejava.edition3;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 纯函数：结果只取决于输入的参数，不依赖任何可变的状态，也不更新任何状态
 */
public class E046_SideEffect {
    public static void main(String[] args) {

        normalGroupingBy();

//        testGroupingby1();

//        testToMap2();
//        testToMap3();
    }

    private static void normalGroupingBy() {
        Map<String, String> groups = new HashMap<>();
        groups.put("A", "Apple");
        groups.put("B", "Banana");
        groups.put("C", "Coconut");
        groups.put("D", "Durian");
        groups.put("E", "Eggplant");
        groups.put("F", "Fig");
        groups.put("G", "Grape");

        List<String> collect = groups.keySet()
                .stream()
                .sorted(Comparator.comparing(groups::get).reversed())
                .limit(10)
                .collect(Collectors.toList());

        System.out.println(collect);

        // toMap 映射到 一个Map ， 最简单到Mapper ，需要一个keyMapper 和一个 valueMapper
        Map<String, String> map2 = groups.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        System.out.println(map2);

        Map<String, String> collect3 = groups
                .values()
                .stream()
                .sorted()
                .collect(Collectors.toMap(Object::toString, e -> e));

        System.out.println(collect3);

        Map<String, String> collect4 = groups.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (s, s2) -> s + "--"));

        System.out.println(collect4);

        Map<String, Long> longMap = groups.values().stream().collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
        System.out.println(longMap);

        String joined = groups.values().stream().collect(Collectors.joining("-", "[", "]"));
        System.out.println(joined);
    }

    private static void testGroupingby1() {
        List<Student> students = Arrays.asList(new Student("apple", "男", 10.0),
                new Student("banana", "男", 10.0),
                new Student("orange", "男", 20.0),
                new Student("pipe", "女", 40.0),
                new Student("pinck", "女", 80.0)
        );

        //根据sex分组 , 默认toList , 转成HashMap ， key 是 sex ， value 是List
        Map<String, List<Student>> collect = students.stream().collect(Collectors.groupingBy(Student::getSex));
        System.out.println(collect);
        //{
        // 女=[Student{name='pipe', sex='女', money=40.0}, Student{name='pinck', sex='女', money=80.0}],
        // 男=[Student{name='apple', sex='男', money=10.0}, Student{name='banana', sex='男', money=10.0}, Student{name='orange', sex='男', money=20.0}]
        // }

        //根据sex分组，然后对money求和
        Map<String, Double> collect1 = students.stream().collect(Collectors.groupingBy(Student::getSex, Collectors.summingDouble(Student::getMoney)));
        System.out.println(collect1);
    }

    public static void testToMap3() {
        List<Student> students = Arrays.asList(new Student("apple", "男", 10.0),
                new Student("banana", "男", 10.0),
                new Student("orange", "男", 20.0),
                new Student("pipe", "女", 40.0),
                new Student("pinck", "女", 80.0)
        );

        LinkedHashMap<String, Double> collect = students
                .stream()
                .sorted(Comparator.comparing(Student::getMoney).reversed())
                // 参数： key 的映射器， value 的映射器 ， key冲突时的处理方式 ， 最终生成的Map类型
                .collect(Collectors.toMap(Student::getName, Student::getMoney, (oldValue, newValue) -> newValue, LinkedHashMap::new));
        System.out.println(collect);
        //{pinck=80.0, pipe=40.0, orange=20.0, apple=10.0, banana=10.0}
    }

    public static void testToMap2() {
        List<Student> students = Arrays.asList(new Student("apple", "男", 10.0),
                new Student("banana", "男", 10.0),
                new Student("orange", "男", 20.0),
                new Student("pipe", "女", 40.0),
                new Student("pinck", "女", 80.0)
        );
        /*
        java.lang.IllegalStateException: Duplicate key apple
        at java.util.stream.Collectors.lambda$throwingMerger$0(Collectors.java:133)
        at java.util.HashMap.merge(HashMap.java:1254)
        at java.util.stream.Collectors.lambda$toMap$58(Collectors.java:1320)
         */
//        Map<Double,String> collect2 = students.stream().collect(Collectors.toMap(Student::getMoney, Student::getName));
//        System.out.println(collect2);

        // 第三个参数用于处理key重复的情况
        Map<Double, String> collect2 = students.stream().collect(Collectors.toMap(Student::getMoney, Student::getName, (oldValue, newValue) -> newValue));
        System.out.println(collect2);
        //{80.0=pinck, 40.0=pipe, 20.0=orange, 10.0=banana}  key重复用新值

    }
}


class Student {
    private String name;
    private String sex;
    private double money;

    public Student(String name, String sex, double money) {
        this.name = name;
        this.sex = sex;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", money=" + money +
                '}';
    }
}
