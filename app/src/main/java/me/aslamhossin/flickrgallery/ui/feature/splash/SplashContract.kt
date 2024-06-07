package me.aslamhossin.flickrgallery.ui.feature.splash

data class SplashState(
    val isSplashTimeout: Boolean = false
)

sealed interface SplashAction {
    data object NavigateHome : SplashAction
}
