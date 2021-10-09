package com.wolf.kotlin

import java.util.concurrent.TimeUnit

// 名字相同的线程会共存
fun main() {
    val thread = Thread({
        println("1111")
        TimeUnit.SECONDS.sleep(50)
    }, "xxxx1")
    thread.start()

    TimeUnit.SECONDS.sleep(2)
    println("22222222222")
//    thread.interrupt()

    val thread2 = Thread({
        println("33333")
        TimeUnit.SECONDS.sleep(50)
    }, "xxxx1")
    thread2.start()

    Thread.currentThread().join()
}