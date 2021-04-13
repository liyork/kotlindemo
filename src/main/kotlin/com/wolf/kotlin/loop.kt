package com.wolf.kotlin

// for 循环可以对任何提供迭代器（iterator）的对象进行遍历

fun main() {
//    forOpt1()

//    forOpt2()

//    whileOpt()

//    labelOpt()

    labelReturnOpt()
}

// Kotlin中有函数字面量、局部函数和对象表达式,因此函数可以被嵌套
fun labelReturnOpt() {
//    foo1()
//    foo2()
//    foo3()
    foo4()
}

fun foo1() {
    var ints = arrayOf(1, 2, 3)

    ints.forEach(fun(i) {
        if (i == 2) return // 返回foo
        print(i)
    })

    var aa: (Int) -> Unit = fun(i) {
        if (i == 2) return // 返回foo
        print(i)
    }
    ints.forEach(aa)

    // lambda表达式中只有一个参数时，可以再代码中将其省略，并用it代替
    ints.forEach {
        if (it == 2) return// 返回foo
        print(it)
    }
}

fun foo2() {
    var ints = arrayOf(1, 2, 3)
    ints.forEach lit@{
        if (it == 2) return@lit // 返回本次这个lambda，而不是foo2函数
        print(it)
    }
}

// 隐士标签，标签就是接受该lambda的函数的名字
fun foo3() {
    var ints = arrayOf(1, 2, 3)
    ints.forEach {
        if (it == 2) return@forEach
        print(it)
    }
}

// 匿名函数，使用return返回匿名函数
fun foo4() {
    var ints = arrayOf(1, 2, 3)
    ints.forEach(fun(value: Int) {
        if (value == 2) return
        print(value)
    })
}

// 任何表达式都可以用标签标记，标识符+@
fun labelOpt() {
    loop@ for (i in 1..100) {
        for (j in 1..100) {
            if (i > 3) {
                break@loop// 跳转到loop标签的下个执行点
            }
        }
    }
    println("next loop")
}

fun whileOpt() {
    println("while...")
    var x = 5
    while (x > 0) {
        println(x--)
    }
    println("do while...")
    var y = 5
    do {
        println(y--)
    } while (y > 0)
}

// 下标，"在区间上遍历"会编译成优化的实现
fun forOpt2() {
    var list = listOf(1, 2, 3)
    for (i in list.indices) {
        print(list[i])
    }

    for ((index, value) in list.withIndex()) {
        println("the element at $index is $value")
    }
}

fun forOpt1() {
    var arr = arrayOf(1, 2, 3)
    for (i in arr) {
        println(i)
    }
}
