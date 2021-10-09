package com.wolf.kotlin

import com.google.common.net.InetAddresses
import org.junit.jupiter.api.Test
import java.net.InetAddress
import java.util.*

class Ipv6Test {

    @Test
    fun testJson() {
        var a = InetAddress.getByName("2001:0DB8:AC10:FE01:0000:0000:0000:0000")
        var b = a.address;
        println(Arrays.toString(b))

        val toIpv6 = toIpv6("33.32.1.98.0.0.0.0.0.0.0.0.0.0.0.1")
        println(toIpv6)
        a = InetAddress.getByName(toIpv6)
        b = a.address;
        println(Arrays.toString(b))

        val ipv6 = "2001:0db8:3c4d:0015:0000:0000:1a2f:1a2b"
        val byName = InetAddress.getByName(ipv6) // 可能会用到dns
        println(Arrays.toString(byName.address))

        val forString = InetAddresses.forString(ipv6)
        println(Arrays.toString(forString.address))
    }

    fun toIpv6(s: String): String {
        val list = s.split('.')
        if (list.size < 16) return ""
        val bytes = ByteArray(16)
        val start = list.size - 16
        for (i in 0 until 16) {
            val int = list[start + i].toInt()
            if (int < 0 || int > 255) return ""
            bytes[i] = int.and(0xff).toByte()
        }
        val byAddress = InetAddress.getByAddress(bytes)
        return InetAddresses.toAddrString(byAddress)
    }
}