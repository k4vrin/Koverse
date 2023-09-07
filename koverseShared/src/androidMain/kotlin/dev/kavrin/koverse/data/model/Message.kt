package dev.kavrin.koverse.data.model

import kotlinx.serialization.Serializable

@Serializable
actual data class Message(
    actual val text: String,
    actual val userName: String,
    actual val timestamp: Long,
    actual val id: String
)