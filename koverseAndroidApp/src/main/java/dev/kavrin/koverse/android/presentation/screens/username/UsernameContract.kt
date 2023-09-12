package dev.kavrin.koverse.android.presentation.screens.username

import dev.kavrin.koverse.android.presentation.util.UnidirectionalViewModel

interface UsernameContract :
    UnidirectionalViewModel<UsernameContract.State, UsernameContract.Event, UsernameContract.Effect> {

    data class State(
        val usernameText: String = "",
    )

    sealed interface Event {
        data class UsernameChange(val str: String) : Event
        data object JoinClicked : Event
    }

    sealed interface Effect {
        data class JoinChat(val username: String) : Effect
    }
}