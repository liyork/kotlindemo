package com.wolf.kotlin

import org.apache.commons.net.util.SubnetUtils
import org.junit.jupiter.api.Test

class IpTest {
    @Test
    fun testInRange() {
        val info = SubnetUtils("172.17.166.20", "255.255.255.0").info
        println(info.isInRange("172.17.166.20"))
        println(info.isInRange("172.17.166.21"))
        println(info.isInRange("172.17.167.21"))
    }
}