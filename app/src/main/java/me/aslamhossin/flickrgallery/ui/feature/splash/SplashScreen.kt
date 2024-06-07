package me.aslamhossin.flickrgallery.ui.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import me.aslamhossin.flickrgallery.ui.feature.splash.component.LottieBackground

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isSplashTimeout) {

    } else {
        LottieBackground()
    }
}
