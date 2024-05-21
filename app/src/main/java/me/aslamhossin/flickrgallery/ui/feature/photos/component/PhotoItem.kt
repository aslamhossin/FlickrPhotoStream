package me.aslamhossin.flickrgallery.ui.feature.photos.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.aslamhossin.flickrgallery.R
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Media
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo
import me.aslamhossin.flickrgallery.ui.theme.AllmTestTheme
import me.aslamhossin.flickrgallery.ui.util.coil.CoilImage

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    photo: Photo,
    onClick: () -> Unit = {},
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            CoilImage(
                imageUrl = photo.media.url,
                modifier = Modifier.height(160.dp).fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = photo.tags,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.by, photo.author),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = photo.dateTaken,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun PhotoItemPreview() {
    AllmTestTheme {
        PhotoItem(
            modifier = Modifier.padding(8.dp),
            Photo(
                "Photo item",
                "https://developer.android.com/static/codelabs/jetpack-compose-layouts/img/layouts11_1920.png",
                Media(""),
                "22,12,2024",
                "",
                "",
                "Aslam",
                "",
                ""
            )
        )
    }
}