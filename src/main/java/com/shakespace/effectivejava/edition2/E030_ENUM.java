package com.shakespace.effectivejava.edition2;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * 每当需要一组固定常量，并且在编译时就知道其成员的时候，就应该使用枚举
 * <p>
 * 枚举类型中的常量集并不一定要保持不变
 * <p>
 * 如果多个（但非所有）枚举常量同时共享相同的行为，则要考虑策略枚举
 */
public class E030_ENUM {
    public static void main(String[] args) {
//         System.out.println(Arrays.stream(Operation.values()).flatMap(new Function<Operation, Stream<?>>() {
//            @Override
//            public Stream<?> apply(Operation operation) {
//                System.out.println(operation);
//                return Stream.of(operation.toString());
//            }
//        }).collect(Collectors.toList()));
        System.out.println(Operation.fromString("+").get());
        System.out.println(Operation.stringToEnum);
    }
}

/**
 * values 是一个编译器插入的静态方法
 */
enum Operation {
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    // 便于识别枚举值的真实作用
    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
        // 除了编译常量域 ，枚举构造器不能访问枚举的静态域
//        It is illegal to access static member 'stringToEnum' from enum constructor or instance initializer
//        stringToEnum.put(symbol,this);
    }

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double apply(double x, double y);

    /**
     * 将 符号 和 枚举实例做映射, 前提是每个常量都有一个独特的字符表示
     * 通过fromString方法，就是通过符号来获取对应的枚举对象
     */
    public static final Map<String, Operation> stringToEnum =
            Stream.of(values()).collect(toMap(Object::toString, e -> e));

    public static Optional<Operation> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }

}

/**
 * 如果新增其他 enum 值， 例如特殊的节假日，则计算会产生问题
 */
enum PayrollDay {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
    SATURDAY, SUNDAY;

    private static final int MINS_PER_SHIFT = 8 * 60;

    int pay(int minutesWorked, int payRate) {
        int basePay = minutesWorked * payRate;

        int otPay;
        switch (this) {
            case SATURDAY:
            case SUNDAY:
                otPay = basePay / 2;
                break;
            default:
                otPay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) / 2;
                break;
        }
        return basePay + otPay;
    }
}

/**
 * 新增一个 枚举策略
 * 增加 枚举 时 ， 默认选择一种策略
 * <p>
 * 枚举中的 switch 语句不是在枚举中实现特定于常量行为的一种很好的选择
 * 适合给外部的枚举类型增加特定于常量的行为
 */
enum PayDay {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
    SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);

    private final PayType payType;

    PayDay() {
        this(PayType.WEEKDAY);
    }

    PayDay(PayType payType) {
        this.payType = payType;
    }

    private enum PayType {
        WEEKDAY {
            int otPay(int minutesWorked, int payRate) {
                return minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) / 2;
            }
        },
        WEEKEND {
            int otPay(int minutesWorked, int payRate) {
                return minutesWorked * payRate / 2;
            }
        };

        abstract int otPay(int mins, int payRate);

        private static final int MINS_PER_SHIFT = 8 * 60;

        int pay(int minutesWorked, int payRate) {
            int basePay = minutesWorked * payRate;
            return basePay + otPay(minutesWorked, payRate);
        }
    }


}
