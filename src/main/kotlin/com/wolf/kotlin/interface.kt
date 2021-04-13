package hello

interface MyInterface {
    fun bar()
    fun foo() {// 默认实现
        println("foo")
    }

    var name: String// 接口中属性只能是抽象的，不允许初始化值，实现接口时，必须重写属性
}

// 一个类或对象可以实现一个或多个接口
class Child : MyInterface {
    override fun bar() {
        println("child bar")
    }

    override var name: String = "runoob"// 重写属性
}

interface B2 {
    fun bar() {
        print("bar")
    }
}

class D : MyInterface, B2 {
    override var name: String = "a"

    // 由于多个接口都有此方法，所以需要重写
    override fun bar() {
        super<B2>.bar()
    }
}

fun main() {
    implOpt()

    multiImplOpt()
}

fun multiImplOpt() {
    val d = D()
    d.foo()
}

private fun implOpt() {
    val c = Child()
    c.foo()
    c.bar()
    println(c.name)
}

