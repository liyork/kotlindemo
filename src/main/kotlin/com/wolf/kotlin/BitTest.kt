package com.wolf.kotlin

fun main() {
    var a = "f"
    var parseInt1 = Integer.parseInt(a, 16)
    println(parseInt1)
    var b = 'f'.toString()

    parseInt1 = Integer.parseInt(b, 16)
    println(parseInt1)
    val parseInt = Integer.toBinaryString(15)
    println(parseInt)
}