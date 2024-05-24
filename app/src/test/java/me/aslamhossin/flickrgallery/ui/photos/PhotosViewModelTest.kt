package me.aslamhossin.flickrgallery.ui.photos

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.aslamhossin.core.error.displayableMessage
import me.aslamhossin.domain.model.MediaItem
import me.aslamhossin.domain.model.PhotoItem
import me.aslamhossin.domain.usecase.GetPhotoUseCase
import me.aslamhossin.flickrgallery.coroutine.TestCoroutineRule
import me.aslamhossin.flickrgallery.ui.feature.photos.PhotoViewIntent
import me.aslamhossin.flickrgallery.ui.feature.photos.PhotosViewModel
import me.aslamhossin.flickrgallery.ui.feature.photos.PhotosViewState
import me.aslamhossin.flickrgallery.ui.feature.uimodel.toUiModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
class PhotosViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var getPhotoUseCase: GetPhotoUseCase

    private lateinit var viewModel: PhotosViewModel

    private val testPhotoItems = listOf(
        PhotoItem(
            title = "Photo1",
            author = "author1",
            dateTaken = "dateTaken1",
            published = "published1",
            tags = "tag1",
            media = MediaItem("url1"),
            description = "description1",
            link = "link1",
            authorId = "authorId1"
        ),
        PhotoItem(
            title = "Photo2",
            author = "author2",
            dateTaken = "dateTaken2",
            published = "published2",
            tags = "tag2",
            media = MediaItem("url2"),
            description = "description2",
            link = "link2",
            authorId = "authorId2"
        )
    )

    private val testPhotos = testPhotoItems.map { it.toUiModel() }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = PhotosViewModel(getPhotoUseCase)
    }

    @Test
    fun `initial state should return when init block executes`() = runTest {
        val expectedState = PhotosViewState(
            isLoading = false,
            photos = null,
            error = null
        )

        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(expectedState, initialState)
        }
    }

    @Test
    fun `success state should return when getPhotoUseCase returns photos`() = runTest(UnconfinedTestDispatcher()) {
        `when`(getPhotoUseCase()).thenReturn(testPhotoItems)

        viewModel.state.test {
            awaitItem() // skip the initial state
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertEquals(testPhotos, successState.photos)
            assertNull(successState.error)
        }
    }

    @Test
    fun `failure state should return when getPhotoUseCase throws an exception`() = runTest(UnconfinedTestDispatcher()) {
        val exception = RuntimeException("Unknown error")
        `when`(getPhotoUseCase()).thenThrow(exception)

        viewModel.state.test {
            awaitItem() // skip the initial state
            awaitItem()

            val failureState = awaitItem()
            assertFalse(failureState.isLoading)
            assertNull(failureState.photos)
            assertEquals(exception.message, failureState.error?.displayableMessage)
        }
    }

    @Test
    fun `filtered photos should return when searchPhotos is called with valid query`() =
        runTest(UnconfinedTestDispatcher()) {
            setPrivateField(viewModel, "cachedPhotos", testPhotos)
            `when`(getPhotoUseCase()).thenReturn(testPhotoItems)

            viewModel.handleIntent(PhotoViewIntent.Search("tag1"))

            viewModel.state.test {
                awaitItem()
                awaitItem()
                awaitItem()

                val filteredState = awaitItem()
                assertEquals(
                    testPhotos.filter { it.tags.contains("tag1", ignoreCase = true) },
                    filteredState.photos
                )
            }
        }

    @Test
    fun `cached photos should be returned when searchPhotos is called with empty query`() = runTest(UnconfinedTestDispatcher()) {
        setPrivateField(viewModel, "cachedPhotos", testPhotos)
        `when`(getPhotoUseCase()).thenReturn(testPhotoItems)

        viewModel.handleIntent(PhotoViewIntent.Search(""))

        viewModel.state.test {
            awaitItem()
            awaitItem()

            val filteredState = awaitItem()
            assertEquals(testPhotos, filteredState.photos)
        }
    }

    @Test
    fun `emptyList should be returned when searchPhotos is called with non-existent query`() =
        runTest(UnconfinedTestDispatcher()) {
            setPrivateField(viewModel, "cachedPhotos", testPhotos)
            `when`(getPhotoUseCase()).thenReturn(testPhotoItems)

            viewModel.handleIntent(PhotoViewIntent.Search("nonexistent"))

            viewModel.state.test {
                awaitItem()
                awaitItem()
                awaitItem()

                val filteredState = awaitItem()
                assertTrue(filteredState.photos?.isEmpty() == true)
            }
        }

    private fun setPrivateField(instance: Any, fieldName: String, value: Any?) {
        val field = instance.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(instance, value)
    }
}

