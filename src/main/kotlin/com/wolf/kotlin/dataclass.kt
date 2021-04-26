package com.wolf.kotlin

// 数据类
// 只包含数据的类
data class User2(val name: String, val age: Int)
// 编译器会自动的从主构造函数中根据所有声明的属性提取以下函数(但如果这些函数在类中已经被明确定义了，或者从超类中继承而来，就不再会生成)
// getter、setter/equals() / hashCode()/toString()/componentN()能够保证数据类可以使用解构声明/copy()浅拷贝

// 为了保证生成代码的一致性以及有意义，数据类需要满足以下条件：
// 主构造函数至少包含一个参数。
//所有的主构造函数的参数必须标识为val 或者 var ;
//数据类不可以声明为 abstract, open, sealed 或者 inner;
//数据类不能继承其他类 (但是可以实现接口)。--这个如何实现共享呢？类似java的继承属性过来或者go的嵌套类则继承属性?

// copy() 函数的实现类似：fun copy(name: String = this.name, age: Int = this.age) = User(name, age)


// 密封类
// 用来表示受限的类继承结构：当一个值为有限几种的类型, 而不能有任何其他类型时
// 枚举类型的值集合也是受限的，但每个枚举常量只存在一个实例，而密封类的一个子类可以有可包含状态的多个实例
// 密封类可以有子类，但是所有的子类都必须要内嵌在密封类中
// sealed 不能修饰 interface ,abstract class(会报 warning,但是不会出现编译错误)
sealed class Expr {
    data class Const(val number: Double) : Expr()
    data class Sum(val e1: Expr, val e2: Expr) : Expr()
    object NotANumber : Expr()
}


// 正常使用when时，所有类都是单独在外面定义的
//fun groovy.eval.eval(expr: Expr): Double = when (expr) {
//    is Const -> expr.number
//    is Sum -> groovy.eval.eval(expr.e1) + groovy.eval.eval(expr.e2)
//    NotANumber -> Double.NaN
//}

// 这就相当于将类Const/Sum给密封到Expr，然后when时就不用else了
fun eval(expr: Expr): Double = when (expr) {
    is Expr.Const -> expr.number
    is Expr.Sum -> eval(expr.e1) + eval(expr.e2)
    Expr.NotANumber -> Double.NaN
    // 不再需要 `else` 子句，因为我们已经覆盖了所有的情况
}

private fun testBase() {
    val jack = User2(name = "jack", age = 1)
    val olderJack = jack.copy(age = 2)
    println(jack)
    println(olderJack)

    // 组件函数允许数据类在解构声明中使用
    val jane = User2("jane", 35)
    // 解构声明
    val (name, age) = jane
    println("$name, $age years of age")

    val pair = Pair("a", 1)
    val triple = Triple(1, 2, 3)
    println(pair)
    println(triple)
}

data class BaseData(val stat: Int, val msg: String)
data class Result1(val stat: Int, val msg: String, val age: Int)
data class Result2(val baseData: BaseData, val age: Int)

open class BaseData3(open val stat: Int, open val msg: String)
data class Result3(override val stat: Int, override val msg: String, val age: Int) : BaseData3(stat, msg)

open class BaseData4 {
    var stat: Int? = null
    var msg: String? = null

}

data class Result4(val age: Int) : BaseData4()

fun <T : BaseData4> T.setBase(stat: Int?, msg: String? = null): T {
    this.stat = stat
    this.msg = msg
    return this
}

private fun testReuse() {
    // 这样会有很多重复字段
    Result1(1, "x", 1)

    // 使用引用，使用时费点劲，但是确实减少了重复
    Result2(BaseData(1, "x"), 1)

    // 使用继承，但是这样确实重复定义了，感觉kotlin的意思是，这个要进行覆盖而不是重用
    val result3 = Result3(1, "x", 1)
    println("${result3.stat},${result3.msg},${result3.age}")

    // 这样应该是是比较好，但是就是赋值时麻烦些，想着能不能给set返回this？那样用stream方式进行设定但是不能返回this。。。
    val result4 = Result4(1)
    result4.stat = 1
    result4.msg = "x"
    println("${result4.stat},${result4.msg},${result4.age}")
    // 可以扩展一下，只能扩展，不能将方法放到父类，因为那样返回的this只能是父类！
    val result5 = Result4(1).setBase(1, "x")
    println("${result5.stat},${result5.msg},${result5.age}")
}

fun main() {
//    testBase()

    testReuse()
}
