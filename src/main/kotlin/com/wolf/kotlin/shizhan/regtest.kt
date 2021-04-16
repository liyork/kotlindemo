package com.wolf.kotlin.shizhan

fun main() {
//    testCreate()

//    testMatches()

//    testContainsMatchIn()

//    testMatchEntire()

//    testReplace()

//    testFind()

    testUseJavaReg()
}

private fun testUseJavaReg() {
    val re = Regex("[0-9]+")
    val p = re.toPattern()
    val m = p.matcher("888ABC999")
    while (m.find()) {
        val d = m.group()
        println(d)
    }
}

private fun testFind() {
    // 第一个匹配的MatcherMatchResult对象
    val re = Regex("[0-9]+")
    val value = re.find("123XYZ99abc888")?.value
    println(value)

    // 所有匹配值的MatchResult的序列
    re.findAll("123XYZ99abc888").forEach {
        println(it.value)
    }
}

private fun testReplace() {
    val re = Regex("[0-9]+")
    var replace = re.replace("1234XYZ", "abc")
    println(replace)

    replace = re.replace("9XYZ8", { (it.value.toInt() * it.value.toInt()).toString() })
    println(replace)

}

private fun testMatchEntire() {
    // 如果输入字符串全部匹配正则表达式则返回一个MatcherMatchResult对象， 否则返回null
    val re = Regex("[0-9]+")
    println(re.matchEntire("12345")?.value)// 安全调用符号
    println(re.matchEntire("12345!"))
}

private fun testContainsMatchIn() {
    // 至少有一个匹配
    val re = Regex("[0-9]+")
    println(re.containsMatchIn("012Abc"))
    println(re.containsMatchIn("Abc"))
}

private fun testMatches() {
    // 全部匹配正则表达式
    val r1 = Regex("[a-z]+")
    println(r1.matches("ABCzxc"))

    val r2 = Regex("[a-z]+", RegexOption.IGNORE_CASE)
    println(r2.matches("ABCzxc"))

    val r3 = "[A-Z]+".toRegex()
    println(r3.matches("GGMM"))
}

private fun testCreate() {
    val r1 = Regex("[a-z]+")
    val r2 = Regex("[a-z]+", RegexOption.IGNORE_CASE)

    val r3 = "[A-Z]+".toRegex()
}