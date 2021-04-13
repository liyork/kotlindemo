package com.wolf.kotlin

// Any是Kotlin中所有类的共同基类，相当于Java中的Object，而Any？则表示允许传入空值
// 泛型，即 "参数化类型"，将类型参数化，把参数化类型放在尖括号内，来声明泛型类、泛型接口，泛型方法

class Box<T>(t: T) {
    var value = t
}

fun main() {
//    genericClass()

//    genericFun1()

//    genericFun2()

//    genericFun3()

//    genericErasure()

//    genericLimitUpper()

//    genericDeclarTypeChange()

//    genericUseTypeChange()

//    genericStarTypeChange()

//    genericXiebian()
    genericNibian()

//    genericAsterisk()
}

// Kotlin 中的泛型是不型变的
fun genericDeclarTypeChange() {
    open class A
    class B : A()

    val arr1: Array<B> = arrayOf(B(), B())
    // Array<B>并不是Array<A>的子类,Kotlin 中的泛型是默认不型变的，无法自动完成类型转换
//    val arr2: Array<A> = arr1

    val list1: List<B> = listOf(B(), B())
    // list/set/map可以支持型变
    // 看到List的泛型类型使用了out修饰符。是 Kotlin 中的声明处型变，用来向编译器解释这种情况。
    // 关于out修饰符，
    // 当类、接口的泛型类型参数被声明为out时，则该类型参数是协变的，泛型类型的子类型是被保留的。但它只能出现在函数的输出位置，只能作为返回类型，即生产者。
    // 带来的好处是，A是B的父类，那么List<A>可以是List<B>的父类。
    val list2: List<A> = list1

    // 关于in修饰符，
    // 当类、接口的泛型类型参数被声明为in时，则该类型参数是逆变的，泛型类型的父类型是被保留的，它只能出现在函数的输入位置，作为参数，只能作为消费类型，即消费者。
    var a: Comparable<A> = object : Comparable<A> {
        override fun compareTo(other: A): Int {
            return 1
        }
    }
    var b: Comparable<B> = a
}

// 逆变，像java的super，知道下限
// 如果B是A的子类型，那么 Consumer<A> 就是Consumer<B>的子类型
fun genericNibian() {
// 一个为特定类型的值定义的比较器显然可以用来比较该类型任意子类型的值
    val anyComparator: Comparator<Any> = Comparator<Any> { o1, o2 ->
        o1.hashCode() - o2.hashCode()
    }
    val list: List<String> = listOf()
    // 期望传入一个Comparator<String>，这里传入了一个更一般类型Any
    list.sortedWith(anyComparator)
}

open class AA
class BB : AA()

// 使用处型变
fun genericUseTypeChange() {
    var arr1: Array<BB> = arrayOf(BB(), BB())
    // 需要每次使用对应类时都添加型变修饰符
    var arr2: Array<out AA> = arr1

    val arrBB: Array<BB> = arrayOf(BB(), BB())
    val arrAA: Array<AA> = arrayOf(AA(), AA())
    // 上限是AA，所以第一个参数是BB或AA都行，
    copy(arrBB, arrAA)
    copy(arrAA, arrAA)
    // 下限是AA，所以BB并不行
//    copy(arrAA, arrBB)
}

// 型变修饰符
// Kotlin 中的out A类似于 Java 中的? extends A，即泛型参数类型必须是A或者A的子类，用来确定类型的上限,
//Kotlin 中的in A类似于 Java 中的? super A，即泛型参数类型必须是B或者B的父类，用来确定类型的下限
fun copy(from: Array<out AA>, to: Array<in AA>) {
    for (i in from.indices) {
        to[i] = from[i]
    }
}

// 星号投射(star-projection):
// 当我们不知道泛型参数的类型信息时，但仍需要安全的使用它时，可以使用星投影，和 Java 中的原始类型很像，但星投影是安全
//对于 Foo <out T : TUpper>，其中 T 是一个具有上界 TUpper 的协变类型参数，Foo <> 等价于 Foo <out TUpper>。
// 这意味着当 T 未知时，你可以安全地从 Foo <> 读取 TUpper 的值。
// out表明，类、接口只会作为返回值，而不会作为参数
//对于 Foo <in T>，其中 T 是一个逆变类型参数，Foo <> 等价于 Foo <in Nothing>。
// 这意味着当 T 未知时，没有什么可以以安全的方式写入 Foo <>。
// in表明，类、接口只用于参数，而不会作为返回值
//对于 Foo <T : TUpper>，其中 T 是一个具有上界 TUpper 的不型变类型参数，Foo<*> 对于读取值时等价于 Foo<out TUpper> 而对于写值时等价于 Foo<in Nothing>。

// 如果泛型类型具有多个类型参数，则每个类型参数都可以单独投影。
// 例如，如果类型被声明为 interface Function <in T, out U>，我们可以想象以下星投影：
//Function<*, String> 表示 Function<in Nothing, String>；
//Function<Int, *> 表示 Function<Int, out Any?>；
//Function<*, *> 表示 Function<in Nothing, out Any?>

fun genericStarTypeChange() {
    val array1: Array<BB> = arrayOf(BB(), BB(), BB())
    // 使用星投影，我们可以将array1赋值给array2，
    val array2: Array<*> = array1
    // 但由于此时array2并不知道泛型参数的类型，所以不能对array2进行数据写入的操作，但可以从中读取数据：
//    array2[0] = A() //编译器会报错
    val a = array2[0] // 正常
    // 星投影更适合那些泛型参数的类型不重要的场景。
}

// Kotlin为泛型声明，执行的类型安全检测，仅在编译期进行， 运行时实例不保留关于泛型类型的任何信息
// Array<String>、Array<Int>的实例都会被擦除为Array<*>，这样带来的好处是保存在内存中的类型信息也就减少了。
fun genericErasure() {
    var arr: Any = 1
//    arr is Array<Int>// 在运行时无法检测一个实例是否是带有某个类型参数的泛型类型
    // 检查一个实例是否是数组，星投影*
    arr is Array<*>

    test<String>(1)

    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>())// 指定String作为函数的类型实参
}

// 在函数内需要使用具体的泛型类型时
// 用inline关键字修饰函数，这样编译器会把每一次函数调用都换成函数实际代码实现，同时用reified关键字修饰泛型类型，这样就能保留泛型参数的具体类型了
inline fun <reified T> test(param: Any) {
    if (param is T) {
        println("param type is T")
    }
}

// 声明处的类型变异使用协变注解修饰符：in(消费者)、out(生产者)。
// 使用 out 使得一个类型参数协变，协变类型参数只能用作输出，仅可以作为返回值类型：
class Runoob2<out A>(val a: A) {
    fun foo(): A {
        return a
    }
}

// in 使得一个类型参数逆变，逆变类型参数只能用作输入，可以作为入参的类型
class Runoob3<in A>(a: A) {
    fun foo(a: A) {

    }
}

fun genericXiebian() {
    var strCo: Runoob2<String> = Runoob2("a")
    var anyCo: Runoob2<Any> = Runoob2<Any>("b")
    anyCo = strCo
    println(anyCo.foo())

    var strDCo = Runoob3("a")
    var anyDCo = Runoob3<Any>("b")
    strDCo = anyDCo

    var cats: Herd<Cat> = Herd()
    // 不用类型转换，通过协变保留了Animal和Cat的父子关系
    feedAll(cats)
}

open class Animal {
    fun fead() {
        println("weiyagn Animal")
    }
}

class Cat : Animal() {
    fun cleanLitter() {

    }
}

// 声明类在某个类型参数上是可以协变的，在该类型参数的名称前加out
// 类型参数T上的关键字 out 有两层含义：
//   子类型化被保留(Producer<Cat> 是 Producer<Animal> 的子类型)
//   T 只能用在out的位置
// 协变，像java的extends，知道上限是什么，List<Cat>可以放入List<Animal>
class Herd<out T : Animal> {
    val list = listOf<T>()
    val size: Int
        get() = list.size

    operator fun get(i: Int): T {
        return list[i]
    }
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].fead()
    }
}


fun genericLimitUpper() {
    sort(listOf<Int>(1, 2, 3))
    // 错误,需要一个Comparable的子类
//    sort(listOf(HashMap<Int, String>()))

    val listOf = listOf<X>(X(1), X(2))
    copyWhenGreater(listOf, X(1))
}

class X(override val length: Int) : CharSequence, Comparable<X> {
    override fun get(index: Int): Char {
        TODO("Not yet implemented")
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        TODO("Not yet implemented")
    }

    override fun compareTo(other: X): Int {
        TODO("Not yet implemented")
    }
}

// kotlin使用冒号(:)指明类型上界，默认上界是Any?，像java的extends，实参需要是Comparable的及其子类
fun <T : Comparable<T>> sort(list: List<T>) {

}

// 多个上界约束条件，用 where 子句：
fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
        where T : CharSequence, T : Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}

fun genericFun2() {
    val age = 23
    val name = "runoob"
    val boo = true

    doPrintln(age)
    // 调用泛型函数时，若可以推断出类型参数，可以省略泛型参数
    doPrintln<Int>(age)
    doPrintln(name)
    doPrintln(boo)
}

fun genericFun3() {
    val list = listOf("a", "b")
    val result = StringBuilder().myApply {
        for (fruit in list) {
            append(fruit).append(" ")
        }
    }
    println("result is $result")
}

// todo 这里的T.()是什么意思？换成()也行啊。
fun <T> T.myApply(block: T.() -> Unit): T {
    block()
    return this
}


fun <T> doPrintln(content: T) {
    when (content) {
        is Int -> println("整形：$content")
        is String -> println("字符：${content.toUpperCase()}")
        else -> println("其他类型")
    }
}

fun genericFun1() {
    boxInt<Int>(1)
    boxInt<String>("1")
    boxInt(1)
}

private fun genericClass() {
    val box: Box<Int> = Box<Int>(1)
    // 编译器进行类型推断
    val box2 = Box(2)

    var boxInt = Box<Int>(10)
    var boxString = Box<String>("Runoob")
    println(boxInt.value)
    println(boxString.value)
}

// 泛型函数，类型参数要放在函数名的前面
fun <T> boxInt(value: T) = Box(value)
