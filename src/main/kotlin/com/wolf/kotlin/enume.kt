package com.wolf.kotlin

// 枚举类最基本的用法是实现一个类型安全的枚举。
// 枚举常量用逗号分隔,每个枚举常量都是一个对象。
enum class Color(val rgb: Int) {
    // 每一个枚举都是枚举类的实例，它们可以被初始化
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

// 默认名称为枚举字符名，值从0开始。若需要指定值，则可以使用其构造函数：
enum class Shape(value: Int) {
    ovel(100),
    rectangle(200)
}

// 枚举还支持以声明自己的匿名类及相应的方法、以及覆盖基类的方法
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },
    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

enum class RGB { RED, GREEN, BLUE }

// 使用 enumValues<T>() 和 enumValueOf<T>() 函数以泛型的方式访问枚举类中的常量
inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
}

fun main() {
    var color: Color = Color.BLUE

    println(Color.values())
    println(Color.valueOf("RED"))
    println(color.name)
    println(color.ordinal)

    printAllValues<RGB>()
}
