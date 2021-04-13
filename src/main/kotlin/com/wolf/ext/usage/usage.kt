package ext.usage

// 使用定义在本包之外的一个扩展，通过import导入扩展的函数名进行使用
import ext.bar.goo

class Baz {
}

fun usage(baz: Baz) {
    baz.goo()
}