package com.wolf.kotlin

import org.junit.Before
import org.junit.Test

// Kotlin 类可以包含：构造函数和初始化代码块、函数、属性、内部类、对象声明
class Runoob {
    // 属性
    var name: String = "a"

    fun foo() {// 成员函数
        print("Foo")
    }
}

// 空类
class Empty

// Kotlin中，声明类的同时可以方便的直接声明构造方法等参数
// 类可以有一个主构造器，一个或多个次构造器，主构造器是类头部的一部分，位于类名之后
class Person constructor(firstName: String) {
}

// 如果主构造器没有任何注解和可见度修饰符，那么constructor关键字可以省略
class Person1(firstName: String) {
}

// 这样的重写get/set
class Person2 {
    var name: String = "a"// 默认实现get/set

    var lastName: String = "zhang"
        get() = field.toUpperCase()// 重写了get方法，用使用field解决重复调用自己get造成死循环问题
        set

    var no: Int = 100
        get() = field // 后端变量
        set(value) {
            if (value < 10) {
                field = value
            } else {
                field = -1
            }
        }

    var heiht: Float = 145.4f
        private set
}

// 非空属性必须在定义的时候初始化,而lateinit可以延迟初始化
class MyTest {
    lateinit var subject: Person2

    @Before
    fun setup() {
        subject = Person2()
    }

    @Test
    fun test() {
        println(subject.name)
    }
}

// 主构造函数，constructor(firstName: String)，不包含任何代码
class Person3 constructor(firstName: String) {
    init {// 初始化代码段
        println("FirstName is $firstName")
    }
}

// 通过主构造函数器，定义属性并初始化
class Person4 public constructor(var firstName: String, var lastName: String) {
    init {
        println("FirstName is $firstName")
    }
}

class Person5 {

    var firstName: String

    // 二级构造函数
    constructor(firstName: String) {
        this.firstName = firstName
    }
}

// 若有主构造函数，则次构造函数需要直接或间接调用到主构造函数。
class Person6(val name: String) {
    // 调用另一个构造函数
    constructor(name: String, age: Int) : this(name) {
    }
}

// 对于非抽象类，若没有声明构造函数，默认产生一个没有参数的public构造函数，若不想则需要主动私有化
class Person7 private constructor() {
}

class Person8(val customerName: String = "") {
}

// 外部类
class Outer {
    // 嵌套类
    class Nested {
        fun foo() = 2
    }

    private val bar: Int = 1
    var v = "成员属性"

    // 嵌套内部类
    inner class Inner {
        // 访问外部类的成员
        fun foo() = bar
        fun innterTest(): Outer {
            // 获取外部类的引用
            var o = this@Outer
            println("内部类可以引用外部类的成员，如:" + o.v)
            println("内部类可以引用外部类的成员，如:" + v)
            return o
        }
    }
}

fun main() {
//    testConstruct()

//    testConstructHasParam()

//    testProperty()

//    testMainConstructor()

//    testMainConstructorHasField()

//    testSecondConstructor()

//    testInvoceOtherConstructor()

//    testPrivateConstructor()

//    testDefaultValConstructor()

//    testAbstract()

//    testUsingNested()

//    testUsingInner()

//    testAnnonyClass()
}

// 类的修饰符包括 classModifier 和_accessModifier_:
// classModifier: 类属性修饰符，标示类本身特性。
//abstract    // 抽象类
//final       // 类不可继承，默认属性
//enum        // 枚举类
//open        // 类可继承，类默认是final的
//annotation  // 注解类

// accessModifier: 访问权限修饰符
//private    // 仅在同一个文件中可见
//protected  // 同一个文件中或子类可见
//public     // 所有调用的地方都可见，默认
//internal   // 同一个模块中可见
private fun foo() {}  // 文件内可见

public var bar: Int = 5// 随处可见

// 定义接口
interface TestInterFace {
    fun test()
}

class TestAnnony {
    var v = "成员属性"

    fun setInterFace(test: TestInterFace) {
        test.test()
    }
}

// 使用对象表达式创建匿名内部类的实例
fun testAnnonyClass() {
    var testAnnony = TestAnnony()

    // 采用对象表达式创建接口对象，匿名内部类实例
    testAnnony.setInterFace(object : TestInterFace {
        override fun test() {
            println("111")
        }
    })
}

fun testUsingInner() {
    val demo = Outer().Inner().foo()
    println(demo)
    val demo2 = Outer().Inner().innterTest()
    println(demo2)
}

// open 表明，类可继承
open class Base {
    open fun f() {}
}

// abstract 表明，抽象类
// 类本身，或类中的部分成员，都可以声明为abstract的
// 抽象成员在类中不需要具体的实现
// 无需对抽象类或抽象成员标注open注解。
abstract class Derived : Base() {
    override abstract fun f()
}

class Concreate : Derived() {
    override fun f() {
        println(1111)
    }
}

fun testAbstract() {
    val concreate = Concreate()
    concreate.f()
}

fun testUsingNested() {
    val fooGetVal = Outer.Nested().foo()
    println(fooGetVal)
}

fun testDefaultValConstructor() {
    // 在jvm虚拟机中，若主构造函数的所有参数都有默认值，编译器会生成一个附加的无参的构造函数，并直接使用默认值，
    // 这样使得kotlin可以更简单的使用像Jackson这种使用无参构造函数来创建类实例的库
    Person8()
}

// 如何使用静态方法还有待商榷?
fun testPrivateConstructor() {
}

fun testInvoceOtherConstructor() {
    val person6 = Person6("xx", 1)
    println(person6)
}

private fun testSecondConstructor() {
    var p: Person5 = Person5("xx")
}

private fun testMainConstructorHasField() {
    val person4 = Person4("xx", "x")
    println(person4.firstName)
}

private fun testMainConstructor() {
    val person3 = Person3("aa")
    println(person3)
}

private fun testProperty() {
    var per: Person2 = Person2()
    per.lastName = "wang"
    println("lastName:${per.lastName}")

    per.no = 9
    println("no:${per.no}")

    per.no = 20
    println("no:${per.no}")

    // 不能设定
//    per.heiht =1.0f
}

private fun testConstructHasParam() {
    val person = Person("aa")
}

private fun testConstruct() {
    val site = Runoob()// 调用构造函数创建类实例
    println(site.name)// 使用属性
}

//todo 不支持调用父类的二级构造函数?
//open class SuperClass() {
//    lateinit var firstName: String
//
//    // 二级构造函数
//    protected constructor(firstName: String) : this() {
//        this.firstName = firstName
//    }
//}
//
//
//class DeriveClass() : SuperClass() {
//
//    // 二级构造函数
//    constructor(firstName: String) : super(firstName) {
//        this.firstName = firstName
//    }
//}

