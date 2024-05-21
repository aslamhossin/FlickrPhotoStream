package me.aslamhossin.flickrgallery.ui.feature.deatils

import me.aslamhossin.domain.repository.file.DownloadStatus
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo

sealed class PhotoDetailIntent {
    data class SavePhoto(val photo: Photo) : PhotoDetailIntent()
}

sealed class PhotoDetailEffect{
    data class ShowMessage(val downloadStatus: DownloadStatus) : PhotoDetailEffect()
}