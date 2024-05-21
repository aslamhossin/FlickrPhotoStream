package me.aslamhossin.data.repository.file

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.aslamhossin.core.coroutines.IoDispatcher
import me.aslamhossin.domain.repository.file.DownloadStatus
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages file downloads using Android's DownloadManager.
 */
@Singleton
class DownloadManagerGateway @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    /**
     * Starts a download request.
     *
     * @param url The URL of the file to download.
     * @param outputFile The file where the downloaded content will be saved.
     * @return The ID of the download.
     */
    fun enqueueDownloadRequest(url: String, outputFile: File): Long {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setDestinationUri(Uri.fromFile(outputFile))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        return downloadManager.enqueue(request)
    }

    /**
     * Monitors the progress of a download.
     *
     * @param downloadId The ID of the download to monitor.
     * @param file The file being downloaded.
     * @return A Flow emitting the download status updates.
     */
    fun monitorDownloadProgress(downloadId: Long, file: File): Flow<DownloadStatus> = flow {
        try {
            while (true) {
                val downloadManager =
                    context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val progress = getDownloadProgress(downloadManager, downloadId)
                if (progress < 100) {
                    emit(DownloadStatus.InProgress(progress))
                } else {
                    emit(DownloadStatus.Completed(file.absolutePath))
                    break
                }
                delay(1000)
            }
        } catch (e: Exception) {
            emit(DownloadStatus.Failed.DownloadFailed("Download failed"))
        }
    }.flowOn(ioDispatcher)

    /**
     * Gets the download progress.
     *
     * @param downloadManager The DownloadManager instance.
     * @param downloadId The ID of the download.
     * @return The download progress as a percentage.
     */
    private fun getDownloadProgress(downloadManager: DownloadManager, downloadId: Long): Int {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor: Cursor = downloadManager.query(query)
        return if (cursor.moveToFirst()) {
            val totalSize =
                cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            val downloadedSize =
                cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            cursor.close()
            if (totalSize > 0) {
                (downloadedSize * 100L / totalSize).toInt()
            } else 0
        } else {
            cursor.close()
            0
        }
    }
}
