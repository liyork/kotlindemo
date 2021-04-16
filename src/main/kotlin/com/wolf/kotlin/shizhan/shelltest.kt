package com.wolf.kotlin.shizhan

import java.io.BufferedReader
import java.io.InputStreamReader

fun String.execute(): Process {
    val runtime = Runtime.getRuntime()
    return runtime.exec(this)
}

fun Process.text(): String {
    var output = ""
    val inputStream = this.inputStream
    val isr = InputStreamReader(inputStream)
    val reader = BufferedReader(isr)
    var line: String? = ""
    while (line != null) {
        line = reader.readLine()
        output += line + "\n"
    }
    return output
}

fun main() {
    val p = "ls -al".execute()
    val exitCode = p.waitFor()
    val text = p.text()

    println(exitCode)
    println(text)
}