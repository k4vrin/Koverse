package dev.kavrin.koverse.data.repository

import dev.kavrin.koverse.data.remote.service.MessageService
import dev.kavrin.koverse.domain.model.GetAllMessagesError
import dev.kavrin.koverse.domain.model.Message
import dev.kavrin.koverse.domain.repository.ChatRepository
import dev.kavrin.koverse.domain.util.ApiResponse
import dev.kavrin.koverse.domain.util.Resource

class ChatRepositoryImpl(
    private val messageService: MessageService
) : ChatRepository {

    override suspend fun getAllMessages(): Resource<List<Message>, GetAllMessagesError> {
        return when (val res = messageService.getAllMessages()) {
            is ApiResponse.Error.HttpError -> Resource.Error(cause = GetAllMessagesError.ServerError, message = res.errorBody)
            ApiResponse.Error.NetworkError -> Resource.Error(cause = GetAllMessagesError.BadInternet)
            ApiResponse.Error.SerializationError -> Resource.Error(cause = GetAllMessagesError.AppError, message = "Serialization Error")
            is ApiResponse.Success -> Resource.Success(data = res.body.map { it.toMessage() })
        }
    }
}