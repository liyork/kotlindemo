package hello

fun main() {
    // 1.1 函数类型，可变
    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

    // 1.2 简化格式，赋值给变量时不能完全省略类型声明，作为一个函数的参数时，可以省略
    val sumSimple1: (Int, Int) -> Int = { x, y -> x + y }
    val sumSimple2 = { x: Int, y: Int -> x + y }

    // 2. trailing lambda，函数的最后一个参数是一个函数类型，可将lambda表达式放到参数列表外
    // 2.1 非一个参数
    fun a3(x: Int, y: Int, lambda: (Int, Int) -> Int): Int {
        return lambda(x, y)
    }

    val b = a3(1, 2, { x, y -> x + y })
    val bOut = a3(1, 2) { x, y -> x + y }

    // 2.2 一个参数
    fun a1(lambda: (Int, Int) -> Int): Int {
        return lambda(1, 2)
    }

    val bOut2 = a1 { x, y -> x + y }

    // 3.it，lambda只有一个参数时，用it表示
    fun itFun(lambda: (Int) -> Unit) {
        lambda(1)
    }

    val itResult = itFun { println(it) }

    val it: (Int) -> Unit = { println(it) }
    it(1)

    // 4 lambda返回值
    val retLambda = {
        1 + 1
        3
    }
    val retVal = retLambda()

    // 5 无用变量处理，_表示占位
    val map = mapOf(1 to "a", 2 to "b")
    map.forEach { _, value -> print(value) }

    // 6 lambda内访问外部变量
    val ints = listOf(1, 2, 3)
    var sum1 = 0
    ints.filter { it > 0 }.forEach {
        sum1 += it
    }
    println(sum1)

    // 7 调用lambda
    val sum2 = { x: Int, y: Int -> x + y }
    sum2.invoke(1, 2)
    sum2(1, 2)
}