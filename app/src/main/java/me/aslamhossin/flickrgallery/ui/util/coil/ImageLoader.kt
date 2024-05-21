package me.aslamhossin.flickrgallery.ui.util.coil

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.util.DebugLogger
import me.aslamhossin.flickrgallery.R
import okhttp3.OkHttpClient

@Composable
fun rememberCoilPainter(
    data: Any,
    context: Context,
    placeholder: Painter? = null,
    error: Painter? = null,
): Painter {
    val imageLoader = ImageLoader.Builder(context)
        .crossfade(true)
        .diskCache(
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        )
        .okHttpClient { OkHttpClient.Builder().build() }
        .logger(DebugLogger())
        .build()

    return rememberAsyncImagePainter(
        model = data,
        imageLoader = imageLoader,
        placeholder = placeholder,
        error = error
    )
}


@Composable
fun CoilImage(imageUrl: String, modifier: Modifier) {
    val context = LocalContext.current

    Image(
        painter = rememberCoilPainter(
            data = imageUrl,
            context = context,
            error = painterResource(R.drawable.ic_image_loading_failed)
        ),
        contentDescription = null,
        modifier =modifier,
        contentScale = ContentScale.Crop
    )
}