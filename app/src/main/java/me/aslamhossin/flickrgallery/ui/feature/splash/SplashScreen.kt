package me.aslamhossin.flickrgallery.ui.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import me.aslamhossin.flickrgallery.ui.feature.splash.component.LottieBackground
import me.aslamhossin.flickrgallery.ui.navigation.SCREEN_PHOTOS
import me.aslamhossin.flickrgallery.ui.navigation.SPLASH

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSplashTimeout) {
        if (state.isSplashTimeout) {
            navHostController.navigate(SCREEN_PHOTOS) {
                popUpTo(SPLASH) { inclusive = true }
            }
        }
    }

    if (!state.isSplashTimeout) {
        LottieBackground()
    }
}
