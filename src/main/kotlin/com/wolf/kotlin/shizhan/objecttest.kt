package com.wolf.kotlin.shizhan

// Kotlin中没有静态属性和方法， 但是可以使用关键字object声明一个object单例对象
// 声明对象类型
object User {
    val username: String = "admin"
    val password: String = "admin"
    fun hello() {
        println("hello, object")
    }
}

// 伴生对象
// 一个类只能有一个伴生对象
class DataProcessor {
    companion object DataProcessor {
        fun process() {
            println("i am processing data .")
        }
    }
}

fun main() {
    println(User.username)
    println(User.password)
    User.hello()

    DataProcessor.process()
}