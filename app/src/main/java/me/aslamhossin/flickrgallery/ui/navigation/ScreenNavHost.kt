package me.aslamhossin.flickrgallery.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.aslamhossin.flickrgallery.ui.feature.deatils.PhotoDetailScreen
import me.aslamhossin.flickrgallery.ui.feature.photos.PhotosScreen
import me.aslamhossin.flickrgallery.ui.feature.splash.SplashScreen
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo

@Composable
fun ScreenNavHost(navHostController: NavHostController) {

    NavHost(
        navHostController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navHostController)
        }

        composable(Screen.Photos.route) {
            PhotosScreen(navHostController)
        }

        composable(Screen.PhotoDetail.route) {
            val photo = navHostController.previousBackStackEntry?.savedStateHandle?.get<Photo>(ARG_PHOTO)
            if (photo != null) PhotoDetailScreen( photo,navHostController)
        }
    }

}
