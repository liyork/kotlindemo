package com.wolf.kotlin.inaction

import io.vertx.kotlin.ext.consul.serviceListOf
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.StringBuilder
import java.util.*
import javax.print.attribute.standard.Destination
import kotlin.reflect.KClass

inline fun <reified T> isA(value: Any) = value is T

// filterIsInstance的简化实现
inline fun <reified T>// reified声明了类型参数不会在运行时被擦除
        Iterable<*>.filterIsInstance1(): List<T> {
    val destination = mutableListOf<T>()
    for (element in this) {
        if (element is T) {// 可以检查元素是不是指定为类型实参的类的实例
            destination.add(element)
        }
    }
    return destination
}

fun main() {
    // 编译器推导出类型实参
    val authors = listOf("D", "1")
    val readers: MutableList<String> = mutableListOf()// 变量声明中的说明泛型的类型
    val readers1 = mutableListOf<String>()// 函数中说明类型实参

    // 调用泛型函数
    val letters = ('a'..'z').toList()
    println(letters.slice<Char>(0..2))// 显示地指定类型实参
    println(letters.slice(0..2))// 编译器推导出T是Char

    // 调用泛化的高阶函数
    readers.filter { it !in authors }

    abstract class StringList : List<String> {// 实现List，提供具体类型实参String

        override fun get(index: Int): String = ""// T被String代替
    }

//    class ArrayList<T> : List<T> {// 现在ArrayList的泛型类型形参T就是List的类型实参
//        override fun get(index: Int): T =1
//    }

    // 声明带类型参数约束的函数
    fun <T : Comparable<T>> max(first: T, second: T): T {// 函数的实参必须是可比较的元素
        return if (first > second) first else second
    }
    println(max("kotlin", "java"))

    // 为一个类型参数指定多个约束
    fun <T> ensureTrailingPeriod(seq: T)
            where T : CharSequence, T : Appendable {// 类型参数约束的列表
        if (!seq.endsWith('.')) {// 调用为CharSequence接口定义的扩展函数
            seq.append('.')// 调用Appendable接口的方法
        }
    }

    val helloWorld = StringBuilder("hello,world")
    ensureTrailingPeriod(helloWorld)
    println(helloWorld)

    // 没有指定上界的类型形参将会使用 Any?这个默认的上界
    class Processor<T> {
        fun process(value: T) {
            value?.hashCode()// value是可空的，这里要安全调用
        }
    }

    val nullableStringProcessor = Processor<String?>()// 可空类型String?被用来替换T
    nullableStringProcessor.process(null)// 使用null作为value实参的代码可以编译

    class Processor1<T : Any> { // 指定Any，实现非空上界
        fun process(value: T) {
            value.hashCode()// 类型T的值是非空的
        }
    }

    // 对泛型类型做类型转换
    fun printSum(c: Collection<*>) {
        val intLit = c as? List<Int> ?: throw IllegalStateException("List is expected")// 有警告
        println(intLit.sum())
    }
    printSum(listOf(1, 2, 3))

    // 对已知类型实参做类型转换
    fun printSum(c: Collection<Int>) {
        if (c is List<Int>) {
            println(c.sum())// 这次检查是合法的
        }
    }
    printSum(listOf(1, 2, 3))

    // 声明带实化类型参数的函数
    println(isA<String>("abc"))
    println(isA<String>(123))

    // 使用标准库函数filterIsInstance
    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>())// 指定<String>作为函数的类型实参，这种情况，类型实参在运行时是已知的

    listOf<String>().filterIsInstance1<String>()

    class TargetService {

    }

    val serviceImpl = ServiceLoader.load(TargetService::class.java)// 获取java.lang.Class对应的kotlin类
    // 用带实化类型参数的函数重写
    val serviceImple1 = loadService<TargetService>()

    // 定义一个不变形的类似集合的类
    open class Animal {
        fun feed() {
            println("feed")
        }
    }

    class Herd<T : Animal> {// 类型参数没有声明成协变的

        val size: Int get() = 1
        operator fun get(i: Int): T {
            return Animal() as T
        }
    }

    fun feedAll(animals: Herd<Animal>) {
        for (i in 0 until animals.size) {
            animals[i].feed()
        }
    }

    // 使用一个不变形的类似集合的类
    class Cat : Animal() {
        fun clearLitter() {
            println("clearLitter")
        }
    }

    fun takeCareOfCats(cats: Herd<Cat>) {
        for (i in 0 until cats.size) {
            cats[i].clearLitter()
//            feedAll(cats)// 错误，推导的类型是Herd<Cats>，但期望的是Herd<Animal>
        }
    }

    // 使用一个协变的类似集合的类
    class Herd1<out T : Animal> {
        // 类型参数T现在是协变的
        val size: Int get() = 1
        operator fun get(i: Int): T {
            return Animal() as T
        }
    }

    fun feedAll1(animals: Herd1<Animal>) {
        for (i in 0 until animals.size) {
            animals[i].feed()
        }
    }

    fun takeCareOfCats1(cats: Herd1<Cat>) {
        for (i in 0 until cats.size) {
            cats[i].clearLitter()
        }
        feedAll1(cats)
    }

    // 带不变型类型参数的数据拷贝函数
    fun <T> copyDate(source: MutableList<T>, destination: MutableList<T>) {
        for (item in source) {
            destination.add(item)
        }
    }

    // 带不变型类型参数的数据拷贝函数
    fun <T : R, R> copyDate(
        source: MutableList<T>,// 来源的元素应该是目标元素类型的子类型
        destination: MutableList<R>
    ) {
        for (item in source) {
            destination.add(item)
        }
    }

    val ints = mutableListOf(1, 2, 3)
    val anyItems = mutableListOf<Any>()
    copyDate(ints, anyItems)
    println(anyItems)

    // 带out投影类型参数的数据拷贝函数
    fun <T> copyDate1(
        source: MutableList<out T>,// 若没有使用那些T用在in位置的方法，可以给类型的用法加上out，source是一个投影(受限)的MutableList
        destination: MutableList<T>
    ) {
        for (item in source) {
            destination.add(item)
        }
    }

    // 使用in投影类型参数的数据拷贝函数
    fun <T> copyDate2(
        source: MutableList<T>,
        destination: MutableList<in T>// 允许目标元素的类型是来源元素类型的超类型
    ) {
        for (item in source) {
            destination.add(item)
        }
    }

    val list: MutableList<Any?> = mutableListOf('a', 1, "qwe")
    val chars = mutableListOf('a', 'b', 'c')
    val unknownElements: MutableList<*> =// MutableList<*>和MutableList<Any?>不一样
        if (Random().nextBoolean()) list else chars
