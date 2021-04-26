package com.wolf.kotlin.inaction

import com.wolf.kotlin.shizhan.TestCase
import com.wolf.kotlin.shizhan.strlen
import com.wolf.kotlin.sum
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.StringReader
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.NumberFormatException
import java.nio.Buffer

// 重写返回泛型参数的函数时用Unit有用
interface Processor<T> {
    fun process(): T
}

fun main() {
    fun strLen(s: String) = s.length// 不可传递空
    fun strLenSafe(s: String?): Int =// 类型后加?表明可为空
        if (s != null) s.length else 0// 增加null检查后，就可以用s操作了

    val x: String? = null
    println(strLenSafe(x))
    println(strLenSafe("abc"))

    // 安全调用运算符?.，将以此null检查和一次方法调用合并成一个操作
    fun printlnAllCaps(s: String?) {
        val allCaps: String? = s?.toUpperCase()// allCaps可能是空
        println(allCaps)
    }
    println(printlnAllCaps("abc"))
    println(printlnAllCaps(null))

    // 使用安全调用处理可空属性
    class Employee(val name: String, val manager: Employee?)

    fun managerName(employee: Employee): String? = employee.manager?.name

    val ceo = Employee("Da Boss", null)
    val developer = Employee("Bob Smith", ceo)
    println(managerName(developer))
    println(managerName(ceo))

    // 连接多个安全调用
    class Address(
        val streetAddress: String, val zipCode: Int,
        val city: String, val country: String
    )

    class Company(val name: String, val address: Address?)
    class Person(val name: String, val company: Company?)

    fun Person.countryName(): String {
        val country = this.company?.address?.country// 多个安全调用连接在一起
        return if (country != null) country else "Unknown"
    }

    var person = Person("Dmitry", null)
    println(person.countryName())

    // Elvis运算符?:
    fun foo(s: String?) {
        val t: String = s ?: ""// 如果s为null，结果是一个空的字符串
    }

    // 使用Elvis运算符处理null值
    fun strLenSafe1(s: String?): Int = s?.length ?: 0
    println(strLenSafe1("abc"))
    println(strLenSafe1(null))

    fun Person.countryName1() = company?.address?.country ?: "Unknown"

    // return和throw的操作时表达式，可以写在Elvis运算符右边
    fun printShippingLabel(person: Person) {
        val address = person.company?.address
            ?: throw IllegalArgumentException("No address")// 若少address就抛出异常
        with(address) {// address不为空
            println(streetAddress)
            println("$zipCode $city, $country")
        }
    }

    val address = Address("Elsestr", 888, "mu", "ger")
    val jetbrains = Company("jet", address)
    person = Person("D", jetbrains)
    println(printShippingLabel(person))

    // 使用安全转换实现equals
    class Person1(val firstName: String, val lastName: String) {
        override fun equals(other: Any?): Boolean {
            val otherPerson = other as? Person1 ?: return false
            return otherPerson.firstName == firstName && otherPerson.lastName == lastName
        }
    }

    val p1 = Person1("dd", "jj")
    val p2 = Person1("dd", "jj")
    println(p1 == p2)
    println(p1.equals(42))

    // 使用非空断言
    fun ignoreNulls(s: String?) {
        val sNotNull: String = s!!// 若s为空则异常
        println(sNotNull.length)
    }
    // 最好避免在同一行中使用多个！！断言：

    // let所做的是把一个调用它的对象变成lambda表达式的参数
    // 使用let调用一个接受非空参数的函数
    fun sendEmailTo(email: String) {
        println("Sending email to $email")
    }

    var email: String? = "xxx@dd.com"
    email?.let { sendEmailTo(it) }
    email = null
    email?.let { sendEmailTo(it) }

    // 使用延迟初始化属性
    class MyService {
        fun performAction(): String = "foo"
    }

    class MyTest {
        // kotlin通常要求在构造方法中初始化所有属性，若想要延迟初始化则用lateinit，必须是var
        private lateinit var myService: MyService

        @Before
        fun setUp() {
            myService = MyService()
        }

        @Test
        fun testAction() {
            Assert.assertEquals("foo", myService.performAction())
        }
    }

    // 用可空接收者调用扩展函数
    fun verifyUserInput(input: String?) {
        if (input.isNullOrBlank()) {// 这里不需要安全调用,null调用时不会异常
            println("Please fill in the required fields")
        }
    }
    verifyUserInput(" ")
    verifyUserInput(null)

    // kotlin中所有泛型类和泛型函数的类型参数默认都是可空的
    // 处理可空的类型参数
    fun <T> printHashCode(t: T) {
        println(t.hashCode())// t可能为空，不过hashCode中会有判断this?
    }
    printHashCode(null)// T被推导成Any?

    // 要使类型参数非空，必须为它指定一个非空的上界
    fun <T : Any> printHashCode1(t: T) {
        println(t.hashCode())// T不可为空
    }
//    printHashCode1(null)// 无法编译

    // ?:代替默认值
    println(Person("a", 1).age ?: "defaultVal")

    // Katlin 并不区分基本数据类型和包装类型，你使用的永远是 同 一个类型 （ 比如：Int)
    val i: Int = 1
    val list: List<Int> = listOf(1, 2, 3)

    // 使用可空的基本数据类型
    data class Person2(val name: String, val age: Int? = null) {
        fun isOlderThan(other: Person2): Boolean? {
            if (age == null || other.age == null) {
                return null
            }
            return age > other.age
        }
    }
    println(Person2("sum", 35).isOlderThan(Person2("aa", 42)))
    println(Person2("sum", 35).isOlderThan(Person2("ja")))

    val longDigit = 123L// Long类型字面值
    val doubleDigit = 0.12// 用浮点数表示Double
    val floatDigit = 0.12f// Float
    val hexDigit = 0xCAFEBABE
    val binaryDigit = 0b00000010

    val answer: Any = 42// Any是引用类型，42会被装箱

    class NoResultProcessor : Processor<Unit> {
        override fun process() {// 返回Unit，但可以省略
            println(1111)// 不需要显示return，编译器会隐士地加上return
        }
    }

    // Nothing，方法永不可能有正常的返回
    fun fail(message: String): Nothing {// 返回类型没有意义，从来不会成功地结束
        throw IllegalStateException(message)
    }
    //fail("Error occurred")

    // 创建一个包含可空值的集合
    fun readNumbers(reader: BufferedReader): List<Int?> {
        val result = ArrayList<Int?>()// 创建包含可空Int值的列表，可以持有Int或者null
        for (line in reader.lineSequence()) {
            try {
                val number = line.toInt()
                result.add(number)// 添加整数(非空值)
            } catch (e: NumberFormatException) {
                result.add(null)// 添加null
            }
        }
        return result
    }

    val l1: List<Int?>// 列表本身始终不为null，但列表中的元素可以为null
    val l2: List<Int>?// 可能包含空引用，但列表中的元素保证是非空

    // 使用可空值的集合
    fun addValidNumbers(numbers: List<Int?>) {
        var sumOfValidNum = 0
        var invalidNum = 0
        for (number in numbers) {
            if (number != null) {// 检查是否为null
                sumOfValidNum += number
            } else {
                invalidNum++
            }
        }
        println("sum of valid numbers: $sumOfValidNum")
        println("invalid numbers : $invalidNum")
    }

    val reader = BufferedReader(StringReader("1\nabc\n42"))
    val numbers = readNumbers(reader)
    addValidNumbers(numbers)

    // 对包含可空值的集合使用filterNotNull
    fun addValidNum(numbers: List<Int?>) {
        val validNums = numbers.filterNotNull()
        println("sum of valid numbers: ${validNums.sum()}")
        println("invalid numbers : ${numbers.size - validNums.size}")
    }

    // 使用只读集合接口与可变集合接口
    fun <T> copyElements(source: Collection<T>, target: MutableCollection<T>) {
        for (item in source) {
            target.add(item)
        }
    }

    val source: Collection<Int> = arrayListOf(3, 5, 7)
    val target: MutableCollection<Int> = arrayListOf(1)
    copyElements(source, target)
    println(target)

    // 使用数组
    var arr: Array<String> = arrayOf("1", "2")
    for (i in arr.indices) {// 使用扩展属性
        println("argumet $i is: ${arr[i]}")// 通过下标使用array
    }

    // 创建字符数组
    val letters = Array<String>(26) { i -> ('a' + i).toString() }// 从0~25开始，执行lambda
    println(letters.joinToString(""))

    // 想vararg方法传递集合
    val strings = listOf("a", "b", "c")
    val args = strings.toTypedArray()
    println("%s/%s/%s".format(*args))// 使用*展开运算符，将数组Array展开成为vararg

    // 基本类型数组
    val fiveZerso = IntArray(5)
    val fiveZerosToo = intArrayOf(0, 0, 0, 0, 0)
    val squares = IntArray(5) { i -> (i + 1) * (i + 1) }
    println(squares.joinToString())

    // 对数组使用forEachIndexed
    arr.forEachIndexed { index, element ->
        println("argument $index is: $element")
    }
}