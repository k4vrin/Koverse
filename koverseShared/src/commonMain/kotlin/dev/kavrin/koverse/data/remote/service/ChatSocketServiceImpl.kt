package dev.kavrin.koverse.data.remote.service

import dev.kavrin.koverse.data.remote.dto.MessageDto
import dev.kavrin.koverse.domain.model.entity.Message
import dev.kavrin.koverse.domain.model.error.SendMessageError
import dev.kavrin.koverse.domain.model.error.StartChatSessionError
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

    override suspend fun initSession(username: String): Resource<Unit, StartChatSessionError> {
        return try {
            socket = client.webSocketSession {
                val urlWithParam = "${ChatSocketService.EndPoints.ChatSocket.url}?username=$username"
                url(urlWithParam)
            }
            if (socket?.isActive == true) Resource.Success(Unit)
            else Resource.Error(cause = StartChatSessionError.Unknown, message = "Couldn't establish connection")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(cause = StartChatSessionError.Unknown, message = e.message)
        }
    }

    override suspend fun sendMessage(message: String): Resource<Unit, SendMessageError> {
        return try {
            socket?.send(Frame.Text(message))
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(cause = SendMessageError.Unknown, message = e.message)
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