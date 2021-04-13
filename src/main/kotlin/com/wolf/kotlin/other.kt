package com.wolf.kotlin

fun main() {
    var arr = arrayOf("a", "b")
    // *加在数组前面，将数组变成可变参数
    testVargs(*arr)
}

fun testVargs(vararg ttt: String) {
    ttt.forEach { println(it) }
}