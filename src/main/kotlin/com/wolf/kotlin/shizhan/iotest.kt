package com.wolf.kotlin.shizhan

import com.fasterxml.jackson.core.util.BufferRecycler
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.Reader
import java.nio.charset.Charset

fun main() {
    val f = File("/Users/chaoli/intellijWrkSpace/kotlindemo/src/main/kotlin/com/wolf/kotlin/shizhan/a.txt")

//    testReadText(f)

//    testReadLines(f)

//    testReadBytes(f)

    val f2 = File("/Users/chaoli/intellijWrkSpace/kotlindemo/src/main/kotlin/com/wolf/kotlin/shizhan/b.txt")
//    testWriteText(f2)

//    testAppend(f2)

    val f3 = File("/Users/chaoli/intellijWrkSpace/kotlindemo/src/main/kotlin/com/wolf/kotlin/shizhan")
    val fileTreeWalk = f3.walk()
    // 子目录及文件
//    fileTreeWalk.iterator().forEach { println(it.absolutePath) }
    fileTreeWalk.filter { f.isFile }.iterator().forEach { println(it.absolutePath) }

}

private fun testAppend(f2: File) {
    f2.appendText("ccc", Charset.defaultCharset())
    f2.appendBytes("ddd".toByteArray(Charset.defaultCharset()))
}

private fun testWriteText(f2: File) {
    if (!f2.exists()) {
        f2.createNewFile()
    }
    f2.writeText("abc", Charset.defaultCharset())
}

private fun testReadBytes(f: File) {
    val readBytes = f.readBytes()// 返回这个文件的字节数组
    println(readBytes.joinToString(separator = " "))

    // 与java互操作
    val reader: Reader = f.reader()
    val inputStream: InputStream = f.inputStream()
    val bufferedReader: BufferedReader = f.bufferedReader()
}

private fun testReadLines(f: File) {
    val readLines = f.readLines(Charset.forName("UTF-8"))
    readLines.forEach {
        println(it)
    }
}

private fun testReadText(f: File) {
    // 获取整个文件的内容，以UTF-8编码格式的字符串
    val readText = f.readText(Charset.forName("UTF-8"))
    println(readText)
}