package com.wolf.kotlin.inaction

import java.lang.StringBuilder


private fun testBaseUse() {
    val set = hashSetOf(1, 2, 3)
    val list = arrayListOf(1, 2, 3)
    val map = hashMapOf(1 to "one", 2 to "two")// to是一个普通函数
    println(set.javaClass)// 等价于java的getClass()
    println(list.javaClass)
    println(map.javaClass)

    val strings = listOf("first", "second", "fourteenth")
    println(strings.last())

    val numbers = setOf(1, 14, 2)
    println(numbers.max())
}

private fun testParam() {
    val list = listOf(1, 2, 3)
    println(list)// 触发toString

    // 在声明函数时，指定参数的默认值，可以避免创建重载的函数
    // 声明待默认参数值的joinToString
    fun <T> joinToString(
        collection: Collection<T>,
        separator: String = ",",// 参数的默认值被编码到被调用的函数中，那么对未传入参数的调用者是透明的用默认值
        prefix: String = "",
        postfix: String = ""
    ): String {
        val result = StringBuilder(prefix)
        for ((index, element) in collection.withIndex()) {
            if (index > 0) result.append(separator)
            result.append(element)
        }
        result.append(postfix)
        return result.toString()
    }

    // 命名参数
    println(joinToString(collection = list, separator = "; ", prefix = "(", postfix = ")"))
    println(joinToString(list, ", ", "", ""))
    println(joinToString(list))
    println(joinToString(list, "; "))
    println(joinToString(list, postfix = ";", prefix = "# "))
}

fun main() {
//    testBaseUse()

//    testParam()

    // 顶层函数和属性,查看[join.kt]

    // 扩展
    // 接收者类型                  接收者对象
    fun String.lastChar(): Char = this.get(this.length - 1)
    println("Kotlin".lastChar())
}

