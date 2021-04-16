package com.wolf.kotlin.shizhan

import com.sun.org.apache.xpath.internal.operations.Bool

fun main() {
//    testNullType()

//    testArray()

//    testNullObject()

//    testSafeType()

//    testUnit()

//    testAny()

//    testIs()

//    testAs()
}

private fun testAs() {
    // as运算符用于执行引用类型的显式类型转换。
    val foo = Foo()
    val goo = Goo()
//    println(foo as Goo)// 父类型不能强制转换为子类型，报错
    println(foo as? Goo)
    println(goo as Foo)
}

private fun testIs() {
    println("abc" is String)
    println(null is Any)
    println(null is Any?)

    val foo = Foo()
    val goo = Goo()
    println(foo is Foo)
    println(goo is Foo)
    println(foo is Goo)
    println(goo is Goo)

    val len = strlen("abc")
    println(len)
    val lens = strlen(1)
    println(lens)
}

fun strlen(ani: Any): Int {
    if (ani is String) {
        return ani.length
    } else if (ani is Number) {
        return ani.toString().length
    } else if (ani is Char) {
        return 1
    } else if (ani is Boolean) {
        return 1
    }
    println("Not A String")
    return -1
}

open class Foo
class Goo : Foo()


private fun testAny() {
    println(1 is Any)
    println(1 is Any?)
    assert(null !is Any)
    assert(null is Any?)
    assert(Any() is Any?)
}

private fun testUnit() {
    fun unitExample() {
        println("hello,Unit")
    }

    val helloUnit = unitExample()
    println(helloUnit)// 函数的返回类型是Unit
    println(helloUnit is Unit)// 判断是否Unit类型

    val ur1 = unitReturn1()
    println(ur1)
    val ur2 = unitReturn2()
    println(ur2)
    val ur3 = unitReturn3()
    println(ur3)

    assert(Unit is Any)
    assert(Unit is Unit?)
    var u: Unit? = null
    assert(u is Any?)
}

fun unitReturn1() {

}

fun unitReturn2() {
    return Unit
}

fun unitReturn3(): Unit {

}

private fun testSafeType() {
    val notNullStr: String
    var nullableStr: String? = null
    println(nullableStr?.length)// 安全调用符?.
    nullableStr = "abc"
    println(nullableStr?.length)

    println(null == null)
    assert(null !is Any)
    assert(null is Any?)

    var a = null
    println(a)

    // Elvis操作符?:
    var s = nullableStr ?: "NULL"// 当nullableStr是null时，返回"NULL"
}

private fun testNullObject() {
    var a: String? = null
    var b = a?.length// 安全调用符?.

    println(strLength(null))
    println(strLength("abc"))
}

fun strLength(s: String?): Int {
    return s?.length ?: 0// ?.是安全调用符，?:是Elvis操作符
}

private fun testArray() {
    val sequare = Array(5, { i -> i * i })// 5个元素的数组，元素初始值是i*i
    sequare.forEach(::println)
}

private fun testNullType() {
    // a、 b它们都是以原始类型存储的
    val a: Int = 1000
    val b: Int = 1000
    println(a === b)// 引用相等
    println(a == b)// 值相等

    val a1: Int? = 1000
    val b1: Int? = 1000
    println(a1 === b1)// 可空类型Int?等价于java的Integer包装类型，a、b引用不相等
    println(a1 == b1)// 值相等
}