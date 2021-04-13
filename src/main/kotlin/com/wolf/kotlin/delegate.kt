package com.wolf.kotlin

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// Kotlin 直接支持委托模式，更加优雅，简洁。关键字 by 实现委托。

// 类的委托即一个类中定义的方法实际是调用另一个类的对象的方法来实现的。
interface Base2 {
    fun print()
}

class BaseImpl2(val x: Int) : Base2 {
    override fun print() {
        print(x)
    }
}

// 类委托要点：传入别委托类b2,通过by建立与其关系
// by 子句表示，将b2保存在 Delegate 的对象实例内部，而且编译器将会生成继承自 Base 接口的所有方法, 并将调用转发给b2。
class DelegateClass(b2: Base2) : Base2 by b2

fun main() {

//    optOperator()

//    classDelegate()

//    propertyDelegate()

//    lazyDelegate()

//    observe()

//    storeProperty2Map()

//    testNotNull()

    testLocalVar {
        println(1111)
        Foo4()
    }

//    delegateInternal()

//    testProvideDelegate()
}

data class PersonOpt(var name: String, var age: Int) {
    operator fun plus(other: PersonOpt): PersonOpt {
        return PersonOpt(this.name + "+" + other.name, this.age + other.age)
    }
}

// 运算符重载，重载的修饰符是operator，+号对应的函数是plus
operator fun Int.plus(b: PersonOpt): Int {
    return this - b.age
}

// 运算符重载实际上是函数重载，本质上是对运算符函数的调用，从运算符到对应函数的映射过程由编译器完成。
fun optOperator() {
    val person = PersonOpt("A", 3)
    val testInt = 5
    println("${testInt + person}")

    var person1 = PersonOpt("A", 3)
    var person2 = PersonOpt("B", 4)
    // person类重载了+符号，即plus函数
    println(person1 + person2)
}

fun testProvideDelegate() {
}

// 后续研究
//class ResourceID<U> {
//    object image_id {
//
//    }
//
//    object text_id {
//
//    }
//
//}
//
//class ResourceLoader<T>(id: ResourceID<T>) {
//    operator fun provideDelegate(thisRef: MyUI, prop: KProperty<*>): ReadOnlyProperty<MyUI, T> {
//        checkProperty(thisRef, prop.name)
//
//    }
//
//    private fun checkProperty(thisRef: MyUI, name: String) {}
//}
//
//fun <T> bindResource(id: ResourceID<T>): ResourceLoader<T> {
//    return ResourceLoader<T>(ResourceID())
//}
//
//class MyUI {
//    val image by bindResource(ResourceID.image_id)
//    val text by bindResource(ResourceID.text_id)
//}


class C7 {
    var prop: String by MyDelegate()
}

class MyDelegate {
    // 第一个参数 this 引用到外部类 C7 的实例，this::prop 是 KProperty 类型的反射对象，该对象描述 prop 自身。
    operator fun getValue(c7: C7, property: KProperty<*>): String {
        return ""
    }

    operator fun setValue(c7: C7, property: KProperty<*>, s: String) {

    }
}

// 在每个委托属性的实现的背后，Kotlin 编译器都会生成辅助属性并委托给它。
// 例如，对于属性 prop，生成隐藏属性 prop$delegate，而访问器的代码只是简单地委托给这个附加属性：
fun delegateInternal() {
    val prop1 = C7().prop

    // 这段是由编译器生成的相应代码：
//    class C7 {
//        private val prop$delegate = MyDelegate()
//        var prop: Type
//            get() = prop$delegate.getValue(this, this::prop)
//        set(value: Type) = prop$delegate.setValue(this, this::prop, value)
//    }
}

