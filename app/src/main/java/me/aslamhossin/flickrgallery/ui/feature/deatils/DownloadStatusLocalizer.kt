package me.aslamhossin.flickrgallery.ui.feature.deatils

import android.content.Context
import me.aslamhossin.domain.repository.file.DownloadStatus
import me.aslamhossin.flickrgallery.R

/**
 * Extension function to get a localized message based on the current DownloadStatus.
 *
 * This function uses the provided context to access string resources and format messages
 * according to the current download status. It handles different download states such as
 * completion, failure, and progress, and returns appropriate localized messages.
 *
 * @param context The context used to access string resources.
 * @return A formatted localized message based on the current DownloadStatus.
 */
fun DownloadStatus.getLocaleMessage(context: Context): String {
    return when (this) {
        is DownloadStatus.Completed -> context.getString(
            R.string.download_completed,
            this.filePath ?: ""
        )

        is DownloadStatus.Failed.DownloadFailed -> context.getString(
            R.string.download_failed,
            this.errorMessage ?: ""
        )

        is DownloadStatus.InProgress -> {
            if (progress == 0) {
                context.getString(R.string.download_started)
            } else {
                context.getString(
                    R.string.download_in_progress,
                    this.progress
                )
            }
        }


        DownloadStatus.Failed.FileAlreadyExists -> context.getString(R.string.file_already_exists)
    }
}

