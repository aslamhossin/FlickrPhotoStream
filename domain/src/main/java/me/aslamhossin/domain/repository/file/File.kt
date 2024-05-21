package me.aslamhossin.domain.repository.file

sealed class FileType {
    data object Image : FileType()
    data object Video : FileType()
    data object Pdf : FileType()

}

sealed class DownloadStatus {
    data class InProgress(val progress: Int) : DownloadStatus()
    data class Completed(val filePath: String? = null) : DownloadStatus()
    sealed class Failed : DownloadStatus() {
        data object FileAlreadyExists : Failed()
        data class DownloadFailed(val errorMessage: String? = "") : Failed()
    }

}

data class FileInput(
    val name: String,
    val url: String,
    val fileType: FileType = FileType.Image,
)

data class FileOutput(
    val name: String,
    val outputPath: String? = null,
    val fileType: FileType = FileType.Image,
    val downloadInfo: DownloadInfo,
)

data class DownloadInfo(
    val id: Long,
    val downloadStatus: DownloadStatus,
)
