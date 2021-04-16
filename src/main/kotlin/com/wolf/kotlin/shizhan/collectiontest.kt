package com.wolf.kotlin.shizhan


private fun testBase() {
    // kotlin集合类中不仅仅能持有普通对象，而且能够持有函数类型的变量
    // (Int)->Boolean是一个从Int映射到Boolean的函数
    val funlist: List<(Int) -> Boolean> =// 声明一个持有类型为函数(Int) -> Boolean的list
        listOf({ it -> it % 2 == 0 }, { it -> it % 2 == 1 })

    val list = listOf(1, 2, 3, 4, 5, 6, 7)
    list.filter(funlist[0])
    list.filter(funlist[1])
}

private fun testConstruct() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7)// 不可变list
    val mutableList = mutableListOf("a", "b", "c")// 可变MutableList

    val set = setOf(1, 2, 3)// 不可变set
    val mutableSet = mutableSetOf("a", "b", "c")// 可变MutableSet

    val map = mapOf(1 to "a", 2 to "b")// 不可变map
    val mutableMap = mutableMapOf(1 to "x", 2 to "y")// 可变MutableMap

    // 空集合，需要显示声明类型
    val emptyList: List<Int> = listOf()// 显示声明List的元素类型为Int
    val emptySet: Set<Int> = setOf()
    val emptyMap: Map<Int, String> = mapOf()
}


private fun testIterator() {
    val list = listOf<String>()
    list.forEach {
        println(it)
    }
    list.forEachIndexed { index, value ->
        println("list index = $index, value = $value")
    }

    val map = mapOf("x" to 1, "y" to 2)
    println(map.entries)
    map.entries.forEach {
        println("key= ${it.key}, value= ${it.value}")
    }
}


private fun testMap() {
    // 使用map函数， 可以把集合中的元素依次使用给定的转换函数进行
    //映射操作， 元素映射之后的新值会存入一个新的集合中， 并返回这个新
    //集合
    val list = listOf(1, 2, 3, 4, 5, 6, 7)
    val set = setOf(1, 2, 3, 4, 5, 6, 7)
    val map = mapOf(1 to "a", 2 to "b", 3 to "c")
    list.map { it * it }// 对每个元素进行操作
    set.map { it + 1 }
    map.map { it.value + "$" }

    val strlist = listOf("a", "b", "c")
    val map1 = strlist.map { it -> listOf(it + 1, it + 2, it + 3) }// 每个元素it映射之后返回一个list
    println(map1)
    // “平铺”函数， 把嵌套在List中的元素“平铺”成一
    println(map1.flatten())

    val flatMap = strlist.flatMap { it -> listOf(it + 1, it + 2, it + 3) }
    println(flatMap)
}

data class Student(var id: Long, var name: String, var age: Int, var score: Int) {
    override fun toString(): String {
        return "Student(id=$id, name='$name', age=$age, score=$score)"
    }
}

fun main() {
//    testBase()

//    testConstruct()

//    testIterator()

//    testMap()

//    testFilter()

//    testSort()

    val dupList = listOf(1, 1, 2, 2, 3, 3)
    dupList.distinct()
}

private fun testSort() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7)
    val set = setOf(1, 3, 2)
    list.reversed()
    set.reversed()

    list.sorted()
    set.sorted()
}

private fun testFilter() {
    val studentList = listOf(
        Student(1, "j", 18, 90),
        Student(2, "r", 17, 80),
        Student(3, "a", 16, 70)
    )
    studentList.filter { it.age >= 18 }

    val list = listOf(1, 2, 3, 4, 5, 6, 7)
    list.filterIndexed { index, it -> index % 2 == 0 && it > 3 }
}
