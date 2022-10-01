package com.shakespace.effectivejava.edition3.chapter9;

/**
 * 常量字段是唯一推荐使用下划线用法的
 * <p>
 * 输入参数是一种特殊的局部变量。它们的命名应该比普通的局部变量谨慎得多，因为它们的名称是方法文档的组成部分。
 * <p>
 * 类型参数名通常由单个字母组成。最常见的是以下五种类型之一：
 * T 表示任意类型，E 表示集合的元素类型，
 * K 和 V 表示 Map 的键和值类型，X 表示异常。
 * 函数的返回类型通常为 R。任意类型的序列可以是 T、U、V 或 T1、T2、T3。
 * <p>
 * 包没有语法命名约定。
 * 可实例化的类，包括枚举类型，通常使用一个或多个名词短语来命名，例如 Thread、PriorityQueue 或 ChessPiece。
 * 不可实例化的实用程序类通常使用复数名词来命名，例如 collector 或 Collections。
 * 接口的名称类似于类，例如集合或比较器，或者以 able 或 ible 结尾的形容词，例如 Runnable、Iterable 或 Accessible。
 * 因为注解类型有很多的用途，所以没有哪部分占主导地位。
 * 名词、动词、介词和形容词都很常见，例如，BindingAnnotation、Inject、ImplementedBy 或 Singleton。
 * <p>
 * 执行某些操作的方法通常用动词或动词短语（包括对象）命名，例如，append 或 drawImage。
 * 返回布尔值的方法的名称通常以单词 is 或 has（通常很少用）开头，后面跟一个名词、一个名词短语，
 * 或者任何用作形容词的单词或短语，例如 isDigit、isProbablePrime、isEmpty、isEnabled 或 hasSiblings。
 * <p>
 * 转换对象类型（返回不同类型的独立对象）的实例方法通常称为 toType，例如 toString 或 toArray。
 * 返回与接收对象类型不同的视图的方法通常称为 asType，例如 asList。
 * 返回与调用它们的对象具有相同值的基本类型的方法通常称为类型值，例如 intValue。
 * 静态工厂的常见名称包括 from、of、valueOf、instance、getInstance、newInstance、getType 和 newType
 * <p>
 * 字段名的语法约定没有类、接口和方法名的语法约定建立得好，也不那么重要，因为设计良好的 API 包含很少的公开字段。
 * 类型为 boolean 的字段的名称通常类似于 boolean 访问器方法，省略了初始值「is」，例如 initialized、composite。
 * 其他类型的字段通常用名词或名词短语来命名，如 height、digits 和 bodyStyle。
 * 局部变量的语法约定类似于字段的语法约定，但要求更少。
 */
public class E068_Naming {
}
