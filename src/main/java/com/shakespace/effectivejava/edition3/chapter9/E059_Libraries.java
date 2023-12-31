package com.shakespace.effectivejava.edition3.chapter9;

/**
 * 了解常用的类库，避免创建多余的方法或者重复实现相同的功能
 * <p>
 * 要编写一个 random 方法来纠正这些缺陷，你必须对伪随机数生成器、数论和 2 的补码算法有一定的了解。
 * 幸运的是，你不必这样做。已经有了现成的方法 Random.nextInt(int)
 * 从 Java 7 开始，就不应该再使用 Random。在大多数情况下，选择的随机数生成器现在是 ThreadLocalRandom。
 * <p>
 * 使用标准库的好处：
 * 1、你可以利用编写它的专家的知识和以前使用它的人的经验。
 * 2、你不必浪费时间为那些与你的工作无关的问题编写专门的解决方案。如果你像大多数程序员一样，那么你宁愿将时间花在应用程序上，而不是底层管道上。
 * 3、随着时间的推移，它们的性能会不断提高，而你无需付出任何努力。
 * 4、随着时间的推移，它们往往会获得新功能。
 * 5、可以将代码放在主干中。这样的代码更容易被开发人员阅读、维护和复用。
 * <p>
 * 库太大，无法学习所有文档 [Java9-api]，但是 每个程序员都应该熟悉 java.lang、java.util 和 java.io 的基础知识及其子包。
 * 其他库的知识可以根据需要获得。
 */
public class E059_Libraries {
}
