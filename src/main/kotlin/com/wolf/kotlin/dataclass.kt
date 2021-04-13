package com.wolf.kotlin

// 数据类
// 只包含数据的类
data class User2(val name: String, val age: Int)
// 编译器会自动的从主构造函数中根据所有声明的属性提取以下函数(但如果这些函数在类中已经被明确定义了，或者从超类中继承而来，就不再会生成)
// equals() / hashCode()/toString()/componentN()/copy()

// 为了保证生成代码的一致性以及有意义，数据类需要满足以下条件：
// 主构造函数至少包含一个参数。
//所有的主构造函数的参数必须标识为val 或者 var ;
//数据类不可以声明为 abstract, open, sealed 或者 inner;
//数据类不能继承其他类 (但是可以实现接口)。

// copy() 函数的实现类似：fun copy(name: String = this.name, age: Int = this.age) = User(name, age)

fun main() {
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
