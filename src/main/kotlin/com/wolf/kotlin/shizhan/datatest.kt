package com.wolf.kotlin.shizhan

// 数据类就是只存储数据， 不包含操作行为的类
data class LoginUser(val username: String, val password: String)
// 反编译成等价的Java代码:Tools|Kotlin|Show Kotlin Bytecode->Decompile
// 编译器会根据主构造函数中声明的属性， 自动创建以下3个函数:equals()/hashCode()和component1()和component2()和copy()

// 数据类的语法限制
// 主构造函数至少包含一个参数；
//参数必须标识为val或者var；
//不能为abstract、 open、 sealed或者inner；
//不能继承其他类（但可以实现接口）

private fun testData() {
    val loginUser = LoginUser("admin", "admin")
    val (username, password) = loginUser// 解构声明
    println("username = ${username}, password = ${password}")
}

// 注解是将元数据附加到代码中。 元数据信息由注解kotlin.Metadata定义
// 这个@Metadata信息存在于由Kotlin编译器生成的所有类文件中， 并由编译器和反射读取
annotation class Suspendable// 声明一个注解

// 枚举
enum class Direction {
    NORTH, SOUTH, WEST, EAST// 每个枚举都是一个对象
}

// 每一个枚举都是枚举类的实例， 它们可以被初始化
enum class Color(val rgb: Int) {
    // 声明一个带构造参数rgb:Int的枚举类
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x00FF00)
}

private fun testEnum() {
    val north = Direction.NORTH
    println("name=>${north.name}, orinal=>${north.ordinal}")

    val c = Color.GREEN
    println(c.rgb)
}

// 普通嵌套类
class NestedClassesDemo {
    class Outer {
        private val zero: Int = 0
        val one: Int = 1

        class Nested {
            fun getTwo() = 2

            // 普通嵌套类没有持有外部类的引用，是无法访问外部类变量
            fun accessOuter() = {
//                println(zero)
//                println(one)
            }

            class Nested1 {
                val three = 3
                fun getFour() = 4
            }
        }

        // 嵌套内部类
// 如果一个类Inner想要访问外部类Outer中的成员， 可以在这个类前
//面添加修饰符inner。 内部类会带有一个对外部类的对象引用
        inner class Inner {
            fun accessOuter() = {
                println(zero)
                println(one)
            }
        }
    }

    class AnnoymousInnerClassDemo {
        var isRunning = false
        fun doRun() {
            // 匿名内部类就是没有名字的内部类。 匿名内部类也可以访问外部类的变量
            Thread(object : Runnable {
                override fun run() {
                    isRunning = true
                    println("doRun: i am running.")
                }
            }).start()
        }
    }
}


fun main() {
//    testData()

    // 使用Pair对象初始化map
//    val map = mapOf(1 to "A", 2 to "B")
//    println(map)

//    testEnum()

    val one = NestedClassesDemo.Outer().one
    val two = NestedClassesDemo.Outer.Nested().getTwo()
    val three = NestedClassesDemo.Outer.Nested.Nested1().three
    val four = NestedClassesDemo.Outer.Nested.Nested1().getFour()

    // 用的是Outer()调用.Inner()，就持有了Outer的对象引用
    val innerClass = NestedClassesDemo.Outer().Inner().accessOuter()

    // 如果对象是函数式Java接口， 即具有单个抽象方法的Java接口的实例，可以使用Lambda表达式实现对应接口
    var isRunning = true
    Thread({// 直接使用lambda表达式
        isRunning = false
    }).start()

    // 使用匿名内部类方式
    Thread(object : Runnable {
        override fun run() {
            isRunning = false
        }
    }).start()

    // 声明一个lambda函数
    var wait = {
        isRunning = false
    }
    Thread(wait).start()
}



