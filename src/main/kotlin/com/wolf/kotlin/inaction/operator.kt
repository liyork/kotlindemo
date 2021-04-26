package com.wolf.kotlin.inaction

import com.wolf.kotlin.overrideFun
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.lang.IndexOutOfBoundsException
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

// 定义一个plus运算符
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {// 定义一个plus的方法
        return Point(x + other.x, y + other.y)
    }
}

// 把运算符定义为扩展函数
operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}

// 定义一个运算数类型不同的运算符
operator fun Point.times(scale: Double): Point {
    return Point((x * scale).toInt(), (y * scale).toInt())
}

// 定义一个返回结果不同的运算符
operator fun Char.times(count: Int): String {
    return toString().repeat(count)
}

fun main() {
    val p1 = Point(1, 2)
    val p2 = Point(3, 4)
    println(p1 + p2)// 通过+号调用plus方法

    val p = Point(1, 2)
    println(p * 1.5)

    println('a' * 3)

    println(0x0F and 0xF0)
    println(0x0F or 0xF0)
    println(0x1 shl 4)

    // 复合赋值运算符
    var point = Point(1, 2)
    point += Point(3, 4)// plusAssign
    println(point)

    val numbers = arrayListOf<Int>()
    numbers += 42
    println(numbers[0])

    val list = arrayListOf(1, 2)
    list += 3// +=修改list
    val newList = list + listOf(4, 5)// +返回一个包含所有元素的新列表
    println(list)
    println(newList)

    // 一元运算符无参数
    operator fun Point.unaryMinus(): Point {
        return Point(-x, -y)
    }

    val p3 = Point(1, 2)
    println(-p3)

    // 定义一个自增运算符
    operator fun BigDecimal.inc() = this + BigDecimal.ONE
    var bd = BigDecimal.ZERO
    println(bd++)
    println(++bd)

    println(null == 1)// ==可用于可控运算数

    // 实现compareTo方法
    class Person(val firstName: String, val lastName: String) : Comparable<Person> {
        override fun compareTo(other: Person): Int {
            // 按顺序调用给定的方法(先用lastName后用firstName)，用来比较值
            return compareValuesBy(this, other, Person::lastName, Person::firstName)
        }
    }

    val p11 = Person("a", "s")
    val p22 = Person("bb", "j")
    println(p11 < p22)

    // 实现get约定
    operator fun Point.get(index: Int): Int {// 定义get运算符函数
        return when (index) {
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException("invalid coordinate $index")
        }
    }

    val p4 = Point(1, 2)
    println(p4[1])

    // 实现set的约定方法
    data class MutablePoint(var x: Int, var y: Int)

    operator fun MutablePoint.set(index: Int, value: Int) {// 定义set运算符函数
        when (index) {
            0 -> x = value
            1 -> y = value
            else -> throw IndexOutOfBoundsException("invalid coordinate $index")
        }
    }

    val p5 = MutablePoint(1, 2)
    p5[1] = 42
    println(p5)

    (1 until 3).forEach(::print)// [1,3)
    (1..2).forEach(::print)// [1,2]
    println()

    // 实现in的约定
    data class Rectangle(val upperLeft: Point, val lowerRight: Point)

    operator fun Rectangle.contains(p: Point): Boolean {
        return p.x in upperLeft.x until lowerRight.x &&
                p.y in upperLeft.y until lowerRight.y
    }

    val rect = Rectangle(Point(1, 2), Point(5, 5))
    println(Point(2, 3) in rect)// in右边对象调用contains，in左边对象作为参数
    println(Point(5, 5) in rect)

    // 处理日期的区间
    val now = LocalDate.now()
    val vacation = now..now.plusDays(10)// ..被编译器转换为rangeTo
    println(now.plusWeeks(1) in vacation)

    // 实现日期区间的迭代器
    operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
        object : Iterator<LocalDate> {// 实现Iterator，可用于for中

            var current = start
            override fun hasNext() = current <= endInclusive
            override fun next() = current.apply {// 在修改前返回当前日期作为结果
                current = plusDays(1)
            }
        }

    val newYear = LocalDate.ofYearDay(2017, 1)
    val daysOff = newYear.minusDays(1)..newYear
    for (dayOff in daysOff) {// 对应iterator函数
        println(dayOff)
    }

    // 解构声明,主要场景是从一个函数返回多个值
    val p6 = Point(1, 2)
    val (x, y) = p// 声明变量x、y，用p的组件来初始化，调用了componentN函数
    println("$x $y")
    // 数据类，编译器为每个在主构造方法中声明的属性生成一个componentN函数，下面展示手动为非数据类声明
    class Point(val x: Int, val y: Int) {
        operator fun component1() = x
        operator fun component2() = y
    }

    // 使用解构生命来返回多个值
    data class NameComponents(val name: String, val extension: String)

    fun splitFilename(fullName: String): NameComponents {
        val (name, extension) = fullName.split('.', limit = 2)// 使用解构声明处理集合
        return NameComponents(name, extension)
    }
    val (name, ext) = splitFilename("example.kt")// 使用解构声明展开这个类
    println("$name $ext")

    // 用解构声明来遍历map
    fun printEntries(map: Map<String, String>) {
        for ((k, v) in map) {// 在in循环中用解构声明，component1,2
            println("$k -> $v")
        }
    }

    val map = mapOf("Oracle" to "java")
    println(printEntries(map))

    // 使用支持属性来实现惰性初始化
    class Person1(val name: String) {
        private var _emails: List<String>? = null// 保存数据，关联委托
        val emails: List<String>
            get() {
                if (_emails == null) {
                    _emails = listOf("1", "2")
                }
                return _emails!!
            }
    }

    val p7 = Person1("aAA")
    p7.emails
    p7.emails

    // 用委托属性实现惰性初始化
    class Person2(val name: String) {
        val emails by lazy { listOf("1", "2") }// lazy是一个函数返回SynchronizedLazyImpl能配合by
    }

    // 使用PropertyChangeSupport的工具类
    open class PropertyChangeAware {
        protected val changeSupport = PropertyChangeSupport(this)
        fun addPropertyChangeListener(listener: PropertyChangeListener) {
            changeSupport.addPropertyChangeListener(listener)
        }

        fun removePropertyChangeListener(listener: PropertyChangeListener) {
            changeSupport.removePropertyChangeListener(listener)
        }
    }

    // 手工实现属性修改的通知
    class Person3(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
        var age: Int = age
            set(newValue) {
                val oldValue = field // 访问属性背后的支持字段
                field = newValue
                changeSupport.firePropertyChange("age", oldValue, newValue)// 通知
            }
        var salary: Int = salary
            set(newValue) {
                val oldValue = field
                field = newValue
                changeSupport.firePropertyChange("salary", oldValue, newValue)
            }
    }

    val p8 = Person3("Dmitry", 34, 2000)
    p8.addPropertyChangeListener(
        PropertyChangeListener { event ->
            println("Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
        }
    )
    p8.age = 35
    p8.salary = 2222

    // 通过辅助类实现属性变化的通知
    class ObservableProperty(
        val propName: String, var propValue: Int,
        val changeSupport: PropertyChangeSupport
    ) {
        fun getValue(): Int = propValue
        fun setValue(newValue: Int) {
            val oldValue = propValue
            propValue = newValue
            changeSupport.firePropertyChange(propName, oldValue, newValue)
        }
    }

    class Person4(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
        val _age = ObservableProperty("age", age, changeSupport)
        var age: Int
            get() = _age.getValue()
            set(value) {
                _age.setValue(value)
            }
        val _salary = ObservableProperty("salary", salary, changeSupport)
        var salary: Int
            get() = _salary.getValue()
            set(value) {
                _salary.setValue(value)
            }
    }

    // ObservableProperty作为属性委托
    class ObservableProperty1(var propValue: Int, val changeSupport: PropertyChangeSupport) {
        operator fun getValue(p: PropertyChangeAware, prop: KProperty<*>): Int = propValue
        operator fun setValue(p: PropertyChangeAware, prop: KProperty<*>, newValue: Int) {
            val oldValue = propValue
            propValue = newValue
            changeSupport.firePropertyChange(prop.name, oldValue, newValue)
        }
    }

    class Person5(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
        var age: Int by ObservableProperty1(age, changeSupport)
        var salary: Int by ObservableProperty1(salary, changeSupport)
    }

    // 使用Delegates.observable实现属性修改的通知
    class Person6(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
        private val observer = { prop: KProperty<*>, oldValue: Int, newValue: Int ->
            changeSupport.firePropertyChange(prop.name, oldValue, newValue)
        }
        var age: Int by Delegates.observable(age, observer)
        var salary: Int by Delegates.observable(salary, observer)
    }

    val p66 = Person6("a", 1, 1)
    p66.age = 1

    // 定义一个属性，把值存到map
    class Person7 {
        private val _attributes = hashMapOf<String, String>()
        fun setAttribute(attrName: String, value: String) {
            _attributes[attrName] = value
        }

        val name: String
            get() = _attributes["name"]!!
    }

    val p77 = Person7()
    val data = mapOf("name" to "Dmitry", "company" to "jxjj")
    for ((attrName, value) in data) {
        p77.setAttribute(attrName, value)
    }
    println(p77.name)

    // 使用委托属性把值存到map中
    class Person8 {
        private val _attributes = hashMapOf<String, String>()
        fun setAttribute(attrName: String, value: String) {
            _attributes[attrName] = value
        }

        val name: String by _attributes// 把map作为委托属性,Map上已定义了getValue和setValue
        val company: String by _attributes
    }

    val p88 = Person8()
    for ((attrName, value) in data) {
        p88.setAttribute(attrName, value)
    }
    println(p88.name)
    println(p88.company)

}

