package com.prmto.mova_movieapp.presentation.splash

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.get_ui_mode.GetUIModeUseCase
import com.prmto.mova_movieapp.presentation.splash.event.SplashEvent
import com.prmto.mova_movieapp.presentation.util.UiText
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
    private val getUIModeUseCase: GetUIModeUseCase,
    private val networkConnectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<SplashEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        observeNetwork()
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
            _eventFlow.emit(SplashEvent.UpdateUiMode(getUIModeUseCase().first()))
        }
    }

    @VisibleForTesting
    fun observeNetwork() {
        viewModelScope.launch {
            delay(200)
            networkConnectivityObserver.observe().collect {
                if (it == ConnectivityObserver.Status.Unavaliable || it == ConnectivityObserver.Status.Lost) {
                    _eventFlow.emit(
                        SplashEvent.NetworkError(
                            UiText.StringResource(
                                R.string.internet_error
                            )
                        )
                    )
                } else if (it == ConnectivityObserver.Status.Avaliable) {
                    _eventFlow.emit(SplashEvent.NavigateTo(SplashFragmentDirections.actionToHomeFragment()))
                }
            }
        }
    }
}