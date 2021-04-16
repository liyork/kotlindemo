package com.wolf.kotlin.shizhan

fun String.firstChar(): String {// 给String扩展一个firstChar函数
    if (this.length == 0) {
        return ""
    }
    return this[0].toString()
}

fun String.lastChar(): String {// 给String扩展一个lastChar函数
    if (this.length == 0) {
        return ""
    }
    return this[this.length - 1].toString()
}

fun <T> List<T>.filter(predicate: (T) -> Boolean): MutableList<T> {// 给List类扩展一个filter函数
    val result = ArrayList<T>()
    this.forEach {
        if (predicate(it)) {
            result.add(it)
        }
    }
    return result
}

private fun testExtClassFun() {
    println("abc".firstChar())
    println("abc".lastChar())
    // 如果扩展函数在其他package路径下，则需要import导入扩展函数

    val list = mutableListOf(1, 2, 3, 4, 5, 6, 7)
    val result = list.filter { it % 2 == 1 }
    println(result)
}

// 扩展属性,允许定义在类或kotlin文件中，不允许定义在函数中
// var表示：属性可变性，最后的T表示firstElement的类型
var <T> MutableList<T>.firstElement: T
    get() {
        return this[0]
    }
    set(value) {
        this[0] = value
    }
var <T> MutableList<T>.lastElement: T
    get() {
        return this[this.size - 1]
    }
    set(value) {
        this[this.size - 1] = value
    }

fun main() {
//    testExtClassFun()

//    testExtProperty()
}

private fun testExtProperty() {
    val list = mutableListOf(1, 2, 3, 4, 5, 6, 7)
    println("list = $list")
    println(list.firstElement)// 调用getter函数
    println(list.lastElement)

    list.firstElement = -1// 调用setter函数
    list.lastElement = -7

    println("list = $list")
    println(list.firstElement)
    println(list.lastElement)
}

