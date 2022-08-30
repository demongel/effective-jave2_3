package com.shakespace.effectivejava;

import java.io.Serializable;

/**
 * 标记接口，是接口但是没有方法需要实现, 例如  Cloneable, Serializable
 *
 * 如果想要定义一个任何新方法都不会与之关联都类型，标记接口就是最好的选择。
 * 如果想要标记程序元素而非类和接口，或者标记要适合与已经广泛使用了注解类型的框架，标记注解是正确的选择
 */
public class E037_MarketInterface {
}

class Maker implements Cloneable, Serializable{

}
