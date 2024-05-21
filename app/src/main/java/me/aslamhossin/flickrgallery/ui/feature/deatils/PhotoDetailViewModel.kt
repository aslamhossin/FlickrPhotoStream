package me.aslamhossin.flickrgallery.ui.feature.deatils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.aslamhossin.domain.repository.file.DownloadStatus
import me.aslamhossin.domain.usecase.DownloadFileUseCase
import me.aslamhossin.flickrgallery.ui.feature.uimodel.Photo
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val downloadFileUseCase: DownloadFileUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<PhotoDetailEffect>()
    val effect: SharedFlow<PhotoDetailEffect> get() = _effect.asSharedFlow()

    private var lastDownloadStatus: DownloadStatus? = null

    fun handleIntent(intent: PhotoDetailIntent) {
        when (intent) {
            is PhotoDetailIntent.SavePhoto -> downloadPhoto(intent.photo)
        }
    }

    private fun downloadPhoto(photo: Photo) = viewModelScope.launch {
        val fileName = photo.title.ifEmpty { photo.author }
        downloadFileUseCase(fileName, photo.media.url).collectLatest { status ->
            if (status != lastDownloadStatus) {
                _effect.emit(PhotoDetailEffect.ShowMessage(status))
            }
        }
    }

}
