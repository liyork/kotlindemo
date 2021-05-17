package com.wolf.kotlin

fun main() {
    val res = ByteArray(2)// 字节数组，每个字节8位
    val i = 20000
    // 高8位
    res[0] = (i.ushr(8) and 0xFF).toByte()
    // 低8位
    res[1] = (i and 0xFF).toByte()

    println("${res[0]}, ${res[1]}")
}