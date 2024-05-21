package me.aslamhossin.domain.repository.file

import kotlinx.coroutines.flow.Flow


interface IFileRepository {

    suspend fun downloadFile(fileInput: FileInput): Flow<FileOutput>

}