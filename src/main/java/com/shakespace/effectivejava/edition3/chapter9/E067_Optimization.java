package com.shakespace.effectivejava.edition3.chapter9;


/**
 * We follow two rules in the matter of optimization: Rule 1. Don’t do it. Rule 2 (for experts only). Don’t do it yet—that is, not until you have a perfectly clear and unoptimized solution.
 * 在优化方面，我们应该遵守两条规则：
 * 规则 1：不要进行优化。
 * 规则 2 （仅针对专家）：还是不要进行优化，也就是说，在你还没有绝对清晰的未优化方案之前，请不要进行优化。
 * <p>
 * 优化的弊大于利，特别是不成熟的优化
 * <p>
 * 1. 努力编写好的程序，而不是快速的程序。
 * 2. 尽量避免限制性能的设计决策。
 * 3. 考虑API设计决策的性能结果。
 * <p>
 * <p>
 * 在每次尝试优化之前和之后测量性能。 你可能会对你的发现感到惊讶。
 * 通常，试图做的优化通常对于性能并没有明显的影响；有时候，还让事情变得更糟。
 * 主要原因是很难猜测程序将时间花费在哪里。
 * 程序中你认为很慢的部分可能并没有问题，在这种情况下，你是在浪费时间来优化它。
 * 一般认为，程序将 90% 的时间花费在了 10% 的代码上。
 * <p>
 * 总而言之，不要努力写快的程序，要努力写好程序；速度自然会提高。但是在设计系统时一定要考虑性能，特别是在设计API、线路层协议和持久数据格式时。
 * 当你完成了系统的构建之后，请度量它的性能。
 * 如果足够快，就完成了。如果没有，利用分析器找到问题的根源，并对系统的相关部分进行优化。
 * 第一步是检查算法的选择：再多的底层优化也不能弥补算法选择的不足。
 * 根据需要重复这个过程，在每次更改之后测量性能，直到你满意为止。
 */
public class E067_Optimization {
    public static void main(String[] args) {

    }
}

