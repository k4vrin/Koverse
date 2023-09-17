package dev.kavrin.koverse.data.db

import dev.kavrin.koverse.data.remote.dto.MessageDto

interface MessageDataSource {
    suspend fun getAllMessages(): List<MessageDto>
    suspend fun insertMessage(messageDto: MessageDto)
}