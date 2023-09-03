package dev.kavrin.koverse.data.db

import dev.kavrin.koverse.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class MongoMessageDataSource(
    db: CoroutineDatabase
): MessageDataSource {

    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages.find()
            .descendingSort(Message::timestamp)
            .toList()
    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }
}