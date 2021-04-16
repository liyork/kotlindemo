package com.wolf.kotlin.shizhan

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KTypeParameter

open class BaseContainer<T>

class Container1<T : Comparable<T>> : BaseContainer<Int> {
    var elements: MutableList<T>

    constructor(elements: MutableList<T>) {
        this.elements = elements
    }

    fun sort(): Container1<T> {
        elements.sort()
        return this
    }

    override fun toString(): String {
        return "Container(elemetns=$elements)"
    }
}

fun isOdd(x: Int) = x % 2 != 0


private fun testGetKClass() {
    val container = Container1(mutableListOf<Int>(1, 3, 2, 5, 4))
    val kClass = container::class
//    val jClass = container.javaClass// 获取java Class对象，和kotlin的kClass不同
//    jClass = kClass.java// 同上
}

private fun testMethodRef() {
    // 函数调用
    isOdd(7)
    isOdd(2)
    // 函数引用
    val nums = listOf(1, 2, 3)
    var a = ::isOdd//引用函数isOdd，类型是(Int)->Boolean
    println(a)
    // 将isOdd函数当做一个参数使用
    val filteredNums = nums.filter(::isOdd)// ::isOdd就是一个函数类型(Int)->Boolean的值
    println(filteredNums)
}

// 属性引用
// 访问属性属于第一级对象，可以使用::操作符
// 表达式::one等价于类型为KProperty的一个值，对于可变属性，返回类型为KMutableProperty的值
var one = 1
fun testReflectProperty() {
    println(::one.get())
    ::one.set(2)
    println(one)
}


private fun testRefMethod() {
    // 引用一个对象实例的方法
    val digitRegex = "\\d+".toRegex()
    digitRegex.matches("7")
    val isDigit = digitRegex::matches
    isDigit("7")
}

class A<T>
open class C<T>
class B<T> : C<Int>()// 继承父类C<Int>()


private fun testGetGenericType() {
    // Java中的泛型采用擦拭法。
    //在程序运行时， 无法得到自己本身的泛型信息。但
    // 当这个类继承了一个父类， 父类中有泛型的信息时， 那么就可以通过调用getGenericSuperclass()方法得到父类的泛型信息。
    // getGenericSuperclass()是Generic继承的特例，对于这种情况子类会保存父类的Generic参数类型， 返回一个ParameterizedType。
    // 所说的Java泛型在字节码中会被擦除， 并不总是擦除成Object类型， 而是擦除到上限类型
    //在Kotlin也是一样的泛型机制通过反射能拿到的也只能是有继承父类泛型信息的子类泛型

    // 无法在此处获得运行时T的具体类型
//    val parameterizedType = A<Int>()::class.java.genericSuperclass as ParameterizedType// 报错
//    val actualTypeArguments = parameterizedType.actualTypeArguments
//    for (type in actualTypeArguments) {
//        val typeName = type.typeName
//        println("typeName = ${typeName}")
//    }

    // 继承了父类C<Int>时，此处能够获得运行时genericSuperclass T的具体类型
    val parameterizedType = B<Int>()::class.java.genericSuperclass as ParameterizedType
    val actualTypeArguments = parameterizedType.actualTypeArguments
    for (type in actualTypeArguments) {
        val typeName = type.typeName
        println("typeName = ${typeName}")
    }
}

// 说明Kotlin中的反射怎样获取泛型代码的基本信息
open class BaseContainer2<T>

// 这个BaseContainer2有无不影响测试数据，因为代码没有获取父类的泛型
class Container2<T : Comparable<T>> : BaseContainer2<Int> {
    var elements: MutableList<T>

    constructor(elements: MutableList<T>) {
        this.elements = elements
    }

    fun sort(): Container2<T> {
        elements.sort()
        return this
    }

    override fun toString(): String {
        return "Container(elements=$elements)"
    }
}

fun main() {
//    testGetKClass()

//    testMethodRef()

//    testReflectProperty()

//    testRefMethod()

//    testGetGenericType()

    val container = Container2(mutableListOf<Int>(1, 3, 2, 5, 4))
    val kClass = container::class// 获取container的KClass对象引用
    val typeParameters = kClass.typeParameters// typeParameters中存有类型参数的信息
    val kTypeParameter: KTypeParameter = typeParameters[0]
    println(kTypeParameter.isReified)
    println(kTypeParameter.name)
    println(kTypeParameter.upperBounds)
    println(kTypeParameter.variance)

    val constructors = kClass.constructors// constructors中存有构造函数的信息
    for (KFunction in constructors) {
        KFunction.parameters.forEach {
            val name = it.name
            val type = it.type
            println("name = ${name}, type = ${type}")
            for (KTypeProjection in type.arguments) {
                println(KTypeProjection.type)
            }
        }
    }
}


