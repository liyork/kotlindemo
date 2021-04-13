package com.wolf.kotlin.shizhan

fun main() {

    // 类似三元表达式
    //if (true) 1 else 0

//    testFor()

//    testWhile()

//    testReturn()

//    testLable()

//    testThrow()

    testOperator()


}

private fun testOperator() {
    // 重载操作符的函数需要用operator修饰符标记， 中缀操作符函数使用infix修饰符标记

    // 重载-a
    data class Point(val x: Int, val y: Int)

    operator fun Point.unaryMinus() = Point(-x, -y)// 使用operator关键字实现重载函数unaryMinus()
    val p = Point(1, 1)
    val np = -p
    println(np)

    // 重载+
    data class Counter(var index: Int)

    operator fun Counter.plus(increment: Int): Counter {// 重载+运算符
        return Counter(index + increment)
    }

    val c = Counter(1)
    val cplus = c + 10
    println(cplus)

    // 在Kotin中， Elvis操作符特定是跟null进行比较
    // Elvis操作符“?:”是一个二元运算符， 如果第一个操作数为真， 则返回第一个操作数， 否则将计算并返回其第二个操作数
    val x = 1// 使用Elvis操作符?:
    val y = x ?: 0
    val y1 = if (x != null) x else 0// 等价于
    // 为空则返回默认值
    var name1: String? = "xxx"
    var displayName = name1 ?: "Unknown"

    // 用infix函数自定义中缀操作符
    data class Person(val name: String, val age: Int)

    infix fun Person.grow(years: Int): Person {// 声明Person类型的中缀操作符函数
        return Person(name, age + years)
    }

    val person = Person("j", 20)
    println(person.grow(2))// 直接调用函数
    println(person grow 2)// 中缀表达式调用方式
}

private fun testThrow() {
    // 在Kotlin中throw是表达式， 它的类型是特殊类型Nothing
    println(Nothing::class)// 使用::类信用操作符，返回这个Nothing类的类型
    // 用Nothing标记无返回的函数
    fun fail(msg: String): Nothing {// 返回类型Nothing，表示该函数永远不会返回某个值
        throw IllegalArgumentException(msg)
    }

    // 把一个throw表达式的值赋给一个变量， 需要显式声明类型为Nothing
    val ex: Nothing = throw Exception("xxxxx")
    // 因为ex变量是Nothing类型， 没有任何值， 所以无法作为参数传给函数
}

private fun testLable() {
    // 在Kotlin中任何表达式都可以用标签（label） 来标记。 标签的格式
    //为标识符后跟@符号
    val intArray = arrayOf(1, 2, 3, 4, 5)
    intArray.forEach here@{// @here是一个标签，在lambda表达式开头
        if (it == 3) return@here// 跳转到lambda表达式标签here@处，继续下个循环
        println(it)
    }
    intArray.forEach {
        if (it == 3) return@forEach// 隐士标签，与接收该lambda的函数同名
        println(it)
    }
}

private fun testReturn() {
    // 在Kotlin中， 除了表达式的值， 有返回值的函数都要求显式使用return语句返回其值
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    // 在Kotlin中可以直接使用“=”符号返回一个函数的值， 这样的函数称为函数字面量，表达式
    fun sum1(a: Int, b: Int): Int = a + b
    fun max(a: Int, b: Int) = if (a > b) a else b
    val sum = fun(a: Int, b: Int) = a + b// 匿名函数，使用表达式来实现
    val sumf = fun(a: Int, b: Int) = { a + b }// 带上{}的表达式返回的是一个lmabda表达式
    sumf(1, 2)()// 调用函数

    // Kotlin中return语句会从最近的函数或匿名函数中返回， 但是在
    //Lambda表达式中遇到return语句时， 则直接返回最近的外层函数
    val intArray = arrayOf(1, 2)
    intArray.forEach {
        if (it == 3) return// lambda表达式中的return直接返回最近的外层函数
        println(it)
    }
    intArray.forEach(fun(a: Int) {// 匿名函数
        if (a == 3) return// 从最近的函数中返回，循环会继续
        println(a)
    })
}

private fun testWhile() {
    var x = 10
    while (x > 0) {
        x--
        println(x)
    }

    var y = 10
    do {
        y = y + 1
        println(y)
    } while (y < 20)
}

private fun testFor() {
    val array = arrayOf(1, 2)
    for (i in array.indices) {
        println(array[i])
    }

    for ((index, value) in array.withIndex()) {
        println("the elemetn at $index is $value")
    }

    for (i in 1..10) {
        println(i)
    }
    (1..10).forEach { print(it) }
}

fun max(a: Int, b: Int): Int {
    // Kotlin中， if是一个表达式， 即它会返回一个值
    val max = if (a > b) a else b
    return max
}

fun casesWhen() {
    var obj: Any? = null
    val s = "123"
    val validNum = arrayOf(1, 2, 3)
    when (obj) {
        0, 1, 2, 3 -> println("${obj}==> 数字")
        "hello" -> println("${obj}==>字符串")
        is Char -> println("char")
        in 1..10 -> println("s is in the range")
        in validNum -> println("x is valid")
        !in 10..20 -> println("x is outside the range")
        Integer.parseInt(s) -> println("x is 123")

        else -> println("else")
    }
}

fun fact(n: Int): Int {
    var result = 1
    when (n) {
        0, 1 -> result = 1
        else -> result = n * fact(n - 1)
    }
    return result
}