package com.wolf.kotlin.shizhan

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions

// 注解
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
@Repeatable
@MustBeDocumented
annotation class TestCase(val id: String)

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER,
)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@MustBeDocumented
annotation class Run

@Run
class SwordTest {

    @TestCase(id = "1")
    fun testCase(testId: String) {
        println("Run SwordTest ID = ${testId}")
    }
}

@RunWith(JUnit4::class)
class AnnotationClassNotTest {
    @Test
    fun testAnno() {
        val sword = SwordTest()
        sword.testCase("10000")
    }
}


private fun testAnno() {
    val sword = SwordTest()
    val kClass: KClass<out SwordTest> = sword::class// 通过变量获取该对象的类的信息,获取的是sword对象实例的KClass类的引用

    val declaredFunctions = kClass.declaredFunctions// 声明的所有函数
    for (f in declaredFunctions) {
        f.annotations.forEach {
            if (it is TestCase) {
                val id = it.id
                println(id)
                f.call(sword, id)// 通过反射调用函数,等价于f.javaMethod?.invoke(sword,id)
            }
        }
    }
}

fun main() {
//    testAnno()

}
