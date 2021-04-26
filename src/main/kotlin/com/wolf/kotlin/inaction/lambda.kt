package com.wolf.kotlin.inaction

import io.vertx.kotlin.sqlclient.poolOptionsOf
import java.awt.Window
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.File

data class Person6(var name: String, var age: Int)

// 在lambda中使用包含其的函数的参数
fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {// 接收lambda作为实参
        println("$prefix $it")// lambda中访问prefix参数
    }
}

// 在lambda中改变局部变量
fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++// lambda中修改外部变量，变量被lambda捕捉，变量可以被存储并稍后再使用，对于val是拷贝值，对于var拷贝引用
        } else if (it.startsWith("5")) {
            serverErrors++
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}

fun salute() = println("Salute!")

fun sendEmail(person: Person6, message: String) {
}

class Book(val title: String, val authors: List<String>)

fun File.isInsideHiddenDirectory() =
    generateSequence(this) { it.parentFile }.any { it.isHidden }

fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") }
}

fun main() {

    // 用lambda替代匿名内部类，替换只有一个方法的匿名对象
    Thread({})

    // 用lambada在集合中搜索
    val people = listOf(Person6("a", 1), Person6("b", 2))
    println(people.maxBy { it.age })//{}中就是lambda，接收一个元素作为实参(用it)并返回用来比较的值
    // 用成员引用搜索
    println(people.maxBy(Person6::age)) //若lambda刚好是函数或者属性的委托，可以用成员引用替换

    // 把lambda表达式存储在变量中，把变量当做普通函数对待
    val sum = { x: Int, y: Int -> x + y }
    println(sum(1, 2))// 调用保存在变量中的lambda

    run { println(42) }// 运行lambda中的代码

    // lambda演进简明
    people.maxBy({ p: Person6 -> p.age })// 完全声明，过多的标点符号破坏可读性，类型可以从上下文推断
    people.maxBy() { p: Person6 -> p.age }// kotlin约定：若lambda表达式时函数调用的最后一个实参，则可以放到括号的外面
    people.maxBy { p: Person6 -> p.age }// 当lambda是函数唯一的实参时，可以去掉调用代码中的空括号

    // 把lambda作为命名实参传递
    val names = people.joinToString(separator = "", transform = { p: Person6 -> p.name })
    println(names)
    people.joinToString("") { p: Person6 -> p.name }// 把lambda放在括号外传递
    // 省略lambda参数类型
    people.maxBy { p: Person6 -> p.age }// 显示地写出参数类型
    people.maxBy { p -> p.age }// 推导出参数类型
    people.maxBy { it.age }// 使用默认参数名称,只有一个参数且类型可以推导出来则生成it

    val getAge = { p: Person6 -> p.age }// 没有可以推断出参数类型的上下文，需要显示地指定参数类型
    people.maxBy(getAge)

    val errors = listOf("403 Forbidden", "404 Not Found")
    printMessageWithPrefix(errors, "Error:")

    val responses = listOf("200 OK", "418 I'm a teapot", "500 Internal Server Error")
    printProblemCounts(responses)

    val getAge1 = Person6::age// 将函数转换成一个值，此表达式称为成员引用
    val getAge2 = { person: Person -> person.age }// lambda表达式，与上面相同
    run(::salute)// 引用顶层函数

    val action = { person: Person6, message: String -> sendEmail(person, message) }// lambda内委托给sendEmail函数
    val action1 = ::sendEmail// 用成员引用代替

    val createPerson = ::Person// 构造方法引用，创建Person实例的动作被保存成了值
    val p = createPerson("Alice", 29)
    println(p)

    val list = listOf(1, 2, 3, 4)
    println(list.filter { it % 2 == 0 })
    val people1 = listOf(Person6("Alice", 29), Person6("Bob", 31))
    println(people1.filter { it.age > 30 })
    println(list.map { it * it })
    println(people.map { it.name })
    println(people.map(Person6::name))// 成员引用
    people.filter { it.age > 30 }.map(Person6::name)

    val maxAge = people.maxBy(Person6::age)?.age
    people.filter { it.age == maxAge }

    val numbers = mapOf(0 to "zero", 1 to "one")
    println(numbers.mapValues { it.value.toUpperCase() })

    val canBeInClub27 = { p: Person6 -> p.age <= 27 }
    println(people.all(canBeInClub27))
    println(people.any(canBeInClub27))
    println(people.count(canBeInClub27))
    println(people.find(canBeInClub27))

    println(people.groupBy { it.age })
    val list1 = listOf("a", "ab", "b")
    println(list1.groupBy(String::first))

    val books = listOf(Book("a", listOf("1", "2")), Book("b", listOf("3", "4")))
    println(books.flatMap { it.authors }.toSet())// 所有作者的set

    // flatMap做：map+合并
    val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() })

    val listOfLists = listOf(listOf("a"), listOf("b"))
    println(listOfLists.flatten())// 平铺集合

    // map等函数会及早地创建中间集合，即每一步的中间结果都被存储在一个临时列表
    people.map(Person6::name).filter { it.startsWith("A") }// filter和map都会返回一个列表，这里会创建两个列表
    // 为了提高效率，可以将操作变成序列，没有创建任何中间集合。惰性集合操作的入口是Sequence接口，序列中的元素求值是惰性的。
    people.asSequence()// 集合转换成序列
        .map(Person6::name).filter { it.startsWith("A") }
        .toList()// 转换回列表

    // 序列操作分：中间的和末端的，中间操作返回另一个序列(是惰性的)，末端操作返回一个结果
    // 下面是中间操作，惰性的，不会输出内容，map和filter变换被延期了
    val filter = listOf(1, 2, 3, 4).asSequence()
        .map { print("Map($it) ");it * it }
        .filter { print("filter($it) ");it % 2 == 0 }
    filter.toList()// 这里执行末端操作才会有内容输出，才会触发执行所有延期计算

    // 对于序列，所有操作是按顺序应用在每个元素上:处理完第一个元素(map+filter)，再处理第二个元素
    // 先应用filter有助于减少变换的总次数

    // 生成并使用自然数序列
    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    println(numbersTo100.sum())// 这里调用sum时，所有被延迟的操作都被执行

    // 创建并使用父目录的序列
    val file = File("/xx/xxx/xx")
    println(file.isInsideHiddenDirectory())

    // java中函数式接口，接口中只有一个抽象方法,SAM(单抽象方法)接口。kotlin允许在调用接收函数式接口作为参数的方法时使用lambda
    // 编译器自动把其转换成一个Runnable的实例
    Thread(object : Runnable {// 把对象表达式作为函数式接口的实现传递，每次调用都会创建一个新的实例。

        override fun run() {
            println(1111)
        }
    })
    Thread({ println(111) })// lambda作为函数传入，若内部没有访问外部变量，则相应的匿名类实例可以重用
    // 若把lambda传给标记成inline的kotlin函数是不会创建任何匿名类的

    // SAM构造方法是编译器生成的函数，让你执行从lambda到函数式接口实例的显示转换
    // 使用SAM构造方法来返回值
    createAllDoneRunnable().run()

    // 带接收者的lambda,在lambda函数体内可以调用一个不同对象(调用lambda的那个对象)的方法。
    // 使用with,把它的第一个参数转换成，作为第二个参数lambda的接收者，返回值是执行lambda的结果
    fun alphabet(s: StringBuilder) = with(s) {// 指定接收者的值，然后lambda中可以调用它的方法
        for (letter in 'A'..'Z') {
            this.append(letter)// 显示的this调用接收者值的方法
        }
        append("\nNow I know the alphabet")// 省略掉this
        this.toString()// 从lambda返回值
    }
    alphabet(StringBuilder())

    // apply和with一样，不过返回的是参数对象，apply的接收者变成了作为实参的lambda的接收者
    fun alphabet1(s: StringBuilder) = s.apply {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet")
    }.toString()

    // 使用apply初始化一个TextView
    fun createVewWithCustomAttr() {
        Person6("a", 1).apply {
            name = "xx"
            age = 11
        }.age
    }

    // 使用buildString创建字母表
    fun alphabet() = buildString {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet")
    }
}