//    unknownElements.add(42)// 报错
    println(unknownElements.first())// 读取元素是安全的，返回类型为Any?的元素

    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator
//    validators[String::class]!!.validate("")// 存储在map中的值的类型是FieldValidator<*>

    // 使用显示的转换获取验证器
    var stringValidator = validators[String::class] as FieldValidator<String>// 警告：未受检查的转换
    println(stringValidator.validate(""))

    // 错误地获取验证器
    stringValidator = validators[Int::class] as FieldValidator<String>// 得到了一个错误的验证器
    stringValidator.validate("")// 直到使用验证器时才发现真正的错误

    // 封装验证器集合的访问
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)
    println(Validators[String::class].validate("kotlin"))
    println(Validators[Int::class].validate(42))
}

inline fun <reified T> loadService(): ServiceLoader<T>? {// 类型参数标记成reified
    return ServiceLoader.load(T::class.java)// 把T::class当成类型形参的类访问
}

interface Product<out T> {// 类被声明成在T上协变

    fun produce(): T
}

// 输入验证的接口
interface FieldValidator<in T> {// 接口定义成在T上逆变

    fun validate(input: T): Boolean// T只在in位置使用(这个方法只消费T的值)
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String) = input.isNotEmpty()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int) = input >= 0
}

object Validators {
    private val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()// 私有
    fun <T : Any> registerValidator(kClass: KClass<T>, fieldValidator: FieldValidator<T>) {
        validators[kClass] = fieldValidator// 只有正确的kv被写入map，当验证器正好对应到类的时候
    }

    @Suppress("UNCHECKED_CASE")// 禁止关于未受检的转换到FieldValidator<T>的警告
    operator fun <T : Any> get(kClass: KClass<T>): FieldValidator<T> =
        validators[kClass] as? FieldValidator<T>
            ?: throw IllegalArgumentException("No validator for ${kClass.simpleName}")

}
