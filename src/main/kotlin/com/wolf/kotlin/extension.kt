package com.wolf.kotlin

// 扩展可以对一个类进行属性和方法扩展，不需要继承或使用Decorator模式，是静态行为，不对原类影响。

class User(var name: String)

fun User.Print() {
    println("user name $name")
}

// 对MutableList扩展一个函数swap
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]// this指向当前调用对象
    this[index1] = this[index2]
    this[index2] = tmp
}

fun main() {
//    extUserOpt()

//    extListOpt()

//    extStatic()

//    extPriority()

//    extNullOpt()

//    extProperty()

//    extCompanion()

//    extPackage()

//    extInClassOpt()

    extOpenInClassOpt()
}

open class D4
class D5 : D4()

open class C4 {
    open fun D4.foo() {
        println("D4.foo in C4")
    }

    open fun D5.foo() {
        println("D5.foo in C4")
    }

    fun caller(d: D4) {
        d.foo()// 调用扩展函数，这是个静态的过程，以声明的D4为准
    }
}

class C5 : C4() {
    // 将对D4.foo的扩展给覆盖了
    override fun D4.foo() {
        println("D4.foo in C5")
    }

    override fun D5.foo() {
        println("D5.foo in C5")
    }
}

// 以成员形式定义的扩展函数，可以声明为open，可以被子类覆盖。
// 对这类扩展函数的派发过程中，针对分发接收者是虚拟的(virtual)--调用端，但针对扩展接受者仍然是静态的
fun extOpenInClassOpt() {
    C4().caller(D4())
    C5().caller(D4())// 分发接收者虚拟解析
//    C5().caller(D5())
    C4().caller(D5())// 扩展接收者静态解析
}

class D3 {
    fun bar() {
        println("D3 bar")
    }

    fun same() {
        println("D3 same")
    }
}

// 一个类内部为另一个类声明扩展
class C3 {
    fun baz() {
        println("C3 bar")
    }

    fun same() {
        println("C3 same")
    }

    // 扩展方法定义，所在类的实例，称为分发接受者，C3
    // 扩展方法的目标类型的实例称为扩展接受者，D3
    fun D3.foo() {
        bar()// 调用D3.bar
        baz()// 调用C3.baz

        same()// 调用D3.same()，扩展接收者优先
        this@C3.same()// 调用C3.same()，使用分发接收者的same
    }

    fun caller(d: D3) {
        d.foo()// 调用扩展函数
    }
}


fun extInClassOpt() {
    val c: C3 = C3()
    val d: D3 = D3()
    c.caller(d)
}

// 扩展包使用，参见ext
fun extPackage() {
}

class MyClass {
    companion object {}// 被称为COmpanion
}

fun MyClass.Companion.foo() {
    println("companion com.wolf.ext fun foo")
}

val MyClass.Companion.no: Int
    get() = 10

// 对类定义的的伴生对象进行扩展
fun extCompanion() {
    println("no:${MyClass.no}")
    MyClass.foo()
}

val <T> List<T>.lastIndex: Int
    get() = size - 1

// 扩展属性，允许定义在类或kotlin文件中，初始化属性因为没有backing field，
// 所以不允许被初始化，只能由显示提供的get/set定义。只能用val
fun extProperty() {
    val mutableListOf = mutableListOf<Int>(1, 2)
    val lastIndex = mutableListOf.lastIndex
    println(lastIndex)
}

// 扩展函数内，通过this判断接收者是否为null
fun Any?.toString(): String {
    if (this == null) return "null11"
    // 上面检测空后，this会自动转换为非空类型，下面即为Any类型的toString
    return toString()
}

fun extNullOpt() {
    var t = null
    println(t.toString())
}

class C2 {
    fun foo() {
        println("member fun foo")
    }
}

fun C2.foo() {
    println("extension fun foo")
}

// 优先使用成员函数
fun extPriority() {
    C2().foo()
}

open class C1
class D1 : C1()

fun C1.foo() = "c"
fun D1.foo() = "d"

// 扩展函数是静态解析的，即调用的对象的显示类型决定
fun printFoo(d1: D1) {
    println(d1.foo())
    var c1: C1 = d1
    println(c1.foo())
}

fun extStatic() {
    printFoo(D1())
}

fun extListOpt() {
    val l = mutableListOf<Int>(1, 2, 3)
    l.swap(0, 2)
    println(l)
}

private fun extUserOpt() {
    User("Runoob").Print()
}
