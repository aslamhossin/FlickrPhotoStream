package me.aslamhossin.flickrgallery.ui.feature.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.aslamhossin.core.error.toError
import me.aslamhossin.core.extensions.suspendRunCatching
import me.aslamhossin.domain.usecase.GetPhotoUseCase
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo
import me.aslamhossin.flickrgallery.ui.feature.uimodel.toUiModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
) : ViewModel() {

    companion object {
        private const val SEARCH_DELAY_MILLIS = 300L
    }

    // State
    private val _state = MutableStateFlow(PhotosViewState())
    val state: StateFlow<PhotosViewState> = _state.asStateFlow()

    // Cache to store the original list of photos
    private var cachedPhotos: List<Photo>? = null

    // Channel to handle search queries
    private val searchQueryChannel = MutableStateFlow("")

    init {
        fetchPhotos()
        observeSearchQuery()
    }

    // Handle intents
    fun handleIntent(intent: PhotoViewIntent) {
        when (intent) {
            is PhotoViewIntent.Search -> searchQueryChannel.value = intent.query
        }
    }

    // Fetch photos
    private fun fetchPhotos() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val result = suspendRunCatching { getPhotoUseCase() }
        cachedPhotos = result.getOrNull()?.map { it.toUiModel() }
        _state.update { state ->
            state.copy(
                isLoading = false,
                photos = result.getOrNull()?.map { it.toUiModel() },
                error = result.exceptionOrNull()?.toError()
            )

        }

    }

    // Observe search query with debounce
    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() = viewModelScope.launch {
        searchQueryChannel
            .debounce(SEARCH_DELAY_MILLIS) // Adjust debounce duration as needed to delay the search on every keystrokes
            .collectLatest { query ->
                searchPhotos(query)
            }
    }

    // Search photos based on the query string
    private fun searchPhotos(query: String) {
        viewModelScope.launch {
            val filteredPhotos = if (query.isBlank()) {
                cachedPhotos  // Reset to the cached list when query is blank
            } else {
                cachedPhotos?.filter {
                    it.tags.contains(query, ignoreCase = true)
                }
            }
            _state.update { currentState ->
                currentState.copy(photos = filteredPhotos)
            }
        }
    }

}
