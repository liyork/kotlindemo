package com.wolf.kotlin

fun main() {
    val list = mutableListOf<Person4>()
    list.add(Person4("a", "2"))
    list.add(Person4("a", "1"))
    list.add(Person4("b", "2"))
    list.add(Person4("b", "1"))

    // 提供选择器，默认排序
    list.sortBy { it.firstName }
    println(list)

    // 提供Comparator排序
    list.sortWith { o1, o2 -> o1!!.firstName.compareTo(o2!!.firstName) }
    println(list)

    // 与上面相同，返回list
    val sortedWith = list.sortedWith { o1, o2 -> o1!!.firstName.compareTo(o2!!.firstName) }
    println(sortedWith)

    // 依次排序
    val sortedWith1 = list.sortedWith(compareBy({ it.firstName }, { it.lastName }))
    println(sortedWith1)

    // 与上面一样效果
    val sortedWith2 = list.sortedWith(compareBy<Person4> { it.firstName }.thenBy { it.lastName })
    println(sortedWith2)
}