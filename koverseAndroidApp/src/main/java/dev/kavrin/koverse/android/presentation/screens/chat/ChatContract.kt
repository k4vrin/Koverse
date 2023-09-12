package dev.kavrin.koverse.android.presentation.screens.chat

import dev.kavrin.koverse.android.presentation.util.UnidirectionalViewModel
import dev.kavrin.koverse.domain.model.error.GetAllMessagesError
import dev.kavrin.koverse.domain.model.entity.Message
import dev.kavrin.koverse.domain.model.error.StartChatSessionError

interface ChatContract : UnidirectionalViewModel<ChatContract.State, ChatContract.Event, ChatContract.Effect> {

    data class State(
        val messages: List<Message> = emptyList(),
        val isLoading: Boolean = false,
        val username: String? = null
    )

    sealed interface Event {
        data class MessageChange(val message: String) : Event
        data object SendMessage: Event
    }

    sealed interface Effect {
        data class ErrorGettingMessages(val cause: GetAllMessagesError) : Effect
        data class ErrorStartChatSession(val cause: StartChatSessionError) : Effect
    }
}