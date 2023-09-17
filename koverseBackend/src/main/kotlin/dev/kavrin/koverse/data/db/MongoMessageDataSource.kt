package dev.kavrin.koverse.data.db

import dev.kavrin.koverse.data.remote.dto.MessageDto
import org.litote.kmongo.coroutine.CoroutineDatabase

class MongoMessageDataSource(
    db: CoroutineDatabase
): MessageDataSource {

    private val messages = db.getCollection<MessageDto>()

    override suspend fun getAllMessages(): List<MessageDto> {
        return messages.find()
            .descendingSort(MessageDto::timestamp)
            .toList()
    }

    override suspend fun insertMessage(messageDto: MessageDto) {
        messages.insertOne(messageDto)
    }
}