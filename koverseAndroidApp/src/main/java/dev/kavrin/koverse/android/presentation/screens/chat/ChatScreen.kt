package dev.kavrin.koverse.android.presentation.screens.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.radusalagean.infobarcompose.InfoBar
import com.radusalagean.infobarcompose.InfoBarMessage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import dev.kavrin.koverse.android.presentation.util.collectInLaunchedEffect
import dev.kavrin.koverse.android.presentation.util.use
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination(navArgsDelegate = ChatScreenNavArgs::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel(),
) {

    val (state, event, effectFlow) = use(viewModel = viewModel)

    var infoBarMessage: InfoBarMessage? by remember { mutableStateOf(null) }


    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is ChatContract.Effect.ErrorGettingMessages -> infoBarMessage = InfoBarMessage(text = "There has been error loading messages!")
            is ChatContract.Effect.ErrorStartChatSession -> infoBarMessage = InfoBarMessage(text = "Error connecting to chat server")
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) event(ChatContract.Event.ConnectToChat)
            else if (event == Lifecycle.Event.ON_STOP) event(ChatContract.Event.StopChat)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        ChatContent(
            state = state,
            event = event,
            messageState = viewModel.messageState.collectAsStateWithLifecycle().value
        )
        InfoBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            offeredMessage = infoBarMessage
        ) {
            infoBarMessage = null
        }
    }

}