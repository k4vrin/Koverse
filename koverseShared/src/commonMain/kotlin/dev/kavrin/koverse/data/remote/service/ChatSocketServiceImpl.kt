package dev.kavrin.koverse.data.remote.service

import dev.kavrin.koverse.data.remote.dto.MessageDto
import dev.kavrin.koverse.domain.model.Message
import dev.kavrin.koverse.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val client: HttpClient
) : ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(username: String): Resource<Unit, Unit> {
        return try {
            socket = client.webSocketSession {
                url(ChatSocketService.EndPoints.ChatSocket.url)
            }
            if (socket?.isActive == true) Resource.Success(Unit)
            else Resource.Error(Unit, message = "Couldn't establish connection")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(cause = Unit, message = e.message)
        }
    }

    override suspend fun sendMessage(message: String): Resource<Unit, Unit> {
        return try {
            socket?.send(Frame.Text(message))
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(cause = Unit, message = e.message)
        }
    }

    override fun observeMessages(): Flow<Message> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDto = Json.decodeFromString<MessageDto>(json)
                    messageDto.toMessage()
                } ?: emptyFlow()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyFlow()
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}