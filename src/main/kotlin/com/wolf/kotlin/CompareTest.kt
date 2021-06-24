package com.wolf.kotlin

fun main() {
//    defaultSort()

//    sortByField()

//    sortByMultiField()

//    sortByMultiFieldPrinciple()


    sortWithObject()
}

// 对象排序
private fun sortWithObject() {
    val list = mutableListOf<Person4>()
    list.add(Person4("a", "2"))
    list.add(Person4("a", "1"))
    list.add(Person4("b", "2"))
    list.add(Person4("b", "1"))

    // 提供筛选器，默认排序
    list.sortBy { it.firstName }
    println(list)

    // 提供Comparator排序
    list.sortWith { o1, o2 -> o1!!.firstName.compareTo(o2!!.firstName) }
    println(list)

    // 与上面相同，不过是ed结尾方法，返回list
    val sortedWith = list.sortedWith { o1, o2 -> o1!!.firstName.compareTo(o2!!.firstName) }
    println(sortedWith)

    // 依次排序，多字段排序
    val sortedWith1 = list.sortedWith(compareBy({ it.firstName }, { it.lastName }))
    println(sortedWith1)

    // 与上面一样效果，不同写法
    val sortedWith2 = list.sortedWith(compareBy<Person4> { it.firstName }.thenBy { it.lastName })
    println(sortedWith2)
}

// 原理
private fun sortByMultiFieldPrinciple() {
    val sortedValues = mutableListOf(1 to "a", 2 to "b", 7 to "c", 6 to "d", 5 to "c", 6 to "e")
    sortedValues.sortWith(// 方法参数是:Comparator对象
        compareBy(// 方法参数是:多个(T) -> Comparable<*>?的函数，函数的参数是T，返回值是Comparable
            fun(it: Pair<Int, String>): Comparable<*>? {// 定义函数：参数是Pair，返回值是String即Comparable
                return it.second
            }, fun(it: Pair<Int, String>): Comparable<*>? {// 也叫做筛选器，selectors，即传入要排序对象，出来的是可排序的字段
                return it.first
            })
    )
    println(sortedValues)
}

private fun sortByMultiField() {
    // 组合多个规则
    val sortedValues = mutableListOf(1 to "a", 2 to "b", 7 to "c", 6 to "d", 5 to "c", 6 to "e")
    // 具有多个属性的对象，我们可以使用compareBy方法，生成
    sortedValues.sortWith(compareBy({ it.second }, { it.first }))
    println(sortedValues) // [(1, a), (2, b), (5, c), (7, c), (6, d), (6, e)]
}

private fun sortByField() {
    // 按特定字段排序
    val mapList = mutableListOf(1 to "A", 2 to "B", 5 to "C", 3 to "D")
    mapList.sortBy { it.first }
    println(mapList) // [(1, A), (2, B), (3, D), (5, C)]
    mapList.sortBy { it.second }
    println(mapList) // [(1, A), (2, B), (5, C), (3, D)]}
    mapList.sortedBy { it.first }// 返回新排序集合，原有顺序不变
}

private fun defaultSort() {
    val intArray = mutableListOf(1, 2, 6, 3, 7, 9, 4)
    intArray.sort()// 自然顺序排序a在b前，1在2前，将原有集合排序
    println(intArray) // [1, 2, 3, 4, 6, 7, 9]}

    val intArray2 = mutableListOf(1, 2, 6, 3, 7, 9, 4)
    val sorted = intArray2.sorted()// 返回新排序集合，原有集合不变
    println(intArray2)
    println(sorted)
}