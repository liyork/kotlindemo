package com.wolf.kotlin//包声明，第一行

import com.wolf.kotlin.testinternal.a.B2
import java.lang.IllegalArgumentException

// 可选的包头

// kotlin定义类型是var/val，而groovy是def或者实际类型

// 包级可见
// 函数定义，关键字 参数:类型，返回值
fun sum(a: Int, b: Int): Int {
    return a + b
}

// 表达式作为函数体，返回类型自动推断
fun sum1(a: Int, b: Int) = a + b

// public 方法必须明确写出返回类型
public fun sum3(a: Int, b: Int): Int = a + b

// 无返回值1
fun printSum(a: Int, b: Int) {
    print(a + b)
}

// 无返回值2
fun printSum2(a: Int, b: Int): Unit {
    print(a + b)
}

// 返回Uint类型，可以省略
public fun printSum3(a: Int, b: Int) {
    print(a + b)
}

// 变长参数
fun vars(vararg v: Int) {
    for (vt in v) {
        print(vt)
    }
}

// 定义变量：
// var <标识符> : <类型> = <初始化值>
// 常量定义：
// val <标识符> : <类型> = <初始化值>
// 常量与变量都可以没有初始化值,但是在引用前必须初始化

// 字符串模板
fun testValTemp() {
    var a = 1
    val s1 = "a is $a"// 引用变量
    println(s1)

    a = 2
    // 计算${}中内容
    val s2 = "${s1.replace("is", "was")}, but now is $a"
    println(s2)
}

fun testNull() {
    // String?表示age可以是空,一个变量是否可空必须显示声明，对于可空变量，在访问其成员时必须做空处理，否则无法编译通过
    var age: String? = null
//    val ages = age!!.toInt()// 若age为null则抛出空指针异常
//    println(ages)

    // 若为空则使用-1,
    var nutNullAge = age ?: -1
    println(nutNullAge)

    // 不为空则用it表示不为空的age，为空则不执行println
    age?.let {
        println(it.length)
    }
    //不论是否为空，都执行println
    //age.let { println() }

    var a: Any? = "1"// 1/null
    val let = a.let {
        if (it is Int) it else 11111
    }
    println(let)

    var a1: Any? = "1"//null/1
    val let1 = a1.let {
        if (it is String && it.isNotEmpty()) it else null
    }
    println(let1)


    // 若为空返回null
    val ages1 = age?.toInt()
    println(ages1)
    // 若age为空则age?.toInt()为空则返回-1
    val ages2 = age?.toInt() ?: -1
    println(ages2)
}

fun testNull2() {
    var arg1 = "2"
    var arg2 = ""

    val x = parseArg(arg1)
    val y = parseArg(arg2)
    if (x != null && y != null) {// 进行判空
        println(x * y)
    }
    println(222)

    // filterNotNull
    var l: List<Int?> = listOf(1, null, 2)
    val filterNotNull = l.filterNotNull()
    println(filterNotNull)

//    var lNull: List<String>? = null
//    lNull.map {}
}

// Elvis 操作符表达,
// 如果 ?: 左侧表达式非空，elvis 操作符就调用其左侧表达式，否则调用右侧表达式。
// 当且仅当左侧为空时，才会对右侧表达式求值
fun testElvis(): String? {
    var a: String? = null //"1"
    var b = a?.length ?: -1
    println(b)

    // 由于 throw 和 return 语句在 Kotlin 中都是表达式，也可以用在 elvis 操作符右侧
//    var x = a ?: return null
//    x = a ?: throw IllegalArgumentException("xx")

    var p: Param? = null
    var l = p?.b?.length ?: 1// ?:的左侧为空则使用右侧的值
    println("l=>${l}")

    return null
}

// Int?也是一种类型，返回Int?表示返回值可能为null
fun parseArg(arg1: String): Int? {
    return null
}

// 类型检测并转换,Smart-Cast
fun testIs(obj: Any): Int? {
    if (obj is String) {// 像instanceof
        // 类型判断以后，obj会被系统自动转换为String类型
        return obj.length
    }

    // 不属于
    // if (obj !is String){
    //   // XXX
    // }
    return null
}

fun testRange() {
    // i不用声明，[1,4]
//    for (i in 1..4) print(i)

//    for (i in 4..1) print(i)

//    var i: Int? = null
//    if (i in 1..10) {
//        println(i)
//    }

    // 步长2
//    for (i in 1..4 step 2) print(i)

    // 递减2
//    for (i in 4 downTo 1 step 2) print(i)

    // [1,10)
//    for (i in 1 until 10) {
//        println(i)
//    }
}

fun main() {
//    println("Hello World!")

//    vars(1, 2, 3, 4, 5)

//    testValTemp()

//    testNull()

    testNull2()

//    testElvis()

//    testIs("1")

//    testRange()

//    listOpt()

//    bitOpt()

//    testMap()

//    testAs()

//    testConstAndVar()

//    testStream()
}

// as?，尝试显式类型转换，若失败则返回null，成功则顺利转型
fun testAs() {
    val p: Any = Student("a", 1, "1", 1)
    val a1 = p as? Person9
    val a2 = p as? Student

    val p1: Any = Person9("1", 1)
    // 尝试转换，失败则返回null
    val b1 = p1 as? Student
    val b2 = p1 as? Person9
    println("$a1, $a2, $b1, $b2")
}

fun testMap() {
    hashMapOf<Int, String>().computeIfAbsent(1) { it ->
        println(1111)
        it.toString() + "1"
    }
}

fun listOpt() {
    var list = listOf<String>("a", "b", "c")

    val target = mutableListOf<String>()
    list.filterTo(target) { it == "a" }
    println(target)

    val map1 = list.map { it + "1" }
    println(map1)

    val map = mapOf("key1" to 1, "key2" to 2)
}

// 变量与常量
fun testConstAndVar() {
    var name1: String = "var string"
    // kotlin和java一样都是一种静态类型的编程语言。所有表达式的类型在编译期就已经确定了，类型推导
    var name3 = "var string"
    val name2: String = "const string"

    name1 = "xxx"
//    name2 = "xxx"// 报错

    // list应用不能变，但是里面内容可变
    val list = mutableListOf<String>()
    list.add("111")

    println(B2.c)
    println(B2.b)
}

// 带有构造函数，并且还能声明属性，相当于定义了2个属性
class Rectangle(var height: Double, var length: Double) {
    var perimeter = (height + length) * 2
}

// 默认类是final
// 继承，需要用open
open class Shape1

// 若是Shape1，则报错：This type has a constructor, and thus must be initialized here，应该是默认是无参构造
class Rectangle1(var height: Double, var length: Double) : Shape1() {
    var perimeter = (height + length) * 2
}

fun testStream() {
    var fruits = listOf("a2", "a1", "b2")
    fruits.filter { it.startsWith("a") }
        .sortedBy { it }
        .map { it.toUpperCase() }
        .forEach { println(it) }
}