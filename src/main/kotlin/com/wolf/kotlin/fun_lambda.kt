package com.wolf.kotlin

import com.wolf.kotlin.shizhan.myfun

// 函数定义
fun testFun(a: Int): String {
    return a.toString()
}

fun pow(a: Int, b: Int): String {
    return "xx"
}

// 使用函数类型的参数
fun map(datas: ArrayList<Int>, add: (Int) -> Int) {
    println("map..")
}

fun seq(a: Int): Int {
    return 1
}


// 使用函数类型作为返回值类型
fun getMath(type: String): (Int) -> Int {
    println("getMath")
    return ::seq
}

// 函数表达式
fun a(x: Int, y: Int): Int = x + y

fun main() {
    // 函数类型的变量
    var myFun: (Int, Int) -> String
    myFun = ::pow// 使用函数引用

    // 局部函数
    fun getMath1(type: String): (Int) -> Int {
        return ::seq
    }

    val math1 = getMath1("22222")
    println(math1)

    val math = getMath("33333")
    println(math)

    // lambda表达式，不能指定返回值类型
    var quare = { n: Int -> n * n }
    val quare1 = quare(11111)
    println(quare1)

}