package dev.kavrin.koverse.data.remote.dto

import dev.kavrin.koverse.domain.model.Message
import kotlinx.serialization.Serializable
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

@Serializable
actual data class MessageDto(
    actual val text: String,
    actual val userName: String,
    actual val timestamp: Long,
    actual val id: String
) {
    actual fun toMessage(): Message {
        val date = NSDate(timeIntervalSinceReferenceDate = timestamp.toDouble())
        val formatter = NSDateFormatter()
        formatter.dateFormat = "MMM d, h:mm a"
        val formattedDate = formatter.stringFromDate(date)

        return Message(
            text = text,
            username = userName,
            formattedTime = formattedDate
        )
    }
}