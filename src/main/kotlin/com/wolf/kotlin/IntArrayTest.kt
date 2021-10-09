package com.wolf.kotlin

fun main() {
    // 测试数据拷贝，只要后16位
    val value = intArrayOf(1, 1, 32, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2)
    val diff = value.size - 16

    val dest = IntArray(16)
    System.arraycopy(value, diff, dest, 0, 16)
    println(dest.contentToString())
}
