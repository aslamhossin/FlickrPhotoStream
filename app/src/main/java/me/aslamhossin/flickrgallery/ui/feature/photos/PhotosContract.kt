package me.aslamhossin.flickrgallery.ui.feature.photos

import me.aslamhossin.core.error.ErrorModel
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo


data class PhotosViewState(
    val isLoading: Boolean = false,
    val photos: List<Photo>? = null,
    val error: ErrorModel? = null,
)


sealed class PhotoViewIntent {
    data class Search(val query: String) : PhotoViewIntent()
}