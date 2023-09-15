package dev.kavrin.koverse.android.presentation.screens.username

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.kavrin.koverse.android.presentation.screens.destinations.ChatScreenDestination
import dev.kavrin.koverse.android.presentation.util.collectInLaunchedEffect
import dev.kavrin.koverse.android.presentation.util.use
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun UsernameScreen(
    viewModel: UsernameViewModel = koinViewModel(),
    navigator: DestinationsNavigator,
) {

    val (state, event, effectFlow) = use(viewModel = viewModel)
    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is UsernameContract.Effect.JoinChat -> navigator.navigate(ChatScreenDestination(effect.username))
        }
    }


    UsernameContent(state = state, event = event)
}