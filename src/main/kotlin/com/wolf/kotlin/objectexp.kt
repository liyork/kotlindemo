package com.wolf.kotlin

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

// object 关键字可以表达两种含义：一种是对象表达式,另一种是对象声明
// 创建一个对某个类做了轻微改动的类的对象，且不需要去声明一个新的子类。
// 通过对象表达式实现一个匿名内部类的对象用于方法的参数中

// 使用场景
// 如果一个类是工具类，里面全是静态方法，此时我们可以使用对象声明；
// 如果一个类里面有部分静态成员（包括静态变量和方法），此时就可以使用companior object，而且这写静态与当前类有关联

interface Listener {
    fun foo()
}

fun addListener(listener: Listener) {

}


fun main() {
//    annoyclass()

//    objectImpl()

//    getObject()

//    objectExpAccess()

    // 对象声明
//    singletonObject()

//    objectSuper()

//    objectAccess()

    // 伴生对象
//    companionObject()

//    objectExpAndDeclarDiff()

//    testInitSeq()

//    testStatic()
}

fun testInitSeq() {
    Under()
    Under()
}

fun objectExpAndDeclarDiff() {
    // 对象表达式和对象声明之间的语义差异
    //   对象表达式是在使用他们的地方立即执行的
    //   对象声明是在第一次被访问到时延迟初始化的
    //   伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配
}

// 类内部的对象声明可以用companion关键字标记，这样它就与外部类关联在一起，就可以直接通过外部类访问到对象的内部元素。
class MyClass2 {
    // 单例
    companion object Factory {
        fun create(): MyClass2 = MyClass2()
    }
}

// companion伴生对象是个实际对象的单例实例
fun companionObject() {
    // 访问到对象的内部元素
    var instance = MyClass2.create()
    // 也可以通过对象访问
    instance = MyClass2.Factory.create()

    // 可以省略掉该对象的对象名，使用Companion替代需要声明的对象名
    MyClass3.create()
    MyClass3.Companion.create()

    MyClass4.create()
}

// 一个类里内只能使用一次。
class MyClass3 {
    companion object {
        fun create(): MyClass3 = MyClass3()
    }
}

interface Factory<T> {
    fun create(): T
}

// 伴生对象看起来像其他语言的静态成员，但在运行时他们仍然是真实对象的实例成员。还可以实现接口：
class MyClass4 {
    companion object : Factory<MyClass2> {
        override fun create(): MyClass2 {
            return MyClass2()
        }
    }
}


class Site {
    var name = "outname"

    // 静态内部对象
    object DeskTop {
        var url = "www.xxx"
        fun showName() {
            // 不能访问外部类的方法和变量
//            println("desk legs $name")
        }
    }
}

// 用object 修饰的类为静态类，里面的方法和变量都为静态的
// 当对象声明在另一个类的内部时，这个对象并不能通过外部类的实例访问到该对象，而只能通过类名来访问，
// 该对象也不能直接访问到外部类的方法和变量
fun objectAccess() {
    val site = Site()
    // 不能通过外部类的实例，访问该对象
//    site.DeskTop
    Site.DeskTop.url// 可以
    Site.DeskTop.showName()
}

// 对象可以有超类型：
object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent?) {
        super.mouseClicked(e)
    }
}

fun objectSuper() {

}

fun singletonObject() {
    // 使用单例对象
    DataProviderManager.registerDataProvider("aa")

    var data1 = DataProviderManager
    var data2 = DataProviderManager
    // 一个对象
    println(data1 == data2)
}

fun objectExpAccess() {
    C6()
}

// 通过对象表达式，可以越过类的定义，直接得到一个对象
private fun getObject() {
    val site = object {
        var name: String = "xx"
        var url: String = "yy"
    }
    println(site.name)
    println(site.url)
}

// 注意，匿名对象可以用作只在本地和私有作用域中声明的类型。
// 如果你使用匿名对象作为公有函数的返回类型或者用作公有属性的类型，那么该函数或属性的实际类型会是匿名对象声明的超类型，
// 如果你没有声明任何超类型，就会是 Any。在匿名对象 中添加的成员将无法访问。
class C6 {
    // 私有函数，返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    // 公有函数，返回类型是Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x
//        未能解析的引用"x"s
//        val x2 = publicFoo().x
    }
}

// 匿名类的的对象，可以继承某个基类，实现其他接口
private fun objectImpl() {
    val ab: A3 = object : A3(1), B {
        override val y = 15
    }
}

// 对象表达式
// 通过对象表达式实现，一个匿名内部类的对象，用于方法的参数中：
private fun annoyclass() {
    addListener(object : Listener {
        override fun foo() {
            TODO("Not yet implemented")
        }
    })
}

open class A3(x: Int) {
    public open val y: Int = x
}

interface B3 {

}

// 对象声明，全局对象
// 使用 object 关键字来声明一个对象
// 可以方便的通过对象声明来获得一个单例
object DataProviderManager {
    fun registerDataProvider(provider: String) {

    }

    val allDataProviders: Collection<String>
        get() {
            println(1111)
            return listOf("1")
        }
}

open class Super {
    init {
        println("Super")
    }

    companion object {
        init {
            println("Super companion")
        }
    }
}

class Under : Super() {
    init {// init若是构造多次则会被多次触发，那应该就是实例级别的方法
        println("Under")
    }

    companion object {
        init {// 单例对象的方法不会因为外部类构造多次触发多次
            println("Under companion")
        }
    }
}


class StaticDemoActivity {
    companion object {
        val LOAN_TYPE = "loanType"
        val LOAN_TITLE = "loanTitle"

        fun test() {
            println("static method test")
        }
    }
    // 类内只能存在一个
//    companion object {
//    }
}

// 常量，相当于java的public static final String LOAN_TYPE = "loanType"
fun testStatic() {
    val loanTitle = StaticDemoActivity.LOAN_TITLE
    println(loanTitle)

    StaticDemoActivity.test()

}

