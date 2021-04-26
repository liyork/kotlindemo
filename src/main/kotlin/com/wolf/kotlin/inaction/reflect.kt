package com.wolf.kotlin.inaction

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer
import com.wolf.kotlin.fromJson
import com.wolf.kotlin.toJson
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.omg.CORBA.FieldNameHelper
import java.lang.StringBuilder
import kotlin.reflect.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

// 元注解
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation

fun foo(x: Int) = println(x)
fun sum(x: Int, y: Int) = x + y

var counter = 0

fun main() {
    class MyTest {
        @Test// 这注解指引JUnit框架把这个方法当测试调用
        fun testTrue() {
            Assert.assertTrue(true)
        }
    }

    @Deprecated("Use removeAt(index) insteda.", ReplaceWith("removeAt(index"))
    fun remove(index: Int) = 1

    class HasTempFolder {
        @get:Rule// 注解的是getter，而不是属性
        val folder = TemporaryFolder()

        @Test
        fun testXx() = {}
    }

    data class Person(
        @JsonProperty("xxx")
        val name: String,
        @JsonIgnore
        val age: Int
    )

    val person = Person("alice", 29)
    val toJson = toJson(person)
    println(toJson)
    val p = fromJson<Person>(toJson)

    // 反射
    val pClass: KClass<Person> = Person::class// 得到一个KClass的实例
    val kClass = person.javaClass.kotlin// 返回一个KClass<Person>的实例
    println(pClass == kClass)
    println(kClass.simpleName)
    kClass.memberProperties.forEach { println(it.name) }

    // 通过反射调用方法
    val kFunction: KFunction1<Int, Unit> = ::foo// 1表示这个函数接收一个形参
    kFunction.call(42)
    // 用更具体的方法调用函数
    val kFunction2: KFunction2<Int, Int, Int> = ::sum
    println(kFunction2.invoke(1, 2) + kFunction2(3, 4))
//    println(kFunction2(1)) // 编译不通过，参数个数不对，优先使用invoke,call是对所有类型都有效的通用手段
    // KFunction1这样的类型代表不同数量参数的函数，每一个类型都继承了KFunction并加上一个额外成员invoke，拥有数量刚好的参数，这种类型称为合成的编译器生成类型。

    val kProperty = ::counter// 顶层属性表示为KProperty0接口的实例
    kProperty.setter.call(21)// 通过反射调用setter
    println(kProperty.get())// 通过get说去属性的值

    // 只能通过反射访问定义在最外层或者类中的属性
    // <接收者的类型,属性的类型>
    val memberProperty: KProperty1<Person, Int> = Person::age// 指向属性的引用
    println(memberProperty.get(person))

    // 序列化一个对象,过滤序列化对象
    fun StringBuilder.serializeObject(obj: Any) {
        val kClass = obj.javaClass.kotlin
        val properties = kClass.memberProperties// 获取类的所有属性
            .filter { it.findAnnotation<JsonIgnore>() == null }
        properties.joinToString(this, prefix = "{", postfix = "}") { prop ->
            append("serializeString(${prop.name})")// 序列化属性
            append(":")
            append("serializePropertyValue(${prop.get(obj)})")// 序列化属性值
        }
    }

    // 序列化单个属性
    fun StringBuilder.serializeProperty(prop: KProperty1<Any, *>, obj: Any) {
        val jsonNameAnn = prop.findAnnotation<JsonProperty>()
        val propName = jsonNameAnn?.value ?: prop.name
        append("serializeString(${propName})")
        append(":")
        append("serializePropertyValue(${prop.get(obj)})")
    }

    // 模拟依据class然后构造实例
    fun KProperty<*>.getSerializer() {
        val customSerializerAnn = findAnnotation<JsonProperty>() ?: return
        val serializerClass = customSerializerAnn.annotationClass
        // 前者表示object创建的单例实例，后者表示普通的类
        val valueSerializer = serializerClass.objectInstance ?: serializerClass.createInstance()
        println(valueSerializer)
    }
}