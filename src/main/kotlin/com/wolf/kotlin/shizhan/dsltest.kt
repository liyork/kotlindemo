package com.wolf.kotlin.shizhan

import java.io.*
import java.util.*

class Hello {
    // 定义了一个操作符函数invoke
    operator fun invoke(name: String) {
        println("hello, $name")
    }
}


private fun testOptSign() {
    // 在类的对象实例中可以像函数那样直接使用“()”操作符来调用这个类的invoke操作符函数
    val hello = Hello()// 将实例对象hello作为函数调用
    hello("world")// Hello对象的invoke方法的调用约定写法
}

// 为List扩展一个函数sort
fun <T : Comparable<T>> List<T>.sort() {
    Collections.sort(this)
}

// 创建一个给定文件名返回文件中每行文本字符串的流式API
fun String.stream() = FileInputStream(this)

fun FileInputStream.buffered() = BufferedInputStream(this)

fun InputStream.reader(charset: String) = InputStreamReader(this, charset)

fun Reader.readLines(): List<String> {
    val result = arrayListOf<String>()
    forEachLine {
        result.add(it)
    }
    return result
}

// 实现一个SQL风格的集合类DSL
data class Student1(var name: String, var sex: String, val score: Int)

fun <E> List<E>.select(): List<E> = this

fun <E> List<E>.where(predicate: (E) -> Boolean): List<E> {
    val list = this
    val result = arrayListOf<E>()
    for (e in list) {
        if (predicate(e)) {
            result.add(e)
        }
    }
    return result
}

fun <E> List<E>.and(predicate: (E) -> Boolean): List<E> {
    return where(predicate)
}

fun main() {
//    testOptSign()

//    testStreamAPI()

//    testFilterCollectionLikeSQL()
}

private fun testFilterCollectionLikeSQL() {
    // 集合类的过滤查询函数具备SQL一样的风格
    val students = listOf(
        Student1("j", "M", 90),
        Student1("a", "F", 70),
        Student1("b", "M", 60),
        Student1("bi", "M", 80)
    )
    val queryResult = students.select()
        .where { it.score >= 80 }
        .and { it.sex == "M" }
    println(queryResult)
}

private fun testStreamAPI() {
    // 流式API
    val lines = "/Users/chaoli/intellijWrkSpace/kotlindemo/src/main/kotlin/com/wolf/kotlin/shizhan/b.txt".stream()
        .buffered()
        .reader("utf-8")
        .readLines()
    lines.forEach(::println)
}
