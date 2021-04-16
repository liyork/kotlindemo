package com.wolf.kotlin.shizhan

private fun testFunExpr() {
    // 为了更加直观地表现函数也可以当作变量来使用，声明一个函数类型的变量sum
    val sum = fun(x: Int, y: Int): Int { return x + y }// sum是一个函数类型的变量
    println(sum)
    sum(1, 1)// 使用sum这个函数字面值来调用函数
}

// lambda是一个代码块形式，可以表示一个函数
private fun testLambda() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7)
    println(list.filter { it % 2 == 1 })// 过滤函数，参数是一个Lambda表达式

    var a = list.filter { it % 2 == 1 }// 简写的lambda表达式
    a = list.filter { it -> it % 2 == 1 }// 完整的lambda表达式

    val isOdd = { it: Int -> it % 2 == 1 }// 使用lambda表达式声明一个函数
    println(isOdd)
    list.filter(isOdd)
}

// 带箭头“->”的表达式就是一个函数类型
typealias G = (String) -> Int
typealias F = (Int) -> Boolean
typealias H = (String) -> Boolean

private fun testHighFun() {
    // 高阶函数
    // 要过滤出字符串元素的长度是奇数的列表
    val strList = listOf("a", "ab", "abc", "abcd", "abcde")
    val f = fun(x: Int) = x % 2 == 1// 求奇数
    val g = fun(s: String) = s.length// 求长度
    // 封装"字符串元素的长度是奇数"逻辑的函数
    val h = fun(g: (String) -> Int, f: (Int) -> Boolean): (String) -> Boolean {
        return { f(g(it)) }
    }

    // 用别名实现上述功能
    val h1 = fun(g: G, f: F): H {
        return { f(g(it)) }// 其中{}是一个lambda表达式
    }

    println(strList.filter(h(g, f)))
}

fun main() {
//    testFunExpr()

//    testLambda()

//    testHighFun()

//    testRun()

//    testApply()

//    testLet()

//    testAlso()

//    testWith()
}

// 传入一个接收者对象receiver,使用该对象去调用传入的lambda代码块receiver.block()
private fun testWith() {
    with(ArrayList<String>()) {
        add("A")
        add("B")
        add("C")
        println("use with, this = $this")
    }.let {
        println(it)
    }
}

private fun testAlso() {
    // 调用block(this),最后返回this
    val a = "ABC".also {
        println(it)
    }
    println(a)
    a.let {
        println(it)
    }
}

// 把当前调用对象作为参数传入block代码中
private fun testLet() {
    1.let { println(it) }// it就是调用者1
    "ABC".let { println(it) }
    myfun().let {// 执行myfun()函数，返回值传入let
        println(it)
    }
}

// 调用block函数后返回调用者对象this
private fun testApply() {
    val list = mutableListOf<String>("A", "B", "C")
    println("common write way, list = ${list}")

    val a = ArrayList<String>().apply {
        add("A")
        add("B")
        add("C")
        println("use apply way, list = ${list}")
    }
    println(a)
    // 等价于
    a.let { println(it) }
}

// 调用传入的block参数
private fun testRun() {
    myfun()
    run({ myfun() })// 使用run函数调用myfun()函数
    run { myfun() }// run()函数的括号"()"可以省略
    run { println("A") }// 等价于println("A")
}

fun myfun(): String {
    println("execute myfun")
    return "this is myfun result"
}



