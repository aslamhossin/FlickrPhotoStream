package me.aslamhossin.flickrgallery.ui.navigation

const val SPLASH = "splash"
const val HOME = "home"
const val SCREEN_PHOTO_DETAIL = "photo_detail"
const val SCREEN_PHOTOS = "photos"

const val ARG_PHOTO = "photo"

sealed class Screen(
    val route: String
) {

    data object Splash : Screen(SPLASH)
    data object Home : Screen(HOME)
    data object Photos : Screen(SCREEN_PHOTOS)
    data object PhotoDetail : Screen(SCREEN_PHOTO_DETAIL)

}
