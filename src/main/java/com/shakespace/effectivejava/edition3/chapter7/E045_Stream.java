package com.shakespace.effectivejava.edition3.chapter7;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 谨慎使用Stream
 * <p>
 * Stream pipeline : 包含一个源 Stream ， 0个或多个中间操作 - 终止操作
 * 通常是 lazy 的
 * 通常是顺序运行的，
 *
 * 1、滥用Stream会使程序代码难以读懂和维护
 * 2、在没有显式类型的情况下，仔细命名Lambda参数，对可读性至关重要
 * 3、在Stream pipeline中是哟哦那个helper方法，对于可读性而言，比在迭代化代码中使用更为重要
 * 4、最好避免使用Stream 处理Char
 */
public class E045_Stream {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Kotlin");
        list.add("Groovy");
        list.add("Staple");
        list.add("aelpst");
        list.add("petals");


        Stream.of(list)
                .parallel()
                .findFirst().isPresent();

        Map<String, Set<String>> groups = new HashMap<>();
        for (String s : list) {
            groups.computeIfAbsent(alphabetize(s), (unused) -> new TreeSet<>()).add(s);
        }

        System.out.println(groups);

        Map<String, Set<String>> groups2 = new HashMap<>();
        list.stream()
                .collect(Collectors
                        .groupingBy(
                                word -> word
                                        .toLowerCase()
                                        .chars()
                                        .sorted()
                                        .collect(StringBuilder::new,
                                                (sb, c) -> sb.append((char) c),
                                                StringBuilder::append).toString()
                        ))
                .values()
                .stream()
                .filter(
                        group -> group.size() >= 0)
                .map(group -> group.size() + ": " + group)
                .forEach(System.out::println);


        list.stream()
                .collect(Collectors
                        .groupingBy(E045_Stream::alphabetize)
                ).values()
                .stream()
                .filter(group -> group.size() >= 0)
                .forEach(g -> System.out.println(g.size() + ": " + g));

        // 最好避免使用Stream来处理Char
        "Hello Word".chars().forEach(System.out::print);
        System.out.println();
        "Hello Word".chars().forEach(x -> System.out.print((char) x));

    }

    private static String alphabetize(String s) {
        char[] chars = s.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
