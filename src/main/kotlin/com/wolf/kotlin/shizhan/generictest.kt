package com.wolf.kotlin.shizhan

import java.util.*
import kotlin.collections.ArrayList

// 泛型接口
// 类型参数，放在接口名称后面
interface Generator<T> {
    operator fun next(): T// 接口函数中直接使用类型T
}


private fun testBase() {
    val gen = object : Generator<Int> {
        // 使用object声明一个Generator实现类
        override fun next(): Int {
            return Random().nextInt(10)
        }
    }
    println(gen.next())

    // 类型参数K和V在泛型类型被实例化和使用时， 将被实际的类型参数所替代
    val map = mutableMapOf<Int, String>(1 to "a", 2 to "b")// 类型推断
}

// 声明一个带类型参数的类
class Container<K, V>(var key: K, var value: V) {
    override fun toString(): String {
        return "Container(key=$key, value=$value)"
    }
}

private fun genericClass() {
    val container = Container<Int, String>(1, "A")// <K, V> 被具体化为<Int,String>
    println(container)
}

class GenericClass {
    // 类中的泛型函数
    fun <T> console(t: T) {
        println(t)
    }
}

interface GenericInterface {
    // 接口中的泛型函数
    fun <T> console(t: T)
}

// T的类型上界是Comparable,表示类型参数T代表的都是实现了Comparable接口的类,即都实现了compareTo方法
fun <T : Comparable<T>> gt(x: T, y: T): Boolean {
    return x > y
}

fun main() {
//    testBase()

//    genericClass()

    // 泛型函数
    // 类型上界

    // Kotlin中的泛型与Java一样是非协变的，即List<sub>的地方不能使用List<parent>
    //Java泛型中引入了类型通配符的概念来解决这个问题。 Java泛型的通配符有两种形式：
    //a.子类型上界限定符?extendsT指定类型参数的上限（该类型必须是类型T或者它的子类型）
    //b.超类型下界限定符?superT指定类型参数的下限（该类型必须是类型T或者它的父类型）
    // 问号“?”， 称之为类型通配符（Type Wildcard）
    // Number类型（简记为F） 是Integer类型（简记为C） 的父类型， 我
    //们把这种父子类型关系简记为C=>F（C继承F） ； 而List,List代表的泛型类型信息分别简记为f(F),f(C)
    // 当C =>F时， 如果有f(C) => f(F)， 那么f叫做协变；
    //当C => F时， 如果有f(F) => f(C)， 那么f叫做逆变。
    // 如果上面两种关系都不成立则叫做不变。

    // Java中的数组是协变的
    // Java中的泛型是非协变的

    // java中的协变与逆变参见GenericTest

    // Kotlin中引入了投射类型out T代表生产者对象， 投射类型in T代表消费
    //者对象， 使用投射类型(projected type)out T和in T来实现与类型通配符同
    //样的功能
    // List<?super T>dest是消费数据的对象，数据会被写入dest对象中，这些数据对象被“消费吞进肚子里”了（Kotlin中叫in T） 。
    //List<?extends T>src是生产提供数据的对象。 src会“产出”数据（ Kotlin中叫out T）
    // 在Kotlin中， 把只能保证读取数据时类型安全的对象叫做生产者， 用out T标记；out T等价于? extends T；
    // 把只能保证写入数据安全时类型安全的对象叫做消费者， 用in T标记；in T等价于? super T


}

