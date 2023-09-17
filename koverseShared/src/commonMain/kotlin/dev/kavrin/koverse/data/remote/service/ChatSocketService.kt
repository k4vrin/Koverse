package dev.kavrin.koverse.data.remote.service

import dev.kavrin.koverse.domain.model.entity.Message
import dev.kavrin.koverse.domain.model.error.SendMessageError
import dev.kavrin.koverse.domain.model.error.StartChatSessionError
import dev.kavrin.koverse.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(username: String): Resource<Unit, StartChatSessionError>
    suspend fun sendMessage(message: String) : Resource<Unit, SendMessageError>

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()

    companion object {
        // Emulator
        const val BASE_URL = "ws://10.0.2.2:8080"
        // Real phone
//        Ipconfig getifaddr en0                                                    ─╯
//        192.168.1.7
//        const val BASE_URL = "ws://192.168.1.7:8080"
    }

    sealed class EndPoints(val url: String) {
        data object ChatSocket : EndPoints("$BASE_URL/chat-socket")
    }
}