package com.wolf.kotlin.inaction

import com.wolf.kotlin.inaction.Color.*
import com.wolf.kotlin.inaction.packagetest.createRandomRectangle
import sun.security.util.Length
import java.io.BufferedReader
import java.io.StringReader
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.util.*

// 函数声明，返回类型
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b// if是有结果值的表达式
}

// 表达式作为函数体
fun max1(a: Int, b: Int): Int = if (a > b) a else b

// 变量
val question = "The xx"// 不可变引用，对应final
val answer = 42
var answer2: Int = 42// 可变引用


private fun baseHello(args: Array<String>) {
    // 字符串模板
    val name = if (args.size > 0) args[0] else "kotlin"
    println("hello, $name, ${if (args.size > 0) args[0] else "someone"}!")// 引用变量
}

// simple class
// public是默认的
class Person1(
    val name: String,// 只读属性，生成一个字段和简单的getter
    var isMarried: Boolean// 可写属性，一个字段、一个getter和一个setter,若属性以is开头，getter不会增加前缀，setter会用set
)

private fun testUseClass() {
    val person = Person1("bob", true)// 直接构造不用new关键字
    println(person.name)// 看似直接访问属性，但底层调用的是getter
    println(person.isMarried)
}

class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() {// 重写属性的getter
            return height == width
        }
}

private fun testProperty() {
    val rectangle = Rectangle(41, 43)
    println(rectangle.isSquare)
}

private fun testPackage() {
    println(createRandomRectangle().isSquare)//使用导入的函数
}

// enum,带属性的枚举类
enum class Color(val r: Int, val g: Int, val b: Int) {    // 声明枚举常量的属性
    //在每个常量创建的时候指定属性值
    RED(255, 0, 0), ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 255, 255),
    INDIGO(75, 0, 130), VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b// 定义方法
}


private fun testEnum() {
    println(BLUE.rgb())
}

// when是一个有返回值的表达式,使用when选择正确的枚举值
fun getMnemonic(color: Color) =// 直接返回一个when表达式
    when (color) {// 自动有break
        RED, VIOLET -> "Richard"// 多个选项
        ORANGE -> "Of"
        YELLOW -> "York"
        GREEN -> "Gave"
        else -> "xxx"
    }

// when允许使用任何对象
fun mix(c1: Color, c2: Color) =
    when (setOf(c1, c2)) {// 参数可以是任何对象
        setOf(RED, YELLOW) -> ORANGE//顺序不重要
        setOf(YELLOW, BLUE) -> GREEN
        // 这里set每次比对都要创建set效率是问题，下面是优化，避免创建额外的垃圾对象，可读性变差，但是为了达到更好的性能而必须付出的代价
        setOf(BLUE, VIOLET) -> INDIGO
        else -> "xxx"
    }

// 不带参数的when
fun mixOptimized(c1: Color, c2: Color) =//不会创建额外的对象，代价是更难理解
    when {// 没给when表达式提供参数，则分支条件可以是任意布尔表达式
        (c1 == RED && c2 == YELLOW) || (c1 == YELLOW && c2 == RED) -> ORANGE
        (c1 == YELLOW && c2 == BLUE) || (c1 == BLUE && c2 == YELLOW) -> GREEN
        else -> throw Exception("Dirty color")
    }

private fun testWhen() {
    println(getMnemonic(BLUE))
    println(mixOptimized(BLUE, YELLOW))
}

// 表达式类层次结构,(1+2)+4
interface Expr
class Num(val value: Int) : Expr// 简单的值对象类，只有一个属性，实现了Expr接口
class Sum(val left: Expr, val right: Expr) : Expr

// 用if层叠对表达式求值
fun eval(e: Expr): Int {
    if (e is Num) {
        val n = e as Num// 显示地转换成类型Num，多余的
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left)// 变量e被智能地转换了目标类型(编译器做的),参见testForceCast
    }
    throw IllegalArgumentException("Unknown expression")
}

// 使用有返回值的if表达式
fun eval1(e: Expr): Int =
    if (e is Num) {
        e.value// 代码块最后一个表达式作为返回值
    } else if (e is Sum) {
        eval1(e.right) + eval1(e.left)
    } else {
        throw IllegalArgumentException("Unknown expression")
    }

