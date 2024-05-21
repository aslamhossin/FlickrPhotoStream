package me.aslamhossin.flickrgallery.ui.feature.deatils

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import me.aslamhossin.domain.repository.file.DownloadStatus
import me.aslamhossin.domain.usecase.DownloadFileUseCase
import me.aslamhossin.flickrgallery.coroutine.TestCoroutineRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private val downloadFileUseCase: DownloadFileUseCase = mock(DownloadFileUseCase::class.java)
    private lateinit var viewModel: PhotoDetailViewModel


    @Before
    fun setup() {
        viewModel = PhotoDetailViewModel(downloadFileUseCase)
    }

    @Test
    fun `savePhoto() calls downloadFileUseCase with correct parameters`() = runTest {
        // Arrange
        val fileName = "test.jpg"
        val url = "https://example.com/image.jpg"
        val downloadStatusFlow = flowOf(DownloadStatus.Completed("test.jpg"))
        `when`(downloadFileUseCase.invoke(fileName, url)).thenReturn(downloadStatusFlow)

        // Act
        viewModel.handleIntent(PhotoDetailIntent.SavePhoto(fileName, url))
        advanceUntilIdle()

        // Assert
        verify(downloadFileUseCase).invoke(fileName, url)

    }


    @Test
    fun `savePhoto() emits completed download status`() = runTest {
        // Arrange
        val fileName = "test.jpg"
        val url = "https://example.com/image.jpg"
        val downloadStatusFlow = flowOf(DownloadStatus.Completed("test.jpg"))
        `when`(downloadFileUseCase.invoke(fileName, url)).thenReturn(downloadStatusFlow)

        // Act
        viewModel.handleIntent(PhotoDetailIntent.SavePhoto(fileName, url))

        // Assert
        viewModel.effect.test {
            val effect = awaitItem()
            assert(effect is PhotoDetailEffect.ShowMessage)
        }

    }

    @Test
    fun `savePhoto() emits failed download status`() = runTest {
        // Arrange
        val fileName = "test.jpg"
        val url = "https://example.com/image.jpg"

        val downloadStatusFlow = flowOf(DownloadStatus.Failed.DownloadFailed("Download failed"))
        `when`(downloadFileUseCase.invoke(fileName, url)).thenReturn(downloadStatusFlow)
        // Act
        viewModel.handleIntent(PhotoDetailIntent.SavePhoto(fileName, url))
        // Assert
        viewModel.effect.test {
            val effect = awaitItem()
            assert(effect is PhotoDetailEffect.ShowMessage)
        }
    }

    @Test
    fun `savePhoto() emits fileAlreadyExists download status`() = runTest {
        // Arrange
        val fileName = "test.jpg"
        val url = "https://example.com/image.jpg"
        val downloadStatusFlow = flowOf(DownloadStatus.Failed.FileAlreadyExists)
        `when`(downloadFileUseCase.invoke(fileName, url)).thenReturn(downloadStatusFlow)

        // Act
        viewModel.handleIntent(PhotoDetailIntent.SavePhoto(fileName, url))
        // Assert
        viewModel.effect.test {
            val effect = awaitItem()
            assert(effect is PhotoDetailEffect.ShowMessage)
        }
    }

}