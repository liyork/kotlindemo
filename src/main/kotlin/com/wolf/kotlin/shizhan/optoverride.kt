package com.wolf.kotlin.shizhan

import com.wolf.kotlin.boxInt
import java.math.BigDecimal

class BoxInt(var i: Int) {
    operator fun times(x: BoxInt) = BoxInt(i * x.i)// 使用类成员函数的方式重载*
    override fun toString(): String {
        return i.toString()
    }
}

operator fun BoxInt.plus(x: BoxInt) = BoxInt(this.i + x.i)// 使用扩展函数的方式重载+

private fun testBase() {
    val a = BoxInt(3)
    val b = BoxInt(7)
    println(a + b)
    println(a * b)
}

// 设计一个类Complex， 实现复数的基本操作
class Complex {
    var real: Int = 0
    var image: Int = 0

    constructor()
    constructor(real: Int, image: Int) {
        this.real = real
        this.image = image
    }

    operator fun plus(c: Complex): Complex {
        return Complex(this.real + c.real, this.image + c.image)
    }

    operator fun minus(c: Complex): Complex {
        return Complex(this.real - c.real, this.image - c.image)
    }

    operator fun times(c: Complex): Complex {
        return Complex(this.real * c.real - this.image * c.image, c.image + this.image * c.real)
    }

    override fun toString(): String {
        val img = if (image >= 0) "+ ${image}i" else "${image}i"
        return "$real ${img}"
    }
}


private fun testComplex() {
    val c1 = Complex(1, 1)
    val c2 = Complex(2, 2)
    val p = c1 + c2
    val m = c1 - c2
    val t = c1 * c2
    println(p)
    println(m)
    println(t)
}

class Point(val x: Int, val y: Int) {
    operator fun unaryMinus() = Point(-x, -y)
    override fun toString(): String {
        return "Point(x=$x, y=$y)"
    }
}

operator fun BigDecimal.inc() = this + BigDecimal.ONE
operator fun BigDecimal.dec() = this - BigDecimal.ONE


private fun testUnaryOpt() {
    // 重载自增自减一元运算符
    val p1 = Point(1, 1)
    println(-p1)

    var bigDecimal1 = BigDecimal(100)
    var bigDecimal2 = BigDecimal(100)
    val tmp1 = bigDecimal1++
    val tmp2 = ++bigDecimal2
    println(tmp1)
    println(tmp2)
    println(bigDecimal1)
    println(bigDecimal2)

    var bigDecimal3 = BigDecimal(100)
    var bigDecimal4 = BigDecimal(100)
    val tmp3 = bigDecimal3--
    val tmp4 = --bigDecimal4
    println(tmp3)
    println(tmp4)
    println(bigDecimal3)
    println(bigDecimal4)
}

class Point1(val x: Int, val y: Int) {
    // 编译器会自动生成equals和hashCode函数，若有需要可以自己重写

    // 比较运算符的重载函数
    operator fun compareTo(other: Point1): Int {
        return this.x - other.x
    }
}


private fun testCompare() {
    val p1 = Point1(1, 1)
    val p2 = Point1(2, 2)
    println(p1 > p2)
}

fun main() {
//    testBase()

//    testComplex()

//    testUnaryOpt()

//    testCompare()
}


