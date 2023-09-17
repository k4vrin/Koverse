package dev.kavrin.koverse.data.remote.dto

import dev.kavrin.koverse.domain.model.entity.Message
import kotlinx.serialization.Serializable

@Serializable
expect class MessageDto {
    val text: String
    val userName: String
    val timestamp: Long
    val id: String


    fun toMessage(): Message
}