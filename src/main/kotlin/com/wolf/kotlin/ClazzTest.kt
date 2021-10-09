package com.wolf.kotlin

class Hello {

}

fun main() {
    // 获取java的class实例
    println(Hello::class.java)
    println(Hello().javaClass)

    // 获取kotlin的KClass实例
    println(Hello::class == Hello().javaClass.kotlin)
    println(Hello::class == Hello()::class)
}