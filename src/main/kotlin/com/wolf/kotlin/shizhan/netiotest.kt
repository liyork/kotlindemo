package com.wolf.kotlin.shizhan

import java.net.URL
import java.nio.charset.Charset

fun main() {
    val url = URL("http://www.baidu.com")
    val readText = url.readText(Charset.defaultCharset())
    println(readText)
    val readBytes = url.readBytes()
    println(readBytes)
}