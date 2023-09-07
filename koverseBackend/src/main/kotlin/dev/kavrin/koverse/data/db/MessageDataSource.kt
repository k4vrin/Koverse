package dev.kavrin.koverse.data.db

import dev.kavrin.koverse.data.model.Message

interface MessageDataSource {
    suspend fun getAllMessages(): List<Message>
    suspend fun insertMessage(message: Message)
}