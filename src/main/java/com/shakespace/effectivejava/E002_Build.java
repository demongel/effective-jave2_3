package com.shakespace.effectivejava;

/**
 * 遇到可选参数很多都情况
 * 1、使用多个不同都构造器 ： 维护麻烦，需要传入不必要都值
 * 2、使用JavaBean , set方法， 不够安全，对象（的属性）必须是可变的
 * 3、Build方式
 *   一个以Builder 为参数的构造器
 *   Builder 要提供一个build方法返回所需要的类型
 *   Builder 内部需要针对每一个参数提供方法，同时返回自身
 */
public class E002_Build {
}


class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
        // required params
        private final int servingSize;
        private final int servings;

        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servings = servings;
            this.servingSize = servingSize;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder fat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        public Builder carbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
            return this;
        }

        // required
        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    public NutritionFacts(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

}
