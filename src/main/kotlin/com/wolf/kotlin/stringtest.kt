package com.wolf.kotlin

fun main() {
    // toInt的字符串不能为空
    println(" ".toInt())

    // 从0寻找第一个不是空白字符的位置，然后subString截取
    println(" a ".trimStart())
    // 从len-1寻找第一个不是空白字符的位置，然后subString截取
    println(" a ".trimEnd())
    // 从0和len-1开始寻找第一个不是空白字符的位置，然后subString截取
    println(" a ".trim())
}