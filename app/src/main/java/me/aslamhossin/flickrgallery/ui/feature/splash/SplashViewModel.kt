package me.aslamhossin.flickrgallery.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            onAction(SplashAction.NavigateHome)
        }
    }

    private fun onAction(action: SplashAction) {
        when (action) {
            is SplashAction.NavigateHome -> {
                _state.value = _state.value.copy(isSplashTimeout = true)
            }
        }
    }

}
