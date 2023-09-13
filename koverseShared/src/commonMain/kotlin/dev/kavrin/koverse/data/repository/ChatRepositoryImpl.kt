package dev.kavrin.koverse.data.repository

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.kavrin.koverse.data.remote.service.ChatSocketService
import dev.kavrin.koverse.data.remote.service.MessageService
import dev.kavrin.koverse.domain.model.error.GetAllMessagesError
import dev.kavrin.koverse.domain.model.entity.Message
import dev.kavrin.koverse.domain.model.error.SendMessageError
import dev.kavrin.koverse.domain.model.error.StartChatSessionError
import dev.kavrin.koverse.domain.repository.ChatRepository
import dev.kavrin.koverse.domain.util.ApiResponse
import dev.kavrin.koverse.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class ChatRepositoryImpl(
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
) : ChatRepository {

    @NativeCoroutines
    override suspend fun getAllMessages(): Resource<List<Message>, GetAllMessagesError> {
        return when (val res = messageService.getAllMessages()) {
            is ApiResponse.Error.HttpClientError -> Resource.Error(cause = GetAllMessagesError.AppError, message = res.errorBody)
            is ApiResponse.Error.HttpServerError -> Resource.Error(cause = GetAllMessagesError.ServerError, message = res.errorBody)
            ApiResponse.Error.NetworkError -> Resource.Error(cause = GetAllMessagesError.BadInternet)
            ApiResponse.Error.SerializationError -> Resource.Error(cause = GetAllMessagesError.AppError, message = "Serialization Error")
            is ApiResponse.Success -> Resource.Success(data = res.body.map { it.toMessage() })
        }
    }

    @NativeCoroutines
    override suspend fun startSession(username: String): Resource<Unit, StartChatSessionError> {
        return chatSocketService.initSession(username)
    }

    @NativeCoroutines
    override suspend fun closeSession(): Resource<Unit, Unit> {
        return chatSocketService.closeSession().let { Resource.Success(it) }
    }

    @NativeCoroutines
    override suspend fun sendMessage(message: String): Resource<Unit, SendMessageError> {
        return chatSocketService.sendMessage(message)
    }

    @NativeCoroutines
    override fun observeMessages(): Flow<Message> = chatSocketService.observeMessages()
}