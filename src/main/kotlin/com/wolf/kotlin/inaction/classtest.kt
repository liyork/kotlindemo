package com.wolf.kotlin.inaction

import com.sun.javafx.scene.control.skin.ButtonSkin
import io.vertx.core.cli.CLI
import java.awt.Window
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File
import java.io.Serializable
import javax.naming.Context
import javax.swing.text.AttributeSet

// 声明一个简单的接口,成员始终是open
interface Clickable {
    fun click()

    // 带默认实现的方法
    fun showOff() = println("I'm clickable!")
}

// 两一个实现了同样方法的接口
interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}

// 实现一个简单接口
class Button : Clickable, Focusable {
    override fun click() = println("I wa clicked")
    override fun showOff() {// 若同样的继承成员有不止一个实现，则必须提供一个显示实现
        super<Clickable>.showOff()// 要调用Clickable的showOff方法
        super<Focusable>.showOff()
    }
}

// 声明一个带open方法的open类
// 类是open可以被继承
open class RichButton : Clickable {

    fun disable() {// 函数默认final
    }

    open fun animate() {// 子类可重写
    }

    override fun click() {// 重写后同样是open，除非标注final

    }
}

// 声明一个抽象类,不能实例化
abstract class Animated {
    abstract fun animate()// 抽象方法，必须让子类实现，默认open
    open fun stopAnimating() {}// 非抽象函数，默认不是open，可以标注
    fun animateTwice() {

    }
}


private fun testImpleInterface() {
    val button = Button()
    button.click()
    button.showOff()
    button.setFocus(true)// 自动继承
}

// 内部类和sealed类
// 声明一个包含可序列化状态的视图
interface State : Serializable
interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

class Button1 : View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) {

    }

    class ButtonState : State {}// 与java的静态嵌套类类似，没有外部类的引用
}

class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer// 有inner的类可以有外部类的引用
    }
}

// 作为密封类的表达式，将基类标记为密封类
sealed class Expr1 {
    // 将所有可能的类作为嵌套类列出
    class Num(val value: Int) : Expr1()
    class Sum(val left: Expr1, val right: Expr1) : Expr1()
}

fun eval(e: Expr1): Int = when (e) {// when表达式涵盖了所有可能的情况，不再需要else分支
    is Expr1.Num -> e.value
    is Expr1.Sum -> eval(e.right) + eval(e.left)
}

// 讲解主构造函数
open class User1(val nickname: String, val isSubscribed: Boolean = true)// 主构造方法，表明构造方法的参数，val定义使用这些参数初始化的属性

// 主构造方法需要初始化父类
class TwitterUser(nickname: String) : User1(nickname)

open class Button2// 将会生成一个不带任何参数的默认构造方法
class RadioButton : Button2()// 显示地调用父类的构造方法

// 没有主构造方法，有从构造方法
open class View1(s: String) {
    constructor(ctx: Context) : this("")// 从构造方法
    constructor(ctx: Context, attr: AttributeSet? = null) : this("s")
}

class MyButton : View1 {
    // 调用父类构造方法
    constructor(ctx: Context) : super(ctx)
//    constructor(ctx: Context, s: String) : this(ctx, "a", null)//委托给当前类的另一个构造方法
//    constructor(ctx: Context, s1: String, attr: AttributeSet? = null) : super(ctx, attr)
}

// 接口可以包含抽象属性声明
interface User3 {
    val nickname: String// 抽象属性
}

class PrivateUser(override val nickname: String) : User3// 主构造方法属性
class SubscribingUser(val email: String) : User3 {
    override val nickname: String
        get() = email.substringBefore('@')// 自定义getter
}

class FacebookUser(val accountId: Int) : User3 {
    override val nickname = "getFacebookName $accountId"// 属性初始化
}

interface User4 {
    // 接口可以包含具有getter和setter的属性
    val email: String// 抽象属性
    val nickname: String
        get() = email.substringBefore('@')// 属性没有支持字段：结果值在每次访问时计算得到
}

// 在setter中访问支持字段
class User5(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println(
                """
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent()// 读取支持字段field的值
            )
            field = value
        }
}

// 声明一个具有private setter的属性
class LengthCounter {
    var counter: Int = 0
        private set// 不能在类外部修改这个属性

    fun addWord(word: String) {
        counter += word.length
    }
}

// ==对等于java的equals，===比较引用
// 数据类，默认重写了eqaulas/hashCode/toString，注意:没有在主构造方法中声明的属性将不会加入到相等性检查和哈希值计算中去
data class Client(val name: String, val postalCode: Int)

// Kotiin 将委托作为一个语言级别的功能做了头等支持
class DelegatingCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList {}// 通过by将接口的实现委托到另一个对象，编译器会生成样板代码

// 使用委托类
class CountingSet<T>(
    val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet { // 将MutableCollection的实现委托给innerSet
    var objectsAdded = 0
    override fun add(element: T): Boolean {// 不使用委托，自己实现
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean {
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}

// 对象声明将类声明与该类的单一实例声明结合到了一起。对象声明在定义的时候就立即创建了。
object Payroll {
    val allEmployees = arrayListOf<String>()
    fun calculateSalary() {
        for (a in allEmployees) {
            println(a)
        }
    }
}

// 使用对象来实现Comparator
object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(file2.path, ignoreCase = true)
    }
}

// 使用嵌套类实现Comparator
data class Person2(val name: String) {
    object NameComparator : Comparator<Person2> {
        // kotlin中对象声明被编译成通过静态字段来持有它的单一实例的类，名字是INSTANCE
        override fun compare(p1: Person2, p2: Person2): Int {
            return p1.name.compareTo(p2.name)
        }
    }
}

class A {
    companion object {
        fun bar() {
            println("Companion object called")
        }
    }
}

// 定义一个拥有多个从构造方法的类
class User6 {
    val nickname: String

    constructor(email: String) {
        nickname = email.substringBefore('@')
    }

    constructor(facebookAccountId: Int) {
        nickname = "getFacebookName $facebookAccountId"
    }
}

// 使用工厂方法来替代从构造方法
class User7 private constructor(val nickname: String) {    // 柱构造方法私有

    companion object {
        // 用工厂方法构造对象
        fun newSubscribingUser(email: String) = User7(email.substringBefore('@'))
        fun newFacebookUser(accountId: Int) = User7("getFacebookName $accountId")
    }
}

// 声明一个命名伴生对象
class Person3(val name: String) {
    companion object Loader {
        fun fromJSON(jsonText: String): Person3 = Person3("xxx")
    }
}

// 伴生对象中实现接口
interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Person4(val name: String) {
    companion object : JSONFactory<Person4> {
        override fun fromJSON(jsonText: String): Person4 {
            return Person4("xxx")
        }
    }
}

// 为伴生对象定义一个扩展函数
class Person5(val firstName: String, val lastName: String) {
    companion object {// 声明一个空的伴生对象

    }
}

fun Person5.Companion.fromJSON(json: String): Person5 {// 声明一个扩展函数
    return Person5("a", "b")
}

fun main() {
//    testImpleInterface()

    val alice = User1("Alice")
    println(alice.isSubscribed)
    println(PrivateUser("test@xx.org").nickname)
    println(SubscribingUser("test@xx.org").nickname)

    val user = User5("Alice")
    user.address = "Elsenhedd "// 底层调用了setter

    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi")
    println(lengthCounter.counter)

    val bob = Client("Bob", 2222)
    println(bob.copy(postalCode = 222))

    val cset = CountingSet<Int>()
    cset.addAll(listOf(1, 1, 2))
    println("${cset.objectsAdded} objects were added, ${cset.size} remain")

    // 使用对象声明
    Payroll.allEmployees.add("")
    Payroll.calculateSalary()

    println(CaseInsensitiveFileComparator.compare(File("/User"), File("/user")))
    val files = listOf(File("/Z"), File("/a"))
    println(files.sortedWith(CaseInsensitiveFileComparator))

    val persons = listOf(Person2("Bob"), Person2("Alice"))
    println(persons.sortedWith(Person2.NameComparator))

    // 可以直接通过容器类名称访问这个对象的方法和属性
    A.bar()

    val subscribingUser = User7.newSubscribingUser("xx@xs.com")
    val facebookUser = User7.newFacebookUser(4)
    println(subscribingUser.nickname)
    println(facebookUser.nickname)

    // 大多数不关心伴生对象的类名，默认是Companion
    Person3.Loader.fromJSON("{name: 'dd'}")
    Person3.fromJSON("{name: 'dd'}")

    val p = Person5.fromJSON("")

    // object声明匿名对象，使用匿名对象实现事件监听器
    Window.getWindows()[0].addMouseListener(
        // 声明一个继承MouseAdapter的匿名对象，对象表达式声明了一个类并创建了该类的一个实例，不是单利
        object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
            }
        }
    )

    // 从匿名对象访问局部变量
    fun countClicks(window: Window) {
        var clickCount = 0
        window.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                clickCount++// 更新外部局部变量值
            }
        })
    }
}
