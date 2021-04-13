package com.wolf.kotlin

import java.util.concurrent.ConcurrentHashMap

// 函数名	定义inline的结构	函数体内使用的对象	返回值	是否是扩展函数	适用的场景
//let	fun <T, R> T.let(block: (T) -> R): R = block(this)	it指代当前对象	闭包形式返回	是	适用于处理不为null的操作场景
//with	fun <T, R> with(receiver: T, block: T.() -> R): R = receiver.block()	this指代当前对象或者省略	闭包形式返回	否	适用于调用同一个类的多个方法时，可以省去类名重复，直接调用类的方法即可，经常用于Android中RecyclerView中onBinderViewHolder中，数据model的属性映射到UI上
//run	fun <T, R> T.run(block: T.() -> R): R = block()	this指代当前对象或者省略	闭包形式返回	是	适用于let,with函数任何场景。
//apply	fun T.apply(block: T.() -> Unit): T { block(); return this }	this指代当前对象或者省略	返回this	是	1、适用于run函数的任何场景，一般用于初始化一个对象实例的时候，操作对象属性，并最终返回这个对象。 2、动态inflate出一个XML的View的时候需要给View绑定数据也会用到. 3、一般可用于多个扩展函数链式调用 4、数据model多层级包裹判空处理的问题
//also	fun T.also(block: (T) -> Unit): T { block(this); return this }	it指代当前对象	返回this	是	适用于let函数的任何场景，一般可用于多个扩展函数链式调用

class Target {
    fun sayHello(): String {
        println("sayHello")
        return "sayHello"
    }
}

fun main() {
//    funcCode()

//    extension()

//    dataClass()

//    invokeJava()

//    callbackSimple()

//    letOpt()

//    withOpt()

//    runOpt()

    applyOpt()

//    alsoOpt()

//    singletonOpt()

//    inlineOpt()
}

// best for functions with parameters of functional types
//几个注意点：
//a.在public的方法里不能够访问私有的成员
//b.小心使用流程跳转，如return语句，由于被inline可能直接退出了被inline的函数
inline fun inlined(getString: () -> String?) = println(getString())
fun notInlined(getString: () -> String?) = println(getString())

fun inlineOpt() {
    var testVar = "Test"
    notInlined { testVar }
    inlined { testVar }
}

class Hoge {
    // 单例
    object A {
        val fizz = "fizz"
        fun foo() {
            println("foo")
        }
    }

    // 属于这个类的单例，应该是单例，然后和这个类有关系
    companion object {
        val buzz = "buzz"
        fun bar() {
            println("bar.")
        }
    }
}

fun singletonOpt() {
    Hoge.A.foo()
    println(Hoge.A.fizz == Hoge.A.fizz)

    Hoge.bar()
    println(Hoge.buzz == Hoge.buzz)
}

// 与let类似，不过let以闭包形式返回，返回函数体内最后一行的值，若最后一行为空则返回Unit类型的默认值
// also返回this，即调用also的对象
fun alsoOpt() {
    val peo = Person4("xx", "yy")
    val also = peo.also {
        it.firstName
    }
    println(also)
}

// apply一般用于一个对象实例初始化的时候，需要对对象中的属性进行赋值。
// 返回this，即调用apply的对象
fun applyOpt() {
    val peo = Person4("xx", "yy")

    peo.apply {
        println(firstName)
    }.apply {
        println(lastName)
    }
    // 由于返回了this，可以使用流式调用
}

// 结合了let(用于判空)，和with(直接访问属性)
fun runOpt() {
//    val peo = Person4("xx", "yy")
    val peo: Person4? = null
    peo?.run {
        // 直接使用属性
        println("my length $firstName")
    }
}

// 对象属性调用简化，内置了this
fun withOpt() {
    val peo = Person4("xx", "yy")
    with(peo) {
        this.firstName = "x1"
        lastName = "y1"
    }
    println("${peo.firstName},${peo.lastName}")

    // 演化
    var with = with(peo, {
        println("xxx with")
        1000
    })

    // with函数最后一个参数是一个函数，可以把函数提到圆括号外部
    with = with(peo) {
        println("xxx with")
        1000
    }
    // 返回值为函数块的最后一行或指定return表达式
    println(with)
}

// let扩展函数的实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，let函数的是一个不错的选择；
// let函数另一个作用就是可以避免写一些判断null的操作。
fun letOpt() {
    var a: String? = "a"
    // 场景1:针对一个可null的对象统一做判空处理。
    val let = a?.let {// 明确一个变量所处特定的作用域范围内可以使用
        // 内部用it代指a
        println(it.length)
        it[0]
    }
    println(let)
}

interface NeedCallBack {
    fun onSuccess(date: String): Unit
}

fun testCallback(cb: NeedCallBack) {

}

fun testCallback2(cb: (String) -> Unit) {

}

// lambda表达式，只支持单抽象方法模型，接口里只有一个抽象方法。
fun callbackSimple() {
    // 实现一个接口的类
    testCallback(object : NeedCallBack {
        override fun onSuccess(date: String) {

        }
    })

    // 接口只有一个方法，符合使用lambda函数
    testCallback2({ date: String ->
        println(date)
    })

    // 省略参数类型,智能类型推导
    testCallback2({ date ->
        println(date)
    })

    // 没使用参数则省略
    testCallback2({
        println()
    })

    // testCallback2的最后一个参数是一个函数，将括号中的实现提到括号外
    testCallback2() {
        println()
    }

    // testCallback2只有一个参数，省略圆括号
    testCallback2 {
        println()
    }
}

fun invokeJava() {
    val concurrentHashMap = ConcurrentHashMap<String, String>()
    concurrentHashMap.put("a", "1")
    concurrentHashMap["b"] = "2"
    println(concurrentHashMap)
}

data class Person11(var name: String?, var age: Int?)

fun dataClass() {
    val person11 = Person11("xx", 1)
    println(person11.age)
}

// 动态扩展
private fun extension() {
    fun Target.swap(index1: Int, index2: Int) {
        println("target swap")
    }

    val target = Target()
    target.swap(1, 2)


    fun String?.notEmpty(): Boolean {
        return this != ""
    }

    var s: String? = null
    val notEmpty = s.notEmpty()
    println(notEmpty)
}

// 函数编程
private fun funcCode() {
    val sum: (Int, Int) -> Int = { x, y -> x + y }
    val sum1 = sum(1, 2)
    println(sum1)
}