package com.wolf.kotlin

import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val date = Date(1632293981756)
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    println(simpleDateFormat.format(date))
}
