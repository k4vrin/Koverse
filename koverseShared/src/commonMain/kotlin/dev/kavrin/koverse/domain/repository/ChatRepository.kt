package dev.kavrin.koverse.domain.repository

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.kavrin.koverse.domain.model.error.GetAllMessagesError
import dev.kavrin.koverse.domain.model.entity.Message
import dev.kavrin.koverse.domain.model.error.SendMessageError
import dev.kavrin.koverse.domain.model.error.StartChatSessionError
import dev.kavrin.koverse.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    @NativeCoroutines
    suspend fun getAllMessages(): Resource<List<Message>, GetAllMessagesError>
    @NativeCoroutines
    suspend fun startSession(username: String): Resource<Unit, StartChatSessionError>
    @NativeCoroutines
    suspend fun closeSession(): Resource<Unit, Unit>
    @NativeCoroutines
    suspend fun sendMessage(message: String): Resource<Unit, SendMessageError>
    @NativeCoroutines
    fun observeMessages(): Flow<Message>
}