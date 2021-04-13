package com.wolf.kotlin.testinternal.a

class B2 {
    fun foo() {
        println("B foo")
    }

    internal fun foo2() {
        println("B foo")
    }

    companion object {
        // 相当于java的private
        val b = "b"

        // 相当于java的public
        const val c = "c"
    }
}

