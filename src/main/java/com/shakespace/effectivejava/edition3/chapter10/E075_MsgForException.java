package com.shakespace.effectivejava.edition3.chapter10;

/**
 * 1.要捕获失败，异常的详细消息应该包含导致异常的所有参数和字段的值。 例如，IndexOutOfBoundsException 的详细消息应该包含下界、上界和未能位于下界之间的索引值。
 * 2.因为许多人在诊断和修复软件问题的过程中可能会看到堆栈跟踪，所以 不应包含密码、加密密钥等详细信息。
 * 3.确保异常在其详细信息中包含足够的故障捕获信息的一种方法是，在其构造函数中配置，而不是以传入字符串方式引入这些信息。之后可以自动生成详细信息来包含细节。
 */
public class E075_MsgForException {
}
