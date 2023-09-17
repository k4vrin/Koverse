package dev.kavrin.koverse.data.remote.dto

import dev.kavrin.koverse.domain.model.entity.Message
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.text.DateFormat
import java.util.Date

@Serializable
actual data class MessageDto(
    actual val text: String,
    actual val userName: String,
    actual val timestamp: Long,
    @BsonId
    actual val id: String = ObjectId().toString()
) {
    actual fun toMessage(): Message {
        val date = Date(timestamp)
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return Message(
            text = text,
            username = userName,
            formattedTime = formattedDate
        )
    }
}