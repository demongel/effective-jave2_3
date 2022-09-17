package com.shakespace.effectivejava.edition2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类型A 包含一个枚举属性
 * 如何将类型A 的集合，按照枚举属性进行分类并转成Map
 *
 * 一维数组 -> EnumMap
 * 二维数组 -> EnumMap<Key,EnumMap<>>
 * 一般情况下都不要使用 ordinal
 */
public class E033_EnumMap {
    public static void main(String[] args) {
        // 1. 普通方式
        Plant[] garden = new Plant[3];
        garden[0] = new Plant("a", Plant.LifeCycle.ANNUAL);
        garden[1] = new Plant("b", Plant.LifeCycle.BIENNIAL);
        garden[2] = new Plant("c", Plant.LifeCycle.PERENNIAL);
        // 一个 Array ， 每个元素都是一个Set<Plant>
        Set<Plant>[] plantByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
        for (int i = 0; i < plantByLifeCycle.length; i++) {
            // 给每个元素赋值，初始化一个空Set
            plantByLifeCycle[i] = new HashSet<>();
        }

        for (Plant p : garden) {
            // 通过 ordinal 拿到Set ， 添加 Plant
            plantByLifeCycle[p.lifeCycle.ordinal()].add(p);
        }

        // 2. 使用EnumMap 注意构造方式
        // 得到一个 Key是枚举类型， value是Set集合的 Map
        Map<Plant.LifeCycle, Set<Plant>> plantsByLifecycle
                = new EnumMap<>(Plant.LifeCycle.class);
        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantsByLifecycle.put(lc, new HashSet<>());
        }
        for (Plant p : garden) {
            plantsByLifecycle.get(p.lifeCycle).add(p);
        }

        System.out.println(plantsByLifecycle);

        /**
         * 关于groupingByfangfa
         * 第一个参数：分组按照什么分类
         * 第二个参数：分组最后用什么容器保存返回
         * 第三个参数：按照第一个参数分类后，对应的分类的结果如何收集
         * 其实一个参数的Collectors.groupingBy方法的实现中 ，第二个参数默认是HashMap::new， 第三个参数收集器其实默认是Collectors.toList
         */
        // 使用Stream 直接转换得到一个Map
        EnumMap<Plant.LifeCycle, Set<Plant>> collect = Arrays.stream(garden)
                // 将 garden 转成流
                .collect(Collectors
                        .groupingBy(
                                // 按照 lifeCycle 进行分类
                                p -> p.lifeCycle,
                                () -> new EnumMap<>(Plant.LifeCycle.class),
                                Collectors.toSet()));
        System.out.println(collect);
    }


}

/**
 * 植物有一个类别关于 一年生 、多年生或者两年生
 * 如果有一个花园，有多种植物，需要遍历各种植物，然后按照 lifeCycle分类
 */
class Plant {
    // 一年生 多年生 两年生
    enum LifeCycle {
        ANNUAL, PERENNIAL, BIENNIAL
    }

    final String name;
    final LifeCycle lifeCycle;

    Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }
}

/**
 * case 2 由不同枚举类组成的 二维数组
 * 使用 stream 映射，新增 枚举元素 时 无需做其他处理。
 */
enum Phase {
    SOLID, LIQUID, GAS, PLASMA;

    enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID), BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID), SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID),
        IONIZE(GAS, PLASMA), DEIONIZE(PLASMA, GAS);

        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        // initialize the phase transition map
        private static final Map<Phase, Map<Phase, Transition>>
                m = Stream.of(values())
                .collect(Collectors.groupingBy(
                        // t : Transition
                        t -> t.from,
                        () -> new EnumMap<>(Phase.class),
                        Collectors.toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase.class))
                ));

        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }
}

// 使用叙述索引 有可能出现问题
//enum Phase {
//    SOLID, LIQUID, GAS;
//
//    enum Transition {
//        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;
//
//        private static final Transition[][] TRANSITIONS = {
//                {null, MELT, SUBLIME},
//                {FREEZE, null, BOIL},
//                {DEPOSIT, CONDENSE, null}
//        };
//
//        public static Transition from(Phase from, Phase to) {
//            return TRANSITIONS[from.ordinal()][to.ordinal()];
//        }
//    }
//}


