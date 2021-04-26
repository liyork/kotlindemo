@file:JvmName("ConstTest")

package com.wolf.kotlin.consttest

fun main(args: Array<String>) {
    println(age)
    println(test1.name)
    println(Person.sex)

    println(age1)
    println(test1.name1)
    println(Person.sex1)
}

const val age: Int = 28
val age1: Int = 28

object test1 {
    const val name: String = "liuliqianxiao"
    val name1: String = "liuliqianxiao"
}

class Person {
    companion object {
        const val sex: Int = 1
        val sex1: Int = 1
    }
}
