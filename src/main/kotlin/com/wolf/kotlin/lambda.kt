package com.wolf.kotlin

import com.wolf.java.MySamInterface
import com.wolf.java.Test

// lambda，匿名函数，可以执行，也可以传递给函数的一小段代码

//高阶函数
//lambda 的应用场景就是高阶函数，我们可以把一个 lambda 当做参数传递到高阶函数中，或者返回一个 lambda。
//高阶函数就是以另一个函数作为参数或者返回值的函数

// 函数类型
// (Int, Int) -> Unit
// 函数类型，需要将函数参数类型放在括号中，紧接着是一个箭头和函数的返回类型
//一般情况下，函数的返回值如果是 Unit 是可以省略的，但是在声明函数类型时必须显示的声明返回类型，不可省略

// Lambda 表达式的语法,lambda表达式始终用花括号包围
// {param1:Type,param2:Type… -> ….}

fun main() {

//    lambdaBase()

//    funType()

//    lambdaValue()

//    methodHashLambdaParam { a, b -> a + b }

//    testLambda()

//    lambdaEvolve()

    memberRef()

//    typeAlias()

//    samConvert()

//    hasReceiver()

//    lambdaUse()

//    lambdaReturn()

//    catchVar()
}

// lambda变量捕捉
fun catchVar() {
    sum2(1, 1)
}

fun invoke(func: () -> Unit) {
    func()
}

// 局部变量或参数被lambda捕捉后，使用该变量的代码块可以被存储并延迟执行
fun sum2(x: Int, y: Int) {
    var count = x + y
    invoke {
        count++
        println("$x + $y +1 = $count")
    }
}

private fun lambdaBase() {
    // lambda表达式
    val sum = { x: Int, y: Int ->
        x + y
        x - y//最后一行为lambda的结果
    }
    // 运行
    println(sum(1, 2))
    val action = { println(42) }
    action()
}

fun lambdaReturn() {
    val arr = listOf("java", "kotlin")
    val buffer = with(StringBuffer()) {
        arr.forEach {// foreach是inline所以会被编译到这里，然后return就是到fun那里了
            if (it == "kotlin") {
                return@with this.append(it)
            }
        }
    }
    println(buffer.toString())

    testReturn1()
    testReturn2()
}

fun testReturn1() {
    StringBuffer().apply {
        println("in sb")
        return// 直接退出当前函数testReturn
    }
    println("out sb")
}

fun testReturn2() {
    var a = fun(x: Int, y: Int): Int {
        return x + y// 返回的是匿名函数而不是testReturn2
    }

    a(1, 2)
    println("after a")
}


// 场景
fun lambdaUse() {
    // 与集合一起使用
    val la = listOf("a", "b")
    la.filter {
        it.contains("a")
    }.forEach {
        println(it)
    }

    // 替代函数式接口实例
    Thread { println(111) }// Runnable

    testReceiveFunc { i ->
        println(i + 1)
        i.toString()
    }
}

fun testReceiveFunc(str: (Int) -> String) {

}

// 带接收者的lambda表达式
fun hasReceiver() {
    // 在A类型的接收者对象上调用q这个lambda，并返回一个C类型值的函数
    // 好处是:
    // a.在lambda函数体可以无需任何额外的限定符的情况下，直接使用接收者对象的成员（属性或方法）--这个是A对象发起的调用当然可以用其内部属性
    // b.可使用this访问接收者对象
    // 在扩展函数中，this关键字也执行扩展类的实例对象，而且也可以被省略掉。扩展函数某种意义上就是带接收者的函数。
    // 扩展函数和带接收者的lambda极为相似，双方都需要一个接收者对象，双方都可以直接调用该对象的成员。如果将普通lambda当作普通函数的匿名方式来看看待，那么带接收者类型的lambda可以当作扩展函数的匿名方式来看待。
    testReceiver(A()) {
        this.f()
        C()
    }

    //Kotlin的标准库中就有提供，带接收者的lambda表达式：with和apply
    val stringBuffer = StringBuffer()
    // with函数，显式调用带有接收者的lambda，并将lambda最后一个表达式的返回值作为with函数的返回值返回。
    val result = with(stringBuffer) {
        append("a")
        append("b")
        this.toString()
    }
    println(result)

    // apply函数是扩展函数，几乎和with函数一模一样，唯一区别是apply始终返回接收者对象
    val stringBuilder = StringBuilder().apply {
        append("daqi在努力学习Android")
    }.apply {
        append("daqi在努力学习Kotlin")
    }
    println(stringBuilder.toString())
}

fun testReceiver(a: A, q: A.() -> C) {
    a.q()
}

