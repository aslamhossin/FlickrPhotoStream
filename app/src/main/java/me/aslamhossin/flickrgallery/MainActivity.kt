package me.aslamhossin.flickrgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.aslamhossin.flickrgallery.ui.navigation.ScreenNavHost
import me.aslamhossin.flickrgallery.ui.theme.AllmTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AllmTestTheme {
                ScreenNavHost(rememberNavController())
            }
        }
    }
}