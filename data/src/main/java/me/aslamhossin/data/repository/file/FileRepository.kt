package me.aslamhossin.data.repository.file

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import me.aslamhossin.core.coroutines.IoDispatcher
import me.aslamhossin.domain.repository.file.DownloadInfo
import me.aslamhossin.domain.repository.file.DownloadStatus
import me.aslamhossin.domain.repository.file.FileInput
import me.aslamhossin.domain.repository.file.FileOutput
import me.aslamhossin.domain.repository.file.IFileRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages file download operations.
 */
@Singleton
class FileRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fileManagerGateway: FileManagerGateway,
    private val downloadManagerGateway: DownloadManagerGateway,
) : IFileRepository {

    /**
     * Downloads a file and provides updates on the download progress.
     *
     * @param fileInput Information about the file to be downloaded.
     * @return A Flow that emits FileOutput updates.
     */
    override suspend fun downloadFile(fileInput: FileInput): Flow<FileOutput> = channelFlow {

        val downloadDirectory = fileManagerGateway.getDownloadDirectory(fileInput.fileType)
        val fileName = fileInput.name
        val outputFile = fileManagerGateway.createOutputFile(downloadDirectory, fileName)


        when {
            fileManagerGateway.fileExists(outputFile) -> {
                send(
                    FileOutput(
                        name = fileName,
                        outputPath = outputFile.absolutePath,
                        fileType = fileInput.fileType,
                        downloadInfo = DownloadInfo(
                            id = 0,
                            downloadStatus = DownloadStatus.Failed.FileAlreadyExists
                        )
                    )
                )
                return@channelFlow
            }

            else -> {
                val downloadId =
                    downloadManagerGateway.enqueueDownloadRequest(fileInput.url, outputFile)

                withContext(ioDispatcher) {
                    downloadManagerGateway.monitorDownloadProgress(
                        downloadId,
                        outputFile
                    ).collectLatest { downloadStatus ->
                        val output = FileOutput(
                            name = fileName,
                            outputPath = outputFile.absolutePath,
                            fileType = fileInput.fileType,
                            downloadInfo = DownloadInfo(id = downloadId, downloadStatus)
                        )
                        send(output)
                        if (downloadStatus is DownloadStatus.Completed) {
                            fileManagerGateway.syncWithGallery(outputFile)
                        }
                    }
                }
            }
        }

    }.flowOn(ioDispatcher)

}