// 函数式接口:只定义一个抽象方法的接口
// SAM转换就是将lambda显示转换为函数式接口实例，但要求Kotlin的函数类型和该SAM（单一抽象方法）的函数类型一致。SAM转换一般都是自动发生的
// SAM构造方法是编译器为了将lambda显示转换为函数式接口实例而生成的函数。SAM构造函数只接收一个参数 —— 被用作函数式接口单抽象方法体的lambda，并返回该函数式接口的实例。
// SAM构造方法的名称和Java函数式接口的名称一样
// SAM转换只适用于接口，不适用于抽象类，即使这些抽象类也只有一个抽象方法。
//SAM转换 只适用于操作Java类中接收Java函数式接口实例的方法。因为Kotlin具有完整的函数类型，不需要将函数自动转换为Kotlin接口的实现。
// 因此，需要接收lambda的作为参数的Kotlin函数应该使用函数类型而不是函数式接口。
fun samConvert() {
    val interfaceObj = MySamInterface {
        "myinterface"
    }
    Test().setMyInterface(interfaceObj)
    println(interfaceObj is MySamInterface)
}

fun lambdaValue() {
    val min: (Int, Int) -> Int = { x, y ->
        // 最后一句表达式的返回值为lambda的返回值
        if (x < y) {
            x
        } else {
            y
        }
    }
}

// 函数类型
// 函数变量的类型统称函数类型，是声明该函数的参数类型列表和函数返回值类型。
fun funType() {

    // 函数类型，使用 -> 作分隔符，是将参数类型列表和返回值类型分开，所有函数类型都有一个圆括号括起来的参数类型列表和返回值类型。
    // 无参、无返回值的函数类型
    fun a(a: () -> Unit) {}

    // 接收T/String类型参数、返回值R的函数类型
    fun <T, R> a1(a: (T, String) -> R) {}
}

class Person12 {
    fun age(i: Int): String {
        return i.toString()
    }
}

// 顶层函数
fun qqq() {
    println("qqq")
}

// 扩展函数
fun Person.getPersonAge() {
    println("getPersonAge")
}

// 成员引用
fun memberRef() {
    // 创建一个调用单个方法的函数值
    var a = Person12::age
    a(Person12(), 1)

    // 顶层函数的成员引用(不附属于任何一个类)
    var qq = ::qqq
    qq()

    val extPersonFunRef = Person::getPersonAge
    extPersonFunRef(Person("1"))

    // 对构造函数使用成员引用
    val createPerson = ::Person
    createPerson("11")

}

// lambda演化
fun lambdaEvolve() {
    // 目标方法需要一个函数，这里用了匿名内部类
    test1(fun(): Foo4 {
        println(1111)
        return Foo4()
    })

    // lambda表示
    test1({
        println(1111)
        Foo4()
    })

    // 由于lambda在方法的最后，就可以放到括号外
    test1() {
        println(1111)
        Foo4()
    }

    // 由于test1方法就一个lambda参数，所以省去了()
    test1 {
        println(1111)
        Foo4()
    }

    // 有参数的lambda
    test2 { i: Int ->
        println(i)
        Foo4()
    }

    // 类型可以自动推导
    test2 { i ->
        println(i)
        Foo4()
    }

    // 默认一个参数时，变量是it
    test2 {
        println(it)
        Foo4()
    }

    // kotlin中的lambda和java中的还是不太一样，kotlin中直接用{xx->qqq}就表示lambda了，而java中还是需要
    // java方式
//    new Thread(() -> {
//        System.out.println(111);
//    });

    // kotlin方式，简化了不少，自动创建Runnable，若是此内部类中没有引用外界变量则可以重用
    Thread {
        println()
    }
}

// lambda
fun testLambda() {
    // 变量名   类型是 入参int，int 返回值int  值为表达式，x,y为参数,x+y为body
    val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
    // 等同于上面
    val sumLambda1: (Int, Int) -> Int = fun(a: Int, b: Int): Int {
        return 1
    }
    println(sumLambda(1, 2))
}

fun methodHashLambdaParam(operation: (Int, Int) -> Int) {
    //调用函数类型的参数
    val result = operation(2, 3)
    println("The result is $result")
}


// 声明函数类型的参数的时候可以指定默认值。
//fun <T> Collection<T>.joinToString(
//    separator: String = ",",
//    prefix: String = "",
//    postfix: String = "",
//    transform: (T) -> String = { it.toString() }//指定参数默认值
//): String {
//或者可空函数类型：
//    fun <T> Collection<T>.joinToString(
//        separator: String = ",",
//        prefix: String = "",
//        postfix: String = "",
//        transform: ((T) -> String)?//可空函数类型
//    ): String {

fun returnLambda(a: Int): (Int) -> Int {
    return { a }
}

fun test1(computeFoo: () -> Foo4) {

}

fun test2(computeFoo: (i: Int) -> Foo4) {
}

typealias alias = (String, (Int, Int) -> String) -> String
typealias alias2 = () -> Unit
typealias mm = ArrayList<String>

// 类型别名为现有类型提供替代名称,不会引入新类型，它等效于相应的底层类型
fun typeAlias() {
    mm()
}