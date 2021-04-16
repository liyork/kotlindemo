package com.wolf.kotlin.shizhan

interface ProjectService {
    val name: String
    val owner: String
    fun print1()
    fun print() {
        println("i am project")
    }
}

interface MilestoneService {
    fun print() {
        println("i am project")
    }
}

// 实现接口,接口没有构造函数，所以不用()
class ProjectServiceImple(override val name: String, override val owner: String) : ProjectService, MilestoneService {
    override fun print1() {
    }

    // 由于两个接口都有print方法，所以需要重写实现
    override fun print() {
        // 对不同接口的print调用
        super<ProjectService>.print()
        super<MilestoneService>.print()
    }
}