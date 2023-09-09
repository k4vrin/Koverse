package dev.kavrin.koverse.domain.repository

import dev.kavrin.koverse.domain.model.GetAllMessagesError
import dev.kavrin.koverse.domain.model.Message
import dev.kavrin.koverse.domain.util.Resource

interface ChatRepository {
    suspend fun getAllMessages(): Resource<List<Message>, GetAllMessagesError>
}