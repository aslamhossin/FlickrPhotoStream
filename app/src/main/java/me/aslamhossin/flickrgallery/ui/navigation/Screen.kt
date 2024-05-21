package me.aslamhossin.flickrgallery.ui.navigation

const val SCREEN_PHOTO_DETAIL = "photo_detail"
const val SCREEN_PHOTOS = "photos"

const val ARG_PHOTO = "photo"

sealed class Screen(val route: String) {
    data object Photos : Screen(SCREEN_PHOTOS)
    data object PhotoDetail : Screen(SCREEN_PHOTO_DETAIL)

}