package me.aslamhossin.data.repository.file

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.aslamhossin.data.coroutine.TestCoroutineRule
import me.aslamhossin.domain.repository.file.DownloadStatus
import me.aslamhossin.domain.repository.file.FileInput
import me.aslamhossin.domain.repository.file.FileType
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.io.File

@ExperimentalCoroutinesApi
class FileRepositoryTest {

    @get:Rule
    val testDispatcherRule = TestCoroutineRule()

    private val fileManagerGateway: FileManagerGateway = mock(FileManagerGateway::class.java)
    private val downloadManagerGateway: DownloadManagerGateway =
        mock(DownloadManagerGateway::class.java)
    private lateinit var fileRepository: FileRepository

    @Before
    fun setUp() {
        fileRepository = FileRepository(
            testDispatcherRule.testDispatcher,
            fileManagerGateway,
            downloadManagerGateway
        )
    }

    @Test
    fun `downloadFile emits FileAlreadyExists if file already exists`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        // Given
        val fileType = FileType.Image
        val fileInput =
            FileInput(name = "testFile", url = "http://example.com/file", fileType = fileType)
        val downloadDirectory = "/downloads"
        val outputFile = File(downloadDirectory, fileInput.name)
        `when`(fileManagerGateway.getDownloadDirectory(fileInput.fileType)).thenReturn(
            downloadDirectory
        )
        `when`(fileManagerGateway.createOutputFile(downloadDirectory, fileInput.name)).thenReturn(
            outputFile
        )
        `when`(fileManagerGateway.fileExists(outputFile)).thenReturn(true)


        // When
        val flow = fileRepository.downloadFile(fileInput)

        // Then
        flow.collect { fileOutput ->
            Assert.assertEquals(
                DownloadStatus.Failed.FileAlreadyExists,
                fileOutput.downloadInfo.downloadStatus
            )
        }

        verify(fileManagerGateway).getDownloadDirectory(fileInput.fileType)
        verify(fileManagerGateway).createOutputFile(downloadDirectory, fileInput.name)
    }

    @Test
    fun `downloadFile emits progress and completes successfully`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        // Given
        val fileType = FileType.Image // Adjust this to your actual enum value or class
        val fileInput =
            FileInput(name = "testFile", url = "http://example.com/file", fileType = fileType)
        val downloadDirectory = "/downloads"
        val outputFile = File(downloadDirectory, fileInput.name)
        val downloadId = 123L
        `when`(fileManagerGateway.getDownloadDirectory(fileInput.fileType)).thenReturn(
            downloadDirectory
        )
        `when`(fileManagerGateway.createOutputFile(downloadDirectory, fileInput.name)).thenReturn(
            outputFile
        )
        `when`(fileManagerGateway.fileExists(outputFile)).thenReturn(false)
        `when`(downloadManagerGateway.enqueueDownloadRequest(fileInput.url, outputFile)).thenReturn(
            downloadId
        )
        `when`(downloadManagerGateway.monitorDownloadProgress(downloadId, outputFile)).thenReturn(
            flowOf(DownloadStatus.InProgress(50), DownloadStatus.Completed(outputFile.absolutePath))
        )

        // When
        val flow = fileRepository.downloadFile(fileInput)

        // Then
        val expectedStatuses = listOf(
            DownloadStatus.InProgress(0),
            DownloadStatus.InProgress(50),
            DownloadStatus.Completed(outputFile.absolutePath)
        )
        var index = 0

        flow.collect { fileOutput ->
            Assert.assertEquals(expectedStatuses[index], fileOutput.downloadInfo.downloadStatus)
            index++
        }

        verify(fileManagerGateway).getDownloadDirectory(fileInput.fileType)
        verify(fileManagerGateway).createOutputFile(downloadDirectory, fileInput.name)
        verify(downloadManagerGateway).enqueueDownloadRequest(fileInput.url, outputFile)
        verify(downloadManagerGateway).monitorDownloadProgress(downloadId, outputFile)
        verify(fileManagerGateway).syncWithGallery(outputFile)
    }

}
