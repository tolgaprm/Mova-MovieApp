package com.prmto.mova_movieapp.feature_splash.presentation.splash

import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.uIMode.GetUIModeUseCase
import com.prmto.mova_movieapp.feature_splash.presentation.splash.event.SplashEvent
import com.prmto.mova_movieapp.feature_splash.util.Constants.SPLASH_SCREEN_DELAY
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
        getNavigateAfterSplashScreenDelay()
        getLanguageIsoCode()
        getUiMode()
    }

    @VisibleForTesting
    fun getLanguageIsoCode() {
        viewModelScope.launch {
            _eventFlow.emit(SplashEvent.UpdateAppLanguage(getLanguageIsoCodeUseCase().first()))
        }
    }

    @VisibleForTesting
    fun getUiMode() {
        viewModelScope.launch {
            _eventFlow.emit(
                SplashEvent.UpdateUiMode(
                    getUIModeUseCase().first() ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                )
            )
        }
    }

    private fun getNavigateAfterSplashScreenDelay() {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DELAY)
            _eventFlow.emit(SplashEvent.NavigateTo(SplashFragmentDirections.actionToHomeFragment()))
        }
    }
}