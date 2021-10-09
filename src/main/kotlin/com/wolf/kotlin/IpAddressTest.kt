package com.wolf.kotlin

import inet.ipaddr.IPAddress
import inet.ipaddr.IPAddressString
import inet.ipaddr.ipv6.IPv6Address
import inet.ipaddr.ipv6.IPv6AddressTrie
import java.util.*
import kotlin.collections.ArrayList

// https://github.com/seancfoley/IPAddress/wiki/Code-Examples
fun main() {
    var ipv6Str = "::/64"
    val ipv4Str = "1.2.255.4/255.255.0.0"
    val ipv4Address = IPAddressString(ipv4Str).toAddress()
    val ipv6Address = IPAddressString(ipv6Str).toAddress()
    println(ipv4Address)
    println(ipv6Address)


//    val hostPortStr = "[a:b:c:d:e:f:a:b]:8080"
//    val hostServiceStr = "a.b.com:service"
//    val hostAddressStr = "1.2.3.4"
//    val dnsStr = "a.b.com"
//    var host = HostName(hostPortStr)
//    var socketAddress = host.asInetSocketAddress()
//    // use socket address
//    host = HostName(hostServiceStr)
//    socketAddress = host.asInetSocketAddress { service: String -> if (service == "service") 100 else null }
//    // use socket address
//    host = HostName(hostAddressStr)
//    var address = host.asAddress() // does not resolve
//    // use address
//    host = HostName(dnsStr)
//    address = host.toAddress() // resolves if necessary
//    // use address


    ipv6Str = "a:b:c:d::a:b/64"
    val ipv6AddressStr = IPAddressString(ipv6Str)
    val ipv6Addr = ipv6AddressStr.toAddress()
    // use address
    println(ipv6Addr) // a:b:c:d::a:b/64


    val ipv6v4Str = "a:b:c:d:e:f:1.2.3.4/112"
    val ipv6v4AddressStr = IPAddressString(ipv6v4Str)
    val ipAddr = ipv6v4AddressStr.address
    println(ipAddr) // a:b:c:d:e:f:102:304/112

    val ipv4Addr = ipAddr?.toIPv6()?.embeddedIPv4Address
    println(ipv4Addr) // 1.2.3.4/16


    val byteArray = byteArrayOf(32, 32, 1, 98, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
    val iPv6Address = IPv6Address(byteArray)
    println(iPv6Address.isIPv4)
    println(iPv6Address.isIPv6)
    println(iPv6Address.contains(iPv6Address))

    var ipAddressString = IPAddressString("fe80::806c:7b93:63b8:1022")
    println(ipAddressString.address)
    println(ipAddressString.isIPv6)

    ipAddressString = IPAddressString("fe80::21a:a9ff:fe7d:ed76/16")
    println(ipAddressString.contains(IPAddressString("fe80::21a:a9ff:fe7d:ed76")))
    println(ipAddressString.toSequentialRange().contains(IPAddressString("fe80::21a:a9ff:fe7d:ed72").address))
    var addressTrie = IPv6AddressTrie()
    addressTrie.add(ipAddressString.address.toIPv6())

    println(addressTrie.contains(IPAddressString("fe80::21a:a9ff:fe7d:ed72").address))


    val containing = IPAddressString("1::3-4:5-6").address.contains(
        IPAddressString("1::4:5").address
    )
    println("==>containing:$containing")


    contains("10.10.20.0/30", "10.10.20.3");
    contains("10.10.20.0/30", "10.10.20.5");
    contains("10.10.20.0/30", "10.10.20.0/31");
    contains("1::/64", "1::1");
    contains("1::/64", "2::1");
    contains("1::/64", "1::/32");
    contains("1::/64", "1::/112");
    contains("1::3-4:5-6", "1::4:5");
    contains("1-2::/64", "2::");
    contains("bla", "foo");




    range("192.200.0.0", "192.255.0.0", "192.200.3.0");
    range(
        "2001:0db8:85a3::8a2e:0370:7334", "2001:0db8:85a3::8a00:ff:ffff",
        "2001:0db8:85a3::8a03:a:b"
    );
    range("192.200.0.0", "192.255.0.0", "191.200.3.0");
    range(
        "2001:0db8:85a3::8a2e:0370:7334", "2001:0db8:85a3::8a00:ff:ffff",
        "2002:0db8:85a3::8a03:a:b"
    );


    val ipv6AddrStrs = arrayOf(
        "2001:4860:4860::8888", "2001:4860:4860::8844", "2620:fe::fe",
        "2620:fe::9", "2620:119:35::35", "2620:119:53::53",
        "2606:4700:4700::1111", "2606:4700:4700::1001", "2a0d:2a00:1::2",
        "2a0d:2a00:2::2", "2620:74:1b::1:1", "2620:74:1c::2:2",
        "2001:4800:780e:510:a8cf:392e:ff04:8982",
        "2001:4801:7825:103:be76:4eff:fe10:2e49",
        "2a00:5a60::ad1:0ff", "2a00:5a60::ad2:0ff"
    )
    val addresses: MutableList<IPv6Address> = ArrayList()
    for (str in ipv6AddrStrs) {
        addresses.add(IPAddressString(str).address.toIPv6())
    }

    addressTrie = IPv6AddressTrie()
    for (addr: IPv6Address in addresses) {
        addressTrie.add(addr)
    }
    println(
        "There are " + addressTrie.size() +
                " unique addresses in the trie:\n" + addressTrie
    )


    val toSequentialRange = IPAddressString("fe80::250:a9ff:fe7d:ed76").address.toIPv6().toSequentialRange()
    val join = toSequentialRange.join(IPAddressString("fe80::250:56ff:feb8:f8d").sequentialRange)

    """
32.32.1.98.0.0.0.0.0.0.0.0.0.0.0.1
254.128.0.0.0.0.0.0.2.26.169.255.254.125.237.118
254.128.0.0.0.0.0.0.2.80.86.255.254.184.15.141
254.128.0.0.0.0.0.0.2.80.86.255.254.188.51.100
254.128.0.0.0.0.0.0.96.0.216.197.115.158.155.161
254.128.0.0.0.0.0.0.138.168.44.165.48.85.59.227
254.128.0.0.0.0.0.0.201.123.47.222.12.190.198.232
33.0.1.96.0.0.0.0.0.0.0.0.0.0.0.1
33.0.1.96.0.0.0.0.220.52.249.202.42.71.229.68
254.128.0.0.0.0.0.0.1.228.251.150.234.125.13.176
254.128.0.0.0.0.0.0.2.26.169.255.254.125.237.118
254.128.0.0.0.0.0.0.40.90.134.81.142.131.247.170
254.128.0.0.0.0.0.0.45.94.91.33.127.48.193.27
254.128.0.0.0.0.0.0.128.108.123.147.99.184.16.34
254.128.0.0.0.0.0.0.201.154.90.221.140.235.143.249
254.128.0.0.0.0.0.0.220.52.249.202.42.71.229.68
""".split("\n").forEach {

    }


    var prefixedAddrStr = "10.10.20.1/30";
    var addrStr = "10.10.20.3";
    contains(prefixedAddrStr, addrStr);
    enclosingBlockContains(prefixedAddrStr, addrStr);

    prefixedAddrStr = "1::f/64";
    addrStr = "1::1";
    contains(prefixedAddrStr, addrStr);
    enclosingBlockContains(prefixedAddrStr, addrStr);

//    prefixedAddrStr.toLong()


//    print(merge("209.152.214.112/30", "209.152.214.116/31", "209.152.214.118/31"));
//    print(merge("209.152.214.112/30", "209.152.214.116/32", "209.152.214.118/31"));
//    print(
//        merge(
//            "1:2:3:4:8000::/65", "1:2:3:4::/66", "1:2:3:4:4000::/66",
//            "1:2:3:5:4000::/66", "1:2:3:5::/66", "1:2:3:5:8000::/65"
//        )
//    );


    var address = IPAddressString("1.2.0.0/16").address
    System.out.println(address.contains(IPAddressString("1.2.3.4").address))
    System.out.println(address.contains(IPAddressString("1.2.3.0/24").address))
    System.out.println(address.contains(IPAddressString("1.2.3.0/25").address))
    System.out.println(address.contains(IPAddressString("1.1.0.0").address))


    address = IPAddressString("2001:db8:85a3::8a00:ff:ffff/48").address
    System.out.println(address.contains(IPAddressString("2001:db8:85a3::8a00:ff:ffff").address))
    System.out.println(address.contains(IPAddressString("2001:db8:85a3::8a00:ff:fff1").address))
    System.out.println(address.contains(IPAddressString("2001:db8:85a3::8a00:ff:fff2").address))
    System.out.println(address.contains(IPAddressString("2001:db8:85a3::8a00:ff:fff3").address))
    System.out.println(address.prefixContains(IPAddressString("2001:db8:85a3::8a00:ff:fff3").address))
    System.out.println(address.prefixContains(IPAddressString("2001:db8:85a3:1:1:8a00:ff:fff3").address))
    System.out.println(address.prefixContains(IPAddressString("2001:db8:85a4::8a00:ff:fff3").address))


}

fun print(addresses: Array<IPAddress?>?) {
    System.out.println("blocks are " + Arrays.asList(addresses))
}


//fun merge(vararg strs: String) {
//    var first = IPAddressString(strs[0]).getAddress();
//    var remaining = Arrays.stream(strs, 1, strs.size).map { str -> IPAddressString(str).getAddress() }
//        .toArray<Array<IPAddress>>{a->Array<IPAddress>(a)};
//    return first.mergeToPrefixBlocks(remaining);
//}

fun enclosingBlockContains(network: String, address: String) {
    var one = IPAddressString(network);
    var oneAddr = one.getAddress().toPrefixBlock();
    var two = IPAddressString(address);
    var twoAddr = two.getAddress();
    println("$one block $oneAddr contains $twoAddr ${oneAddr.contains(twoAddr)}");
}

fun contains(network: String, address: String) {
    var one = IPAddressString(network);
    var two = IPAddressString(address);
    println("$one contains $two ${one.contains(two)}")
}


fun range(lowerStr: String, upperStr: String, str: String) {
    var lower = IPAddressString(lowerStr).toAddress();
    var upper = IPAddressString(upperStr).toAddress();
    var addr = IPAddressString(str).toAddress();
    var range = lower.toSequentialRange(upper);
    println("$range contains $addr ${range.contains(addr)}")
}

//public static long[] ip2Longs(String ipString) {
//    if (ipString == null || ipString.isEmpty()) {
//        throw new IllegalArgumentException("ipString cannot be null.");
//    }
//    String[] ipSlices = ipString.split(":");
//    if (ipSlices.length != 8) {
//        throw new IllegalArgumentException(ipString + " is not an ipv6 address.");
//    }
//    long[] ipv6 = new long[2];
//    for (int i = 0; i < 8; i++) {
//        String slice = ipSlices[i];
//        // 以 16 进制解析
//        long num = Long.parseLong(slice, 16);
//        // 每组 16 位
//        long right = num << (16 * i);
//        // 每个 long 保存四组，i >> 2 = i / 4
//        ipv6[i >> 2] |= right;
//    }
//    return ipv6;
//}
