package com.wolf.kotlin

fun main() {
    // fold，设定初始值，并累加每个元素
    val arr = intArrayOf(1, 2, 4, 6)
    val fold = arr.fold(2) { acc, i -> acc + i }
    println(fold)

}
