package com.wolf.kotlin.shizhan

// 声明空类
class AnEmptyClass

private fun testEmptyClass() {
    val anEmptyClass = AnEmptyClass()
    println(anEmptyClass)
    println(anEmptyClass is AnEmptyClass)
    println(anEmptyClass::class)
}

// 声明类和构造函数
class Person(var name: String, var age: Int, var sex: String) {
    // 通过主构造器定义属性
    override fun toString(): String {
        return "Person(name='$name', age=$age, sex'$sex')"
    }
}

// 先声明属性，等构造实例对象后再初始化属性
class Person1 {
    lateinit var name: String // 属性延迟初始化
    var age: Int = 0
    lateinit var sex: String
    override fun toString(): String {
        return "Person1(name='$name', age=$age, sex'$sex')"
    }
}

// 有多种构造方式
class Person2() {
    // 无参的主构造函数
    lateinit var name: String
    var age: Int = 0// lateinit关键字不能修饰 primitive 类
    lateinit var sex: String

    constructor(name: String) : this() {// 次级构造函数
        this.name = name
    }

    constructor(name: String, age: Int) : this(name) {// 次级构造函数
        this.age = age
    }

    constructor(name: String, age: Int, sex: String) : this(name, age) {// 次级构造函数
        this.sex = sex
    }

    override fun toString(): String {
        return "Person2(name='$name', age=$age, sex'$sex')"
    }
}

private fun testConstructor() {
    val person = Person("jack", 29, "M")
    println("person = ${person}")

    val person1 = Person1()
    person1.name = "jack"
    person1.age = 29
    person1.sex = "M"
    println("person1 = ${person1}")

    val person21 = Person2()
    person21.name = "jack"
    person21.age = 29
    person21.sex = "M"
    println("person21 = ${person21}")

    val person22 = Person2("jack", 29)
    person22.sex = "M"
    println("person22 = ${person22}")

    val person23 = Person2("jack", 29, "M")
    println("person23 = ${person23}")
}

// 抽象类
abstract class Shape {
    open var width: Double = 0.0
    abstract var heigth: Double
    abstract var radius: Double
    abstract fun area(): Double

    // 带实现的函数，默认final，不可被覆盖重写
    fun onClick() {
        println("I am clicked")
    }

    open fun onClick1() {// 可被重写
        println("I am clicked1")
    }
}

// 继承类，父类需要在这里使用构造函数进行初始化
class Rectangle(
    override var width: Double, override var heigth: Double,
    override var radius: Double// 声明类的同时也声明了构造函数
) : Shape() {
    override fun area(): Double {
        return heigth * width
    }

    override fun onClick1() {
        println("${this::class.simpleName} is Clicked")// 反射api
    }
}

class Circle(
    override var width: Double, override var heigth: Double,
    override var radius: Double
) : Shape() {
    override fun area(): Double {
        return 3.14 * radius * radius
    }
}

fun main() {
//    testEmptyClass()

//    testConstructor()

    // 因为抽象的概念在问题领域中没有对应的具体概念， 所以抽象类是不能够实例化的
    //抽象类的成员也必须是抽象的， 需要使用abstract关键字修饰。
    val r = Rectangle(3.0, 4.0, 0.0)
    println(r is Shape)
    println(r.area())
    r.onClick()
    r.onClick1()

    val c = Circle(0.0, 0.0, 4.0)
    println(c.area())
    c.onClick()

}

