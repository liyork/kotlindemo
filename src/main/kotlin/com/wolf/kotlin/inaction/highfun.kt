package com.wolf.kotlin.inaction

import java.io.BufferedReader
import java.io.FileReader
import java.lang.StringBuilder
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

enum class Delivery { STANDARD, EXPEDITED }
enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

// 定义一个内联函数
inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action()
    } finally {
        lock.unlock()
    }
}

fun main() {
    // 编译器推导出两个变量具有函数类型
    val sum = { x: Int, y: Int -> x + y }
    val action = { println(42) }
    // 显示类型
    val sum1: (Int, Int) -> Int = { x, y -> x + y }// 有两个Int型参数和Int型返回值的函数
    val action1: () -> Unit = { println(42) }// 没有参数和返回值的函数

    var canReturnNull: (Int, Int) -> Int? = { _, _ -> null }// 返回值为可空类型
    var funOrNull: ((Int, Int) -> Int)? = null// 定义一个函数类型的可空变量

    // 定义一个简单的高阶函数
    fun twoAndThree(operation: (Int, Int) -> Int) {// 参数是函数类型
        val result = operation.invoke(2, 3)
        println("the result is $result")
    }
    twoAndThree { a, b -> a + b }
    twoAndThree { a, b -> a * b }

    // 实现简单版本的filter函数
    fun String.filter(predicate: (Char) -> Boolean): String {
        val sb = StringBuilder()
        for (index in 0 until length) {// [0,length-1]
            val element = get(index)
            if (predicate(element)) sb.append(element)// 调用参数predicate函数
        }
        return sb.toString()
    }
    println("ab1c".filter { it in 'a'..'z' })

    // 给函数类型的参数，指定默认值
    fun <T> Collection<T>.joinToString(
        separator: String = ", ", prefix: String = "", postfix: String = "",
        transform: (T) -> String = { it.toString() }// 以lambda为默认值的函数类型的参数
    ): String {
        val result = StringBuilder(prefix)
        for ((index, element) in this.withIndex()) {
            if (index > 0) result.append(separator)
            result.append(transform(element))
        }
        result.append(postfix)
        return result.toString()
    }

    val letters = listOf("Alpha", "Beta")
    println(letters.joinToString())
    println(letters.joinToString { it.toLowerCase() })

    // 使用函数类型的可空参数
    fun <T> Collection<T>.joinToString(
        separator: String = ", ", prefix: String = "", postfix: String = "",
        transform: ((T) -> String)? = null// 声明了一个，函数类型的可空的参数
    ): String {
        val result = StringBuilder(prefix)
        for ((index, element) in this.withIndex()) {
            if (index > 0) result.append(separator)
            result.append(
                transform?.invoke(element) // 使用安全调用语法调用函数
                    ?: element.toString()// 使用Elvis运算符处理transform没有被指定的情况
            )
        }
        result.append(postfix)
        return result.toString()
    }

    // 定义一个返回函数的函数

    class Order(val itemCount: Int)

    fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {// 声明一个返回函数的函数
        if (delivery == Delivery.EXPEDITED) {
            return { order -> 6 + 2.1 * order.itemCount }// 返回lambda
        }
        return { order -> 1.2 * order.itemCount }
    }

    val calculator = getShippingCostCalculator(Delivery.EXPEDITED)// 将返回的函数保存在变量中
    println("shipping costs ${calculator(Order(3))}")// 调用返回的函数

    // 在UI代码中定义已给返回函数的函数
    // 让展示联系人列表的逻辑代码 和 输入过滤条件的UI代码解棋，可以定义一个函数来创建一个判断式，用它来过滤联系人列表
    data class Person(val firstName: String, val lastName: String, val phoneNumber: String?)
    class ContactListFilters {
        var prefix: String = ""
        var onlyWithPhoneNumber: Boolean = false
        fun getPredicate(): (Person) -> Boolean {// 声明一个返回函数的函数
            val startsWithPrefix = { p: Person ->
                p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
            }
            if (!onlyWithPhoneNumber) {
                return startsWithPrefix// 返回一个函数类型的变量
            }
            return { startsWithPrefix(it) && it.phoneNumber != null }// 返回lambda
        }
    }

    val contacts = listOf(
        Person("Dmitry", "Jemerov", "123-4567"),
        Person("Svetlan", "Isakova", null)
    )
    val contactListFilters = ContactListFilters()
    with(contactListFilters) {
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    println(
        contacts.filter(
            contactListFilters.getPredicate()// 将getPredicate返回的函数，作为参数
        )
    )

    // 通过lambda去除重复代码
    data class SiteVisit(val path: String, val duration: Double, val os: OS)

    val log = listOf(
        SiteVisit("/", 34.0, OS.WINDOWS),
        SiteVisit("/", 22.0, OS.MAC),
        SiteVisit("/login", 12.0, OS.WINDOWS),
        SiteVisit("/signup", 8.0, OS.IOS),
        SiteVisit("/", 16.3, OS.ANDROID)
    )
    // 用硬编码的过滤器分析站点访问数据
    val averageWindowsDuration = log
        .filter { it.os == OS.WINDOWS }
        .map(SiteVisit::duration)
        .average()
    println(averageWindowsDuration)
    // 将重复代码抽取到函数中
    fun List<SiteVisit>.averageDurationFor(os: OS) =
        filter { it.os == os }.map(SiteVisit::duration).average()
    println(log.averageDurationFor(OS.WINDOWS))
    println(log.averageDurationFor(OS.MAC))
    // 用一个复杂的硬编码函数分析站点访问数据
    val averageMobilDuration = log
        .filter { it.os in setOf(OS.IOS, OS.ANDROID) }
        .map(SiteVisit::duration)
        .average()
    println(averageMobilDuration)
    // 用一个高阶函数去除重复代码
    fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
        filter(predicate).map(SiteVisit::duration).average()
    println(log.averageDurationFor {
        it.os in setOf(OS.ANDROID, OS.IOS)
    })
    println(log.averageDurationFor {
        it.os == OS.IOS && it.path == "/signup"
    })

    val l = ReentrantLock()
    synchronized(l) {// 编译后被内联到这里，内联方法被调用时，最好直接用lambda，不然可能内联函数被内联但是内联函数中调用的代码可能不被内联
        println(11)
    }

    val l1: Lock = ReentrantLock()
    l1.withLock {// 加锁情况下执行指定操作
        println(111)
    }

    // 使用use函数作资源管理
    fun readFirstLineFromFile(path: String): String {
        BufferedReader(FileReader(path)).use { br ->// use调用lambda并确保资源被正确关闭，不论lambda正常与否
            return br.readLine()
        }
    }

    // 在一个普通循环中使用return
    data class Person1(val name: String, val age: Int)

    val people = listOf(Person1("Alice", 29), Person1("Bob", 31))
    fun lookForAlice(people: List<Person1>) {
        for (person in people) {
            if (person.name == "Alice") {
                println("Found!")
                return
            }
        }
        println("Alice is not found")// 若上面找到则此行不被执行
    }
    lookForAlice(people)

    // 在传递给forEach的lambda中使用return
    fun lookForAlice1(people: List<Person1>) {
        people.forEach {
            if (it.name == "Alice") {
                println("Found!")
                return// 和lookForAlice一样返回,从调用 lambda 的函数中返回，并不只是从 lambda 中返回，叫作非局部返回，它从一个比包含 return 的代码块更大的代码块中返回了 。
                // 只有在以 lambda 作为参数的函数是内联函数的时候才能从更外层的函数返回
            }
        }
        println("Alice is not found")// 若上面找到则此行不被执行
    }
    lookForAlice1(people)

    // 用一个标签实现局部返回
    fun lookForAlice2(people: List<Person1>) {
        people.forEach label@{// 给lambda表达式加上标签
            if (it.name == "Alice") return@label// 返回到标签处
        }
        println("Alice might be somewhere")// 此行总是打印
    }
    lookForAlice2(people)

    // 用函数名作为return标签
    fun lookForAlice3(people: List<Person1>) {
        people.forEach {
            if (it.name == "Alice") return@forEach// 从lambda表达式返回
        }
        println("Alice might be somewhere")
    }
    lookForAlice3(people)

    // 在匿名函数中使用return
    fun lookForAlice4(people: List<Person1>) {
        people.forEach(fun(person) {// 使用匿名函数取代lambda表达式
            if (person.name == "Alice") return// 从最近的函数(匿名函数)返回
            println("${person.name} is not Alice")
        })
    }
    lookForAlice4(people)

    // filter中使用匿名函数
    people.filter(fun(person): Boolean {
        return person.age < 30
    })

    // 使用表达式体匿名函数
    people.filter(fun(person) = person.age < 30)

    // 返回规则：return从最近的使用fun关键字声明的函数返回


}