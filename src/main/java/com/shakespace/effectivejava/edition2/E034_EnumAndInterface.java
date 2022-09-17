package com.shakespace.effectivejava.edition2;

/**
 * 无法编写可以扩展的枚举类型，但是可以通过编写接口以及实现该接口的基础枚举类型来对它进行模拟
 *
 * 参考 CopyOption / OpenOption
 */
public class E034_EnumAndInterface {
}

interface Action {
    double apply(double x, double y);
}

enum BaseAction implements Action {
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

    BaseAction(String symbol) {
        this.symbol = symbol;
        // 除了编译常量域 ，枚举构造器不能访问枚举的静态域
//        It is illegal to access static member 'stringToEnum' from enum constructor or instance initializer
//        stringToEnum.put(symbol,this);
    }

    @Override
    public String toString() {
        return symbol;
    }

}

enum ExtendedAction implements Action {
    EXP("^") {
        @Override
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMINDER("%") {
        @Override
        public double apply(double x, double y) {
            return x % y;
        }
    };
    // 便于识别枚举值的真实作用
    private final String symbol;

    ExtendedAction(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

}




