package com.wolf.kotlin

fun main() {
//    ifOpt()

//    rangeOpt()

    whenOpt1()
    whenOpt2()
    whenOpt3()
}

fun whenOpt2() {
    var x = 1
    when {
        x == 1 -> print("x is odd")
        x == 2 -> print("x is even")
        else -> print("x is funny")
    }
}

// 顺序求值，可以当表达式有返回值当结果，或当成语句
fun whenOpt1() {
    var x = getX()
    when (x) {
        1 -> print("x == 1")// 遇到符合就break
        2 -> print("x == 2")
        in 1..10 -> print("x is in the range")// 范围内
        !in 1..10 -> print("x is not in the range")
        is String -> x.startsWith("prefix")// 检查是否指定类型
        !is String -> x// 不属于
        else -> {// default
            print("x 不是1或2")
        }
    }
}

fun whenOpt3() {
    val a = ArrayList<ArrayList<Int>>()
    println(a is List<*>)

    val data = test()
    when (data) {
        (data as? List<*>) -> {
            println(111)
        }
        else -> {
            println(222)
        }
    }
}

fun test(): Any? {
//    return 1
//    return arrayListOf<String>()
//    return ArrayList<String>()
    return ArrayList<Int>()
}

fun getX(): Any {
    return 1
}

fun rangeOpt() {
    val x = 5
    val y = 9
    if (x in 1..8) {
        println("x 在1~8之间")
    }

    if (y in 1..8) {
        println("y 在1~8之间")
    }
}

fun ifOpt() {
    val a = 1
    val b = 2

    var max = 1
    // if表达式，可以产生结果，赋值给变量
    max = if (a > b) {
        println("chose a")
        a
    } else {
        println("chose a")
        b
    }
    println(max)

    // 简化if表达式，不用三元操作符
    max = if (a > b) a else b
    println(max)

    maxOf(1, 2)
}

// 用表达式替换if正规方式，这里的返回值是被自动推导了
fun maxOf(a: Int, b: Int) = if (a > b) a else b
