package dev.kavrin.koverse.plugins

import dev.kavrin.koverse.room.RoomController
import dev.kavrin.koverse.routes.chatSocket
import dev.kavrin.koverse.routes.getAllMessages
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>()
    routing {
        get("/") {
            call.respondText("Welcome to Koverse!")
        }
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}
