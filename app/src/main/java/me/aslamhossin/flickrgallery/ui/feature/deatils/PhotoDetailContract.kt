package me.aslamhossin.flickrgallery.ui.feature.deatils

import me.aslamhossin.domain.repository.file.DownloadStatus

sealed class PhotoDetailIntent {
    data class SavePhoto(val fileName:String, val url:String) : PhotoDetailIntent()
}

sealed class PhotoDetailEffect{
    data class ShowMessage(val downloadStatus: DownloadStatus) : PhotoDetailEffect()
}