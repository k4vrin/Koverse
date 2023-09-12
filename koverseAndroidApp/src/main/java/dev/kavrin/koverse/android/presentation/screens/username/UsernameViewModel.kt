package dev.kavrin.koverse.android.presentation.screens.username

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class UsernameViewModel(

) : ViewModel(), UsernameContract {

    private val _state = MutableStateFlow(UsernameContract.State())
    override val state: StateFlow<UsernameContract.State> = _state.asStateFlow()

    val a = Channel<UsernameContract.Effect>()

    private val effectChannel = MutableSharedFlow<UsernameContract.Effect>()
    override val effect: SharedFlow<UsernameContract.Effect> = effectChannel.asSharedFlow()

    override fun onEvent(event: UsernameContract.Event) {
        when (event) {
            UsernameContract.Event.JoinClicked -> joinChat()
            is UsernameContract.Event.UsernameChange -> _state.update { mState ->
                mState.copy(
                    usernameText = event.str
                )
            }
        }
    }

    private fun joinChat() {
        viewModelScope.launch {
            _state.value.usernameText.takeIf { it.isNotBlank() }?.let { username ->
                effectChannel.emit(UsernameContract.Effect.JoinChat(username))
            }
        }
    }
}