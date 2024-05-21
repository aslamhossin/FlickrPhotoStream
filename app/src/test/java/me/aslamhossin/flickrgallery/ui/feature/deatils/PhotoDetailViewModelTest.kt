package me.aslamhossin.flickrgallery.ui.feature.deatils

import kotlinx.coroutines.test.runTest
import me.aslamhossin.domain.usecase.DownloadFileUseCase
import me.aslamhossin.flickrgallery.coroutine.TestCoroutineRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

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
        val fileName = "test.jpg"
        val url = "https://example.com/image.jpg"
        viewModel.handleIntent(PhotoDetailIntent.SavePhoto(fileName, url))
        verify(downloadFileUseCase).invoke(fileName, url)

    }

}