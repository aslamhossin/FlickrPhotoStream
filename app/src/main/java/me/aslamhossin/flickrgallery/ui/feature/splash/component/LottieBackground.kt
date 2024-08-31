package me.aslamhossin.flickrgallery.ui.feature.splash.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import me.aslamhossin.flickrgallery.R

@Preview
@Composable
fun PreviewLottieBackground() {
    LottieBackground()
}

@Composable
fun LottieBackground() {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_screen))
    val progress by animateLottieCompositionAsState(composition = composition)

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .fillMaxSize()
    )

}
