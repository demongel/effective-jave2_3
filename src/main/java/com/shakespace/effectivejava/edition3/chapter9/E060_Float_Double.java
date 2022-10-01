package com.shakespace.effectivejava.edition3.chapter9;

/**
 * 若需要精确答案就应避免使用 float 和 double 参见008 条目
 * <p>
 * float 和 double 类型特别不适合进行货币计算，因为不可能将 0.1（或 10 的任意负次幂）精确地表示为 float 或 double
 * <p>
 * 对于任何需要精确答案的计算，不要使用 float 或 double 类型。
 * 如果希望系统来处理十进制小数点，并且不介意不使用基本类型带来的不便和成本，请使用 BigDecimal。
 * 使用 BigDecimal 的另一个好处是，它可以完全控制舍入，当执行需要舍入的操作时，可以从八种舍入模式中进行选择。
 * 如果你使用合法的舍入行为执行业务计算，这将非常方便。
 * 如果性能是最重要的，那么你不介意自己处理十进制小数点，而且数值不是太大，可以使用 int 或 long。
 * 如果数值不超过 9 位小数，可以使用 int；如果不超过 18 位，可以使用 long。如果数量可能超过 18 位，则使用 BigDecimal。
 */
public class E060_Float_Double {
}
