package com.wolf.kotlin.shizhan

import kotlin.concurrent.thread


private fun testThread() {
    // object对象表达式,创建一个匿名类
    object : Thread() {
        override fun run() {
            sleep(3000)
            println("a use Thread object expression ${currentThread().name}")
        }
    }.start()

    Thread({// lambda表达式
        Thread.sleep(2000)
        println("b use lambda expression, ${Thread.currentThread().name}")
    }).start()

    thread(start = true, isDaemon = false, name = "DThread", priority = 3) {
        Thread.sleep(1000)
        println("d use encapsulate thread, ${Thread.currentThread().name}")
    }
}

@Synchronized// 同步方法
fun appendFile() {
    println("appendFile, ${Thread.currentThread().name}")
}

class aa {
    fun appendFile1() {
        synchronized(this) {
            println("use synchronize block")
        }
    }
}

@Volatile
private var running = false

fun start() {
    println("start")
    running = true
    thread(start = true) {
        while (running) {
            println("still running, ${Thread.currentThread().name}")
            Thread.sleep(1000)
        }
    }
}

fun stop() {
    running = false
    println("stop: ${Thread.currentThread().name}")
}

fun main() {
//    testThread()

// Synchronize

//    testVolatile()
}

private fun testVolatile() {
    start()
    Thread.sleep(2000)
    stop()
}
