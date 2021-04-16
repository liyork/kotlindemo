package com.wolf.kotlin.shizhan.packagetest.test

import com.wolf.kotlin.shizhan.packagetest.Motorbike
import com.wolf.kotlin.shizhan.packagetest.what
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PackageDemoTest {

    @Test
    fun testWhat() {
        what()// 不在一个包，需要导入函数what()
    }

    @Test
    fun testDriveMotorbike() {
        val motorbike = Motorbike()// 需要导入类Motorbike
        motorbike.drive()
    }

}