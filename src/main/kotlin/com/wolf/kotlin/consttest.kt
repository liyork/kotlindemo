package com.wolf.kotlin

// const只能修饰val
// 顶层类、object中，可以使用const，被转换成java的静态字段
//
//访问kotlin的val字段默认用get和set，而对于const则是直接访问
//前者是private final static，后者是public final static
//使用const val方式可以避免频繁函数调用
//
//在kotlin语法中，有没有const修饰都是直接引用的，感觉不到引用方式上有什么区别。
//在java语法中，没用cont修饰的，通过getter方法获取属性信息，而用了const修饰符的属性，则可以直接像java中的常量一样直接通过类引用。