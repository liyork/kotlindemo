package com.wolf.vertx

import io.vertx.kotlin.mqtt.mqttClientOptionsOf

fun main() {
    val mqttClientOptionsOf = mqttClientOptionsOf()
    mqttClientOptionsOf.reconnectAttempts
}