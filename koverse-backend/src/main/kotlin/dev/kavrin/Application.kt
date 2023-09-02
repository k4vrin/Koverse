package dev.kavrin

import dev.kavrin.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureSockets()
    configureSecurity()
    configureRouting()
}
