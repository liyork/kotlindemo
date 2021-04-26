@file:JvmName("JoinKt")// 指定类名

package strings

import java.lang.StringBuilder

// 顶层函数
// kotlin编译生成的类名称为JoinKt，对应于包含函数的文件的名称，文件中所有顶层函数编译为类的静态函数
// java调用参见testJoinToStringInjava
fun <T> joinToString(
    collection: Collection<T>,
    separator: String = ",",// 参数的默认值被编码到被调用的函数中，那么对未传入参数的调用者是透明的用默认值
    prefix: String = "",
    postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

//顶层属性
var opCount = 0// 声明顶层属性，被存储到一个静态的字段中

// 默认，顶层属性和其他任意属性都是通过访问器暴露给java使用
const val UNIX_LINE_SEPARATOR = "\n"// public static final

