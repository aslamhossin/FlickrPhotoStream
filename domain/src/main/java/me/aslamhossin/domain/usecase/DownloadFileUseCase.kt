package me.aslamhossin.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.aslamhossin.domain.repository.file.DownloadStatus
import me.aslamhossin.domain.repository.file.FileInput
import me.aslamhossin.domain.repository.file.IFileRepository
import javax.inject.Inject

class DownloadFileUseCase @Inject constructor(private val fileRepository: IFileRepository) {

    suspend operator fun invoke(fileName: String, fileUri: String): Flow<DownloadStatus> =
        fileRepository.downloadFile(FileInput(name = fileName, url = fileUri))
            .map { it.downloadInfo.downloadStatus }

}
