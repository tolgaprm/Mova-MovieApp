package com.prmto.mova_movieapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.get_ui_mode.GetUIModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    private val getUIModeUseCase: GetUIModeUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<SplashEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            launch {
                val language = getLanguageIsoCodeUseCase().first()
                _eventFlow.emit(SplashEvent.UpdateAppLanguage(language))
            }
            launch {
                val uiMode = getUIModeUseCase().first()
                _eventFlow.emit(SplashEvent.UpdateUiMode(uiMode))
            }

        }
        navigateToHomeFragment()
    }

    private fun navigateToHomeFragment() {
        viewModelScope.launch() {
            delay(2000)
            _eventFlow.emit(
                SplashEvent.NavigateTo(SplashFragmentDirections.actionToHomeFragment())
            )
        }
    }

}

sealed class SplashEvent {
    data class NavigateTo(val directions: NavDirections) : SplashEvent()
    data class UpdateAppLanguage(val language: String) : SplashEvent()
    data class UpdateUiMode(val uiMode: Int) : SplashEvent()
}