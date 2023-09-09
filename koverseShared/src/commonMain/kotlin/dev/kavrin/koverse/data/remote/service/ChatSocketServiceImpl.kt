package dev.kavrin.koverse.data.remote.service

import dev.kavrin.koverse.domain.model.Message
import dev.kavrin.koverse.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive

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
        TODO("Not yet implemented")
    }

    override suspend fun closeSession() {
        TODO("Not yet implemented")
    }
}