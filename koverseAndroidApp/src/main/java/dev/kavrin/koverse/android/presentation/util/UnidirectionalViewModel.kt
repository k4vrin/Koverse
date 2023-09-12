package dev.kavrin.koverse.android.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UnidirectionalViewModel<STATE, EVENT, EFFECT> {
    val state: StateFlow<STATE>
    val effect: SharedFlow<EFFECT>
    fun onEvent(event: EVENT)
}

data class StateEffectDispatch<STATE, EVENT, EFFECT>(
    val state: STATE,
    val effect: Flow<EFFECT>,
    val dispatch: (EVENT) -> Unit
)

@Composable
inline fun <reified STATE, EFFECT, EVENT> use(
    viewModel: UnidirectionalViewModel<STATE, EVENT, EFFECT>
): StateEffectDispatch<STATE, EVENT, EFFECT> {

    val state: STATE by viewModel.state.collectAsStateWithLifecycle()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.onEvent(event)
    }

    return StateEffectDispatch(
        state = state,
        effect = viewModel.effect,
        dispatch = dispatch
    )
}