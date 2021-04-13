package com.wolf.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServerOptions
import io.vertx.kotlin.core.http.httpServerOptionsOf
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import java.util.concurrent.TimeUnit

public class MyVerticle : AbstractVerticle() {
    // Called when verticle is deployed
    override fun start() {
        val classic: HttpServerOptions = HttpServerOptions()
            .setIdleTimeout(5)
            .setIdleTimeoutUnit(TimeUnit.MINUTES)
            .setLogActivity(true)

        val options = httpServerOptionsOf(idleTimeout = 5, idleTimeoutUnit = TimeUnit.MINUTES, logActivity = true)

        // The json builder declares a JSON structure
        val result = json {

            "firstName" to "Dale"
            "lastName" to "Cooper"
            "age" to 64

            // in this structure the obj function can be used

            // takes a vararg of Kotlin pairs
            obj(
                "key_1" to 1,
                "key_2" to 2,
                "key_3" to 3
            )

            // or an iterable of Kotlin pairs
            obj((1..3).map { "key_$it" to it })

            // or a Map<String, Any?>
            obj(mapOf())

            // apply function on object receiver
            obj {
                for (i in 1..3) {
                    put("key_$i", i)
                }
            }

            // in this structure the array function can be used

            // takes a vararg of values
            array("1", "2", "3")

            // or an iterable of values
            array((1..3).map { "$it" })

            // apply function on array receiver
            array {
                for (i in 1..3) {
                    add("$i")
                }
            }
        }

        println(result)
//        println(result["firstName"])
//        println(result["4"])

        super.start()
    }

    // Optional - called when verticle is undeployed
    override fun stop() {
        super.stop()
    }

}