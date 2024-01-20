package com.prmto.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.uIMode.GetUIModeUseCase
import com.prmto.core_ui.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.navigation.NavigateFlow
import com.prmto.splash.event.SplashEvent
import com.prmto.splash.util.Constants.SPLASH_SCREEN_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    private val getUIModeUseCase: GetUIModeUseCase
) : BaseViewModelWithUiEvent<SplashEvent>() {

    init {
        getNavigateAfterSplashScreenDelay()
        getLanguageIsoCode()
        getUiMode()
    }

    private fun getLanguageIsoCode() {
        viewModelScope.launch {
            addConsumableViewEvent(SplashEvent.UpdateAppLanguage(getLanguageIsoCodeUseCase().first()))
        }
    }

    private fun getUiMode() {
        viewModelScope.launch {
            addConsumableViewEvent(
                SplashEvent.UpdateUiMode(
                    getUIModeUseCase().first() ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                )
            )
        }
    }

    private fun getNavigateAfterSplashScreenDelay() {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DELAY)
            addConsumableViewEvent(SplashEvent.NavigateTo(NavigateFlow.HomeFlow))
        }
    }
}