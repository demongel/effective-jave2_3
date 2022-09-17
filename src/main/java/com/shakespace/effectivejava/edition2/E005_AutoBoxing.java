package com.shakespace.effectivejava.edition2;

public class E005_AutoBoxing {

    /**
     * 2305843005992468481 , time = 5600
     * 2305843005992468481 , time = 508 : 尽量使用基本类型
     */
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        Long sum = 0L; // 每次都会自动装箱
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum + " , time = " + (System.currentTimeMillis() - time));

        long time2 = System.currentTimeMillis();
        long sum2 = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum2 += i;
        }
        System.out.println(sum2 + " , time = " + (System.currentTimeMillis() - time2));
    }

}
