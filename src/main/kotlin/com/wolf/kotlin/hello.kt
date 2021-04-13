package com.wolf.kotlin  //可选的包头

class Greeter(val name: String) {
    fun greet() {
        println("Hello, $name")
    }
}

// 包级可见
fun main() {
//    println("Hello World!")

    // 创建对象，调用方法
    Greeter("World!").greet()
}