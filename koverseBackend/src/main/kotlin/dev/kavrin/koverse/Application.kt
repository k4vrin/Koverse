package dev.kavrin.koverse

import dev.kavrin.koverse.di.mainModule
import dev.kavrin.koverse.plugins.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureSockets()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}

private fun Application.configureKoin() {
    install(Koin) {
        modules(mainModule)
    }
}