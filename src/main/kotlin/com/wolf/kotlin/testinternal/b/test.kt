package com.wolf.kotlin.testinternal.b

import com.wolf.kotlin.testinternal.a.B2


// 这是模块可见，应该是得打包成另一个maven项目，然后依赖之后就不能用了
// a module is a set of Kotlin files compiled together:
// 1.an IntelliJ IDEA module
// 2.a Maven project
// 3.a Gradle source set (with the exception that the groovy.reg.test source set can access the internal declarations of main)
// 4.a set of files compiled with one invocation of the <kotlinc> Ant task
fun main() {
    val b = B2()
    b.foo()
    b.foo2()
}