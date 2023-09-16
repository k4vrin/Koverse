package dev.kavrin.koverse.android.presentation.screens.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kavrin.koverse.android.presentation.screens.navArgs
import dev.kavrin.koverse.domain.repository.ChatRepository
import dev.kavrin.koverse.domain.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), ChatContract {



    private val _state = MutableStateFlow(ChatContract.State())
    override val state: StateFlow<ChatContract.State> = _state.asStateFlow()

    private val _messageState = MutableStateFlow("")
    val messageState = _messageState.asStateFlow()

    private val effectFlow = MutableSharedFlow<ChatContract.Effect>()
    override val effect: SharedFlow<ChatContract.Effect> = effectFlow.asSharedFlow()

    override fun onEvent(event: ChatContract.Event) {
        when (event) {
            is ChatContract.Event.MessageChange -> _messageState.update { event.message }
            ChatContract.Event.SendMessage -> sendMessage()
            ChatContract.Event.ConnectToChat -> connectToChat()
            ChatContract.Event.StopChat -> disconnectChat()
        }
    }

    private fun disconnectChat() {
        viewModelScope.launch {
            chatRepository.closeSession()
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            _messageState.value.takeIf { it.isNotBlank() }?.let { message ->
                chatRepository.sendMessage(message)
            }
        }
    }

    private fun connectToChat() {
        getAllMessages()
        savedStateHandle.navArgs<ChatScreenNavArgs>().let {
            _state.update { mState -> mState.copy(username = it.username) }
            startChatSession(it.username)
        }
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            _state.update { mState -> mState.copy(isLoading = true) }
            chatRepository.getAllMessages().let { res ->
                when (res) {
                    is Resource.Error -> effectFlow.emit(ChatContract.Effect.ErrorGettingMessages(res.cause))
                    is Resource.Success -> _state.update { mState -> mState.copy(messages = res.data) }
                }
            }
        }.invokeOnCompletion { _state.update { mState -> mState.copy(isLoading = false) } }
    }

    private fun startChatSession(username: String) {
        viewModelScope.launch {
            chatRepository.startSession(username).let { res ->
                when (res) {
                    is Resource.Error -> effectFlow.emit(ChatContract.Effect.ErrorStartChatSession(res.cause))
                    is Resource.Success -> observeMessages()
                }
            }
        }
    }

    private fun observeMessages() {
        chatRepository
            .observeMessages()
            .onEach { message ->
                _state.update { mState ->
                    mState.copy(
                        messages = mState.messages.toMutableList().apply {
                            add(0, message)
                        }
                    )
                }
            }
            .launchIn(viewModelScope)

    }

    override fun onCleared() {
        super.onCleared()
        disconnectChat()
    }
}