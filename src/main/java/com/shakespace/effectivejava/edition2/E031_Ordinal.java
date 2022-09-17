package com.shakespace.effectivejava.edition2;

public class E031_Ordinal {
}

/**
 * 最好避免使用ordinal方法
 * 永远不要根据枚举的序数导出与它关联的值，一旦重新排列，将无法得到准确的结果。
 *
 * 最好使用一个成员变量（field）来表示
 */
enum Ensemble {
    SOLO, DUET, TRIO, QUARTET, QUINTET,
    SEXTET, SEPTET, OCTET, NONET, DECTET;

    public int numberOfMusicians() {
        return ordinal() + 1;
    }
}
