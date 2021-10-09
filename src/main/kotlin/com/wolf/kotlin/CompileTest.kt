package com.wolf.kotlin

/**
 * 测试编译后的类样子
 *
 * 对于class内的东西，保留原有类CompileTest.java
 * 对于其内的伴生对象，static final class Companion
 * 对于文件内的东西，生成CompileTestKt.java，字段方法都囊括其中
 *
 * val用来申明：只读变量【read-only】。相当于，java中final修饰的变量
 * const用来申明：编译期的常量【Compile-Time Constants】
 */
class CompileTest {
    public val msg1 = "val in class"
    private var msg2 = "var in class"

    companion object {
        val msg3 = "val in companion object"
    }
}

val msg4 = "val in file"

fun sayHello(msg: String) {
    println("msg=$msg")
}

fun main() {
    val msg5 = "show me your class file"
    sayHello(msg5)
}