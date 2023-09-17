package dev.kavrin.koverse.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dev.kavrin.koverse.android.presentation.screens.NavGraphs
import dev.kavrin.koverse.android.presentation.theme.KoverseTheme
import dev.kavrin.koverse.android.presentation.theme.TransitionAnimation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val engine = rememberAnimatedNavHostEngine(
                rootDefaultAnimations = TransitionAnimation.default
            )
            val navController = engine.rememberNavController()
            KoverseTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    engine = engine,
                    navController = navController
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    KoverseTheme {
    }
}
