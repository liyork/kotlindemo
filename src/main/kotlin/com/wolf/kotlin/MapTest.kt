package com.wolf.kotlin

data class Param(var a: Int, var b: String?)

fun main() {
    val mutableMapOf = mutableMapOf<Int, String?>()
    mutableMapOf[1] = "a"
    mutableMapOf[2] = "b"

    // 有则返回没有则用默认值
    println(mutableMapOf.getOrDefault(1, "x"))
    println(mutableMapOf.getOrDefault(3, "x"))

//    mutableMapOf.getOrPut(1) { "x" }
//    println(mutableMapOf)
//    mutableMapOf.getOrPut(3) { "x" }
//    mutableMapOf.getOrPut(4) { null }// 即使null也会被放入
//    println(mutableMapOf)

    mutableMapOf.computeIfAbsent(1) { "x" }
    println(mutableMapOf)
    mutableMapOf.computeIfAbsent(3) { "x" }
    mutableMapOf.computeIfAbsent(4) { null }
    println(mutableMapOf)

    // 同getOrDefault，只不过参数是lambda了，都不对map产生影响。
    mutableMapOf.getOrElse(1) { "x" }
    println(mutableMapOf)
    mutableMapOf.getOrElse(3) { "x" }
    println(mutableMapOf)

    val map = mutableMapOf<Int, Param>()
    map[1] = Param(1, "1")
    // 有值才会继续向右求值或赋值
    map[2]?.a = 1
    println(map)

    val mapNull: Any? = null
    val a = mapNull as Map<String, String>?// aa as XX?，即使aa为空也没有关系
    println(a)

    val listOf = listOf(1, 2, 3)
    // flatMap中的闭包是k->Iterable<R>的，最后出来的是一个集合而不是集合的集合
    val flatMap = listOf.flatMap { listOf(it + 1) }
    println(flatMap)
}
