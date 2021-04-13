package com.wolf.kotlin.shizhan

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class Introduction {

    @Test
    fun test() {
        val list = getArrayList()
        Assert.assertTrue(list.size == 2)

        println("abc".firstChar())

        list.filter { it.length == 1 }
    }

    private fun getArrayList(): List<String> {
        val arrayList = ArrayList<String>()
        arrayList.add("A")
        arrayList.add("B")
        return arrayList
    }

    // 为类扩展一个函数
    fun String.firstChar(): String {
        if (this.isEmpty()) {
            return ""
        }
        return this[0].toString()
    }

}