package com.wolf.kotlin

import java.lang.IllegalArgumentException

// 基本类型
// Double  64
// Float   32
// Long  64
// Int 32
// Short  16
// Byte  8

fun main() {

    compareDigit()

//    typeConvert()

//    bitOpt()

//    charOpt()

//    arrayOpt()

//    stringOpt()

//    stringTempOpt()

//    decimalDigitValue('1')

//    conditionOpt()

}

fun conditionOpt() {
    val a = 1
    val b = if (a == 1) 1 else 2
    println(b)
}

fun decimalDigitValue(c: Char) {
    if (c !in '0'..'9') {
        throw IllegalArgumentException("Out of range")
    }
    println(c.toInt() - '0'.toInt())
}

fun stringTempOpt() {
    val i = 10
    var s = "i = $i"
    println(s)

    s = "runoob"
    // ${}求值表达式
    val str = "$s.length is ${s.length}"
    println(str)

    // 展示$
    val price = """
    ${'$'}9.99
    """.trimIndent()
    println(price)
}

fun stringOpt() {
    var str = "abc"
    for (c in str) {
        println(c)
    }

    var text = """
    aaa
    bbb
    cccc
    """
    println(text.trimIndent())

    text = """
    %aaa
    %bbb
        % cccc
    """
    println(text.trimMargin("%"))  // 边界前缀

    // 字符模板，可用于替换
    var a = 1
    // simple name in template:
    val s1 = "a is $a"

    // 表达式
    val s2 = "${s1.replace("is", "was")}, but now is $a"

    var b: String? = null
    val nullOrEmpty = b.isNullOrEmpty()// 针对CharSequence?的方法，可以为空的进行调用
    println("nullOrEmpty==>${nullOrEmpty}")
}

// 数组用Array类实现，并且还有一个 size 属性及 get 和 set 方法，使用[] 重载了 get 和 set 方法,可以[index]访问
// 还有ByteArray, ShortArray, IntArray表示各种类型的数组，省去了装箱操作。
fun arrayOpt() {
    val a = arrayOf(1, 2, 3)
    // 0,1,2调用init函数
    val b = Array(3, { i -> (i * 2) })

    println(a[0])
    println(b[1])
}

fun charOpt() {
    var c: Char = '1'
    val toInt = c.toInt()
    println(toInt)
}

fun bitOpt() {
    // int和Long类型
    val a: Int = 1
    a.shl(2)
    a.shr(2)
    a.ushr(2)
    a.and(2)
    a.or(2)
    a.xor(2)
    a.inv()
}

// 每种数据类型都有各种的toXX进行转换
fun typeConvert() {
    val b: Byte = 1
//    val i: Int = b// 每个类型都是独立的，不能自动转换

    val i: Int = b.toInt()
}

private fun compareDigit() {
    // 是基本类型
    val a: Int = 10000
    // 是对象类型
    val b: Int? = 10000
    // 比较值
    println(a == a)
    // 比较地址
    println(a === a)

    // todo-lichao,为什么是复制?Int?是对象类型了，包装类型
    // 装箱，创建对象，那这个就相当于是copy而不是引用了
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a

    // 比较地址
    println(boxedA === anotherBoxedA)
    // 比较值
    println(boxedA == anotherBoxedA)

    val aStr = "a"
    val bStr = "a"
    println(aStr == bStr)
    println(aStr === bStr)
}

