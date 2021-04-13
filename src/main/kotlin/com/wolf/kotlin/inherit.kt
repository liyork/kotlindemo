package com.wolf.kotlin

// Kotlin 中所有类都继承该 Any 类

// 从Any隐式继承
class Example

//Any 默认提供了三个函数：equals()/hashCode()/toString()

// 类默认是final的，如果要被继承，使用 open 关键字进行修饰

open class Base1(p: Int)// 基类
class Derived1(p: Int) : Base1(p)// 继承

open class Person9(var name: String, var age: Int) {}// 基类

// 如果子类有主构造函数，则基类必须在主构造函数中立即初始化。
class Student(name: String, age: Int, var no: String, var score: Int) : Person9(name, age) {}

fun main() {
//    subHasMainConstructor()

//    subHasSecondConstructor()

//    overrideFun()

//    overrideFunHasMultiImpl()

//    overrideProperty()

    overridePropertyThrowConstructor()
}

interface Foo2 {
    val count: Int
}

// 通过柱构造函数使用override进行属性覆盖
class Bar2(override val count: Int) : Foo2

class Bar3 : Foo2 {
    override var count: Int = 0
}

fun overridePropertyThrowConstructor() {
    val bar2 = Bar2(1)
    val bar3 = Bar3()
    println(bar2.count)
    println(bar3.count)
}

open class Foo {
    open val x: Int
        get() {
            return 1
        }
}

// 针对父类需要进行实例化，继承要用:XX()，针对接口不用，直接用:XX
class Bar1 : Foo() {
    override val x: Int
        get() {
            return 2
        }
}

fun overrideProperty() {
    val bar1 = Bar1()
    println(bar1.x)
}


open class A {
    open fun f() {
        print("A")
    }
}

interface B {
    fun f() {
        print("B")
    }
}

class C() : A(), B {
    // 继承的上层都有f实现，那么需要自己进行显示重写并决定如何实现
    override fun f() {
        super<A>.f()// 调用父类A的方法
        super<B>.f()// 调用接口B的方法
    }
}

fun overrideFunHasMultiImpl() {
    val c = C()
    c.f()
}

fun overrideFun() {
    val student3 = Student3()
    student3.study()
}


// 子类没有主构造函数，则必须在每一个二级构造函数中用 super 关键字初始化基类。
class Student2 : Person9 {
//    lateinit var age1: Int

    constructor(name: String, age: Int, age1: Int) : super(name, age) {}
}

fun subHasSecondConstructor() {
    val student2 = Student2("x", 1, 1)
    println(student2)
}

private fun subHasMainConstructor() {
    val s = Student("Runoob", 18, "S12345", 89)
    println("${s.name}")
    println("${s.age}")
    println("${s.no}")
    println("${s.score}")
}

open class Person10 {
    open fun study() {
        println("study person10")
    }
}

// 这里为什么有()?，因为这是继承类，所以要进行初始化，而接口则不用
class Student3 : Person10() {
    override fun study() {
        println("sub person10")
    }
}
