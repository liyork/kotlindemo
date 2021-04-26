package com.wolf.kotlin.inaction

import java.lang.StringBuilder
import kotlin.time.days


fun main() {
    // 定义以lambda为参数的buildString()
    fun buildString(builderAction: (StringBuilder) -> Unit): String {// 定义一个函数类型的参数
        val sb = StringBuilder()
        builderAction(sb)// 传递一个sb对象作为lambda的参数
        return sb.toString()
    }

    val s = buildString {
        it.append("Hello, ")
        it.append("world!")
    }
    println(s)

    // 重新定义以带接收者的lambda为参数的buildString
    // StringBuilder叫做接收者类型，()是参数，Unit是返回类型，传递给lambda的这个类型的值叫做接收者对象
    fun buildString1(builderAction: StringBuilder.() -> Unit): String {// 定义带接收者的函数类型的参数
        val sb = StringBuilder()
        sb.builderAction()// 传递一个sb对象作为lambda的接收者
        return sb.toString()
    }

    val s1 = buildString1 {
        this.append("hello, ")// 用this指向StringBuilder实例
        append("world!")// 省略this
    }
    println(s1)

    // 用变量保存带接收者的lambda，函数的签名可以说明lambda是否有接收者
    val appendExcl: StringBuilder.() -> Unit = { this.append("!") }// appendExcl是一个扩展函数类型的值
    val stringBuilder = StringBuilder("hi")
    stringBuilder.appendExcl()// 可以像调用扩展函数一样调用appendExcl
    println(stringBuilder)
    println(buildString(appendExcl))// 也可将appendExcl作为参数传递

    // apply/with
    val map = mutableMapOf(1 to "one")
    map.apply { this[2] = "two" }
    with(map) {
        this[3] = "three"
    }
    println(map)
}