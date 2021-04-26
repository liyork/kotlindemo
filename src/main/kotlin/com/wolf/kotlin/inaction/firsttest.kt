package com.wolf.kotlin.inaction

// first experiment
// 数据类，两个属性
data class Person(val name: String, val age: Int? = null)// 可空类型Int?，默认值

fun main() {// 顶层函数
    val persons = listOf(Person("Alice"), Person("Bob", age = 29))// 命名参数

    val oldest = persons.maxBy { it.age ?: 0 }// lambda表达式，Elvis运算符(若:?左边为null则用右边值)
    println("The oldest is: $oldest")// 字符串模板，打印时用到自动生成的toString方法

    val s: String? = null// 可以为null
    val s2: String = ""// 不能为null
}

