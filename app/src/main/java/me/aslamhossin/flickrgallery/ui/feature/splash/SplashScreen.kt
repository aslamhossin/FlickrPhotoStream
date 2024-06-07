package me.aslamhossin.flickrgallery.ui.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import me.aslamhossin.flickrgallery.ui.feature.splash.component.LottieBackground
import me.aslamhossin.flickrgallery.ui.navigation.SCREEN_PHOTOS

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isSplashTimeout) {
        navHostController.navigate(SCREEN_PHOTOS)
    } else {
        LottieBackground()
    }
}
