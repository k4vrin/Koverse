package dev.kavrin.koverse.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
actual data class Message(
    actual val text: String,
    actual val userName: String,
    actual val timestamp: Long,
    @BsonId
    actual val id: String = ObjectId().toString()
)