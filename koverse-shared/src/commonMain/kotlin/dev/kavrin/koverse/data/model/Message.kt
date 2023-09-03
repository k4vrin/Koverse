package dev.kavrin.koverse.data.model

import kotlinx.serialization.Serializable

@Serializable
expect class Message {
    val text: String
    val userName: String
    val timestamp: Long
    val id: String
}
