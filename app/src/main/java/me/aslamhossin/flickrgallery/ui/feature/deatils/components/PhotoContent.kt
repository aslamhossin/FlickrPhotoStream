package me.aslamhossin.flickrgallery.ui.feature.deatils.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.aslamhossin.flickrgallery.R
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo


@Composable
fun PhotoContent(photo: Photo) {
    AsyncImage(
        model = photo.media.url,
        contentDescription = stringResource(R.string.photo_image),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.taken_on, photo.dateTaken),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray
    )
    Text(
        text = stringResource(R.string.published, photo.published),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(8.dp))
    HtmlWebView(photo.description)
    Spacer(modifier = Modifier.height(8.dp))
    ClickableText(
        text = buildAnnotatedString {
            append(stringResource(R.string.author, photo.author))
            addStyle(
                style = SpanStyle(color = Color.Blue),
                start = 8,
                end = 8 + photo.author.length
            )
        },
        onClick = { offset ->

        }
    )
}
