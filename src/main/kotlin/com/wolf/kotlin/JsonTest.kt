package com.wolf.kotlin

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val JSON_MAPPER: ObjectMapper = ObjectMapper().apply {
    registerKotlinModule()
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}
private val PRETTY_MAPPER: ObjectMapper = ObjectMapper().apply {
    configure(SerializationFeature.INDENT_OUTPUT, true)
    registerKotlinModule()
    configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

fun toJson(data: Any?, pretty: Boolean = false): String =
    if (pretty) PRETTY_MAPPER.writeValueAsString(data) else JSON_MAPPER.writeValueAsString(data)


inline fun <reified T : Any> fromJson(json: String): T = JSON_MAPPER.readValue(json)

class JsonWrapper(val data: Any?, val pretty: Boolean = false) {
    override fun toString(): String =
        if (pretty) {
            PRETTY_MAPPER.writeValueAsString(data)
        } else {
            JSON_MAPPER.writeValueAsString(data)
        }
}

data class Param1(var a: Int, var b: String)

fun main() {
    val p = Param1(1, "xxx")
    val message = toJson(p)
    println(message)

    // 若是针对String的类型，但是json中没有则报错
    val json = """
        {"a":1,"b":"xxx"}
    """.trimIndent()
    val pp: Param1 = fromJson(json)
    println(pp)
}