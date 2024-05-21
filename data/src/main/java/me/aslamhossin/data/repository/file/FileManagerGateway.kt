package me.aslamhossin.data.repository.file

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import me.aslamhossin.domain.repository.file.FileType
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages file operations such as creating directories and syncing files with the gallery.
 */
@Singleton
class FileManagerGateway @Inject constructor(@ApplicationContext private val context: Context) {

    /**
     * Gets the download directory based on the file type.
     *
     * @param fileType The type of the file (Image, Video, Pdf).
     * @return The download directory path as a string.
     */
    fun getDownloadDirectory(fileType: FileType): String {
        return when (fileType) {
            FileType.Image -> Environment.DIRECTORY_PICTURES
            FileType.Video -> Environment.DIRECTORY_MOVIES
            FileType.Pdf -> Environment.DIRECTORY_DOWNLOADS
        }
    }

    /**
     * Creates an output file in the specified download directory.
     *
     * @param downloadDirectory The directory where the file will be saved.
     * @param fileName The name of the file to be created.
     * @return The created File object.
     */
    fun createOutputFile(downloadDirectory: String, fileName: String): File {
        val directory = File(context.getExternalFilesDir(downloadDirectory), "")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return File(directory, fileName)
    }

    /**
     * Syncs the file with the gallery so that it is visible in the device's media applications.
     *
     * @param file The file to sync with the gallery.
     */
    fun syncWithGallery(file: File) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val uri =
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            uri?.let {
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    file.inputStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
        } else {
            val provider = "${context.packageName}.provider"
            val contentUri = FileProvider.getUriForFile(context, provider, file)
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri)
            context.sendBroadcast(intent)
        }
    }

    fun fileExists(file: File) = file.exists()

}