// 使用when代替if层叠，一般替换多个连续的if表达式
fun eval2(e: Expr): Int =
    when (e) {
        is Num -> e.value// 检查实参类型的分支,应用了智能转换
        is Sum -> eval(e.right) + eval(e.left)
        else -> throw IllegalArgumentException("Unknown expression")
    }

// 智能转换:合并类型检查和转换
private fun testSmartCast() {
    //          Sum
    //     Sum      Num(4)
    //Num(1) Num(2)
    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(eval1(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalWithLogging(Sum(Sum(Num(1), Num(2)), Num(4))))
}

// 使用分支中含有混合操作的when
fun evalWithLogging(e: Expr): Int =
    when (e) {
        is Num -> {
            println("num: ${e.value}")
            e.value// 代码块中最后表达式作为返回值
        }
        is Sum -> {
            val left = evalWithLogging(e.left)
            val right = evalWithLogging(e.right)
            println("sum: $left+$right")
            left + right
        }
        else -> throw IllegalArgumentException("Unknown expression")
    }

// 使用when实现Fizz-Buzz游戏
fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}

private fun testRange() {
    for (i in 1..15) {
        print(fizzBuzz(i))
    }
    // 迭代带步长的区间
    for (i in 15 downTo 1 step 2) {
        println(fizzBuzz(i))
    }
}

private fun testIterator() {
    // 初始化并迭代map
    val binaryReps = TreeMap<Char, String>()
    for (c in 'A'..'F') {// 字符区间
        val binary = Integer.toBinaryString(c.toInt())// 将ASCII码转换成二进制
        binaryReps[c] = binary
    }
    for ((letter, binary) in binaryReps) {// 迭代map
        println("$letter = $binary")
    }
    val list = arrayListOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {// 迭代集合时使用下标
        println("$index: $element")
    }
}

// 使用in检查区间的成员
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigith(c: Char) = c !in '0'..'9'// 底层变换成0<=c&&c<=9

// 用in检查作为when分支
fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter"// 组合多个区间
    else -> "I don't know.."
}

private fun testIn() {
    println(isLetter('q'))
    println(isNotDigith('x'))
    println(recognize('8'))
    println("Kotlin" in "Java".."Scala")// "Java"<="Kotlin"&&"Kotlin"<="Scala",字符串按照字母表顺序进行比较
    println("Kotlin" in setOf("Java", "Scala"))// in 用于集合
}

// 像java一样用try
fun readNumber(reader: BufferedReader): Int? {// 不必显示地指定抛出异常
    try {
        val line = reader.readLine()
        return Integer.parseInt(line)
    } catch (e: NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
}


private fun testExceptionExpr() {
    val number: Any = 1
    val percentage =
        if (number in 0..100)
            number
        else// throw是一个表达式，有返回值
            throw IllegalArgumentException("A percentage value must be between 0 and 100: $number")

    var reader = BufferedReader(StringReader("239"))
    println(readNumber(reader))

    // 把try当做表达式使用
    fun readNumber(reader: BufferedReader) {
        val number = try {
            Integer.parseInt(reader.readLine())// try表达式的值
        } catch (e: NumberFormatException) {
            null
        }
        println(number)
    }

    reader = BufferedReader(StringReader("not a number"))
    readNumber(reader)
}


// 声明一个扩展属性
val String.lastChar: Char
    get() = get(length - 1)// 必须定义getter函数，因为没有支持字段，也没有地方存储值

// 声明一个可变的扩展属性
var StringBuilder.lastChar: Char
    get() = get(length - 1)// getter属性
    set(value: Char) {
        this.setCharAt(length - 1, value)// setter属性
    }


private fun testExt() {
    val list = listOf(1, 2, 3)
    println(list.joinToString(separator = "; ", prefix = "(", postfix = ")"))

    // 扩展函数是静态函数的一个高效的语法糖，可用更具体的类型作为接收者类型
    fun Collection<String>.join(separator: String = ", ", prefix: String = "", postfix: String = "") =
        joinToString(separator, prefix, postfix)
    println(listOf("one", "two").join(" "))
    // 只能用string，不能用int
//    println(listOf(1, 2).join(" "))

    // 扩展函数不能重写
    open class View {
        open fun click() = println("View clicked")
    }

    class Button : View() {
        override fun click() = println("Button clicked")
    }

    val view: View = Button()
    view.click()

    fun View.showOff() = println("I'm a view")
    fun Button.showOff() = println("I'm a button")
    view.showOff()// 扩展函数被静态地解析
    (view as Button).showOff()

    println("Kotlin".lastChar)
    val sb = StringBuilder("Kotlin")
    sb.lastChar = '!'
    println(sb)
}


private fun testMidInvoke() {
    val arr = Array(2) { i -> i.toString() }
    arr[0] = "1"
    arr[1] = "2"
    val list = listOf("args: ", *arr)// 展开运算符展开数组内容
    println(list)

    val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")// to是中缀调用，函数名称直接放在目标对象和参数之间
    1.to("one")// 一般to函数的调用
    1 to "one"// 使用中缀符号调用to函数
    // 中缀调用可以与只有一个参数的函数一起使用，使用infix修饰符
    // 解构声明
    val (nuber, name) = 1 to "one"
}


private fun testReg() {
    println("12.345-6.A".split("\\.|-".toRegex()))// 显示创建一个正则表达式
    println("12.345-6.A".split(".", "-"))// 指定多个分隔符

    // 使用String的扩展函数来解析文件路径
    fun parsePath(path: String) {
        val directory = path.substringBeforeLast("/")
        val fullName = path.substringAfterLast("/")

        val fileName = fullName.substringBeforeLast(".")
        val extension = fullName.substringAfterLast(".")
        println("Dir: $directory, name: $fileName, ext:$extension")
    }
    parsePath("/xxsdfsdf/sdfsd/fsdf/chapter.adoc")

    // 使用正则表达式解析文件路径
    fun parsePathWithReg(path: String) {
        val regex = """(.+)/(.+)\.(.+)""".toRegex()// 三重引号的字符串，不需要对任何字符进行转义。
        val matchResult = regex.matchEntire(path)
        if (matchResult != null) {
            val (directory, filename, extension) = matchResult.destructured
            println("Dir: $directory, name: $filename, ext:$extension")
        }
    }
}

// 带重复代码的函数
class User(val id: Int, val name: String, val address: String)

fun saveUser(user: User) {
    // 重复的字段检查
    if (user.name.isEmpty()) {
        throw IllegalArgumentException("Can't save user ${user.id}: empty Name")
    }
    if (user.address.isEmpty()) {
        throw IllegalArgumentException("Can't save user ${user.id}: empty Address")
    }
}

// 提取局部函数来避免重复
fun saveUserWithLocalFun(user: User) {
    fun validate(user: User, value: String, fieldName: String) {// 声明局部函数来验证所有字段
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${user.id}: empty $fieldName")
        }
    }
    validate(user, user.name, "Name")
    validate(user, user.address, "Address")
}

// 在局部函数访问外层函数的参数
fun saveUserWithLocalFunAccessOutParam(user: User) {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${user.id}: empty $fieldName")// 直接访问外部函数的参数
        }
    }
    validate(user.name, "Name")
    validate(user.address, "Address")
}

// 提取逻辑到扩展函数
fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${id}: empty $fieldName")
        }
    }
    validate(name, "Name")
    validate(address, "Address")
}

fun saveUserWithExt(user: User) {
    // 这一段代码和其他用到 User 的地方没有关系。如果你能遵循，类的 API 只能包含必需的方法，那么就可以让类保持精炼的同时，也让你的思路更加清晰
    user.validateBeforeSave()
}

fun testLocalFun() {
    // 参考上面saveUser中的逻辑过程
}

// hello, world in kotlin
fun main(args: Array<String>) {// fun声明函数,参数类型在名称后面，函数可以放在文件中的最外层不需要在类中，
//    baseHello(args)

//    testUseClass()

//    testProperty()

//    testPackage()

//    testEnum()

//    testWhen()

//    testSmartCast()

//    testRange()

//    testIterator()

//    testIn()

//    testExceptionExpr()

//    testExt()

//    testMidInvoke()

//    testReg()

    testLocalFun()
}