// by lazy应用于常量,默认是by lazy(LazyThreadSafetyMode.SYNCHRONIZED)同步的
// by lazy()是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托
// 懒加载，只有用到时才会对控件初始化
// 将局部变量声明为委托属性。 例如，可以使一个局部变量惰性初始化：
fun testLocalVar(computeFoo: () -> Foo4) {
    val memorizedFoo by lazy(computeFoo)

    // memoizedFoo变量只会在第一次访问时计算，若条件失败，则该变量不会计算
    var somCondition = true
    if (somCondition && memorizedFoo.isValid()) {
        memorizedFoo.doSomething()
    }

    // 再次调用则直接返回
    memorizedFoo.isValid()

}

class Foo3 {
    var notNullBar: String by Delegates.notNull<String>()
}

class Foo4 {
    fun isValid(): Boolean {
        return true
    }

    fun doSomething() {
    }

}

// notNull 适用于那些无法在初始化阶段就确定属性值的场合。
fun testNotNull() {
    val foo = Foo3()
    // 报错
//    println(foo.notNullBar)
    foo.notNullBar = "bar"
    println(foo.notNullBar)
}

class Site2(val map: Map<String, Any?>) {
    val name: String by map
    val url: String by map
}

// 属性使用var则需要用MutableMap
class Site3(val map: MutableMap<String, Any?>) {
    var name: String by map
    var url: String by map
}

// map中存储属性值，可以使用映射实例自身作为委托来实现委托属性
fun storeProperty2Map() {
    val site = Site2(
        mapOf(
            "name" to "name1",
            "url" to "www.url"
        )
    )

    println(site.name)
    println(site.url)

    var map: MutableMap<String, Any?> = mutableMapOf(
        "name" to "name1",
        "url" to "www.url"
    )
    val site2 = Site3(map)
    println(site2.name)
    println(site2.url)
}

class User3 {
    var name: String by Delegates.observable("初始值") { prop, old, new ->
        println("oldValue:$old -> newValue:$new")
    }
}

fun observe() {
    val user = User3()
    user.name = "first value"
    user.name = "second value"
}

// 标准委托
fun lazyDelegate() {
//    val lazyValue: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {// 默认LazyThreadSafetyMode.SYNCHRONIZED
    val lazyValue: String by lazy {
        println("computed!")// 第一次调用则执行，第二次则不执行
        "Hello"
    }
    println(lazyValue)
    println(lazyValue)
}

// 属性委托
// 一个类的某个属性值不是在类中直接进行定义，而是将其托付给一个代理类，从而实现对该类的属性统一管理
// 属性委托语法格式：val/var <属性名>: <类型> by <表达式>，表达式表示：委托代理类
// 属性的 get() 方法以及set() 方法将被委托给这个对象的 getValue() 和 setValue() 方法。属性委托不必实现任何接口, 但必须提供 getValue() 函数(对于 var属性,还需要 setValue() 函数)
private fun propertyDelegate() {
    val e = Example2()
    println(e.p)

    e.p = "Runoob"
    println(e.p)
}

private fun classDelegate() {
    val b = BaseImpl2(10)
    DelegateClass(b).print()
}

// 被委托的类
class Example2 {
    // 字段p，委托给Delegate进行get/set
    var p: String by Delegate()
}

// 委托的类
class Delegate {
    // 属性委托要求
    // 1.对于只读属性(val属性), 它的委托必须提供一个名为getValue()的函数。该函数接受以下参数：
// thisRef —— 必须与属性所有者类型（对于扩展属性——指被扩展的类型）相同或者是它的超类型
// property —— 必须是类型 KProperty<*> 或其超类型
// 这个函数必须返回与属性相同的类型（或其子类型）。
// 2.对于一个值可变(mutable)属性(var属性),除 getValue()函数之外,它的委托还必须另外再提供一个名为setValue()的函数, 这个函数接受以下参数:
//property —— 必须是类型 KProperty<*> 或其超类型
//new value —— 必须和属性同类型或者是它的超类型。

    // thisRef是被委托的类的对象，prop是被委托的属性的对象
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "获取 $thisRef, 委托了 ${property.name}属性"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("设定 $thisRef 的 ${property.name} 属性值为 $value")
    }
}
