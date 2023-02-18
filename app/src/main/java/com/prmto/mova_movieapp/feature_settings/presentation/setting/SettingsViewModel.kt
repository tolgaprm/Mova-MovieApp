package com.prmto.mova_movieapp.feature_settings.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.use_case.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.domain.use_case.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.presentation.util.BaseUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_settings.domain.use_case.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase,
    private val firebaseCoreUseCases: FirebaseCoreUseCases,
    private val localDatabaseUseCases: LocalDatabaseUseCases
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn.asStateFlow()

    private val _eventFlow = MutableSharedFlow<BaseUiEvent>()
    val eventFlow: SharedFlow<BaseUiEvent> = _eventFlow.asSharedFlow()

    init {
        val isUserSignIn = firebaseCoreUseCases.isUserSignInUseCase()
        _isSignedIn.value = isUserSignIn
    }

    fun getUIMode(): Flow<Int> {
        return settingUseCase.getUIModeUseCase()
    }

    fun updateUIMode(uiMode: Int) {
        viewModelScope.launch {
            settingUseCase.updateUIModeUseCase(uiMode)
        }
    }

    fun updateLanguageIsoCode(languageTag: String) {
        viewModelScope.launch {
            settingUseCase.updateLanguageIsoCodeUseCase(languageTag = languageTag)
        }
    }

    fun logOut() {
        viewModelScope.launch {
            getMovieFavoriteFromLocalDatabaseThenUpdateFirebase()
            getMovieWatchListItemFromLocalDatabaseThenUpdateFirebase()
            getTvSeriesFavoriteFromLocalDatabaseThenUpdateFirebase()
            getTvSeriesWatchListItemFromLocalDatabaseThenUpdateFirebase()
            delay(2000)
            signOut()
            localDatabaseUseCases.clearAllDatabaseUseCase()
        }
    }

    private fun signOut() {
        firebaseCoreUseCases.signOutUseCase()
        _isSignedIn.value = false
        emitUiEvent(BaseUiEvent.ShowSnackbar(UiText.StringResource(R.string.successfully_log_out)))
    }

    private fun getMovieFavoriteFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            localDatabaseUseCases.getFavoriteMovieIdsUseCase().collect { favoriteMovieIds ->
                firebaseCoreUseCases.addMovieToFavoriteListInFirebaseUseCase(movieIdsInFavoriteList = favoriteMovieIds,
                    onSuccess = { },
                    onFailure = { emitUiEvent(BaseUiEvent.ShowSnackbar(it)) })
            }
        }
    }

    private fun getMovieWatchListItemFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            localDatabaseUseCases.getMovieWatchListItemIdsUseCase().collect { movieIdsInWatchList ->
                firebaseCoreUseCases.addMovieToWatchListInFirebaseUseCase(movieIdsInWatchList = movieIdsInWatchList,
                    onSuccess = { },
                    onFailure = { emitUiEvent(BaseUiEvent.ShowSnackbar(it)) })
            }
        }
    }

    private fun getTvSeriesFavoriteFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            localDatabaseUseCases.getFavoriteTvSeriesIdsUseCase().collect { favoriteTvSeriesIds ->
                firebaseCoreUseCases.addTvSeriesToFavoriteListInFirebaseUseCase(
                    tvSeriesIdsInFavoriteList = favoriteTvSeriesIds,
                    onSuccess = { },
                    onFailure = { emitUiEvent(BaseUiEvent.ShowSnackbar(it)) })
            }
        }
    }

    private fun getTvSeriesWatchListItemFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            localDatabaseUseCases.getTvSeriesWatchListItemIdsUseCase()
                .collect { tvSeriesIdsInWatchList ->
                    firebaseCoreUseCases.addTvSeriesToWatchListInFirebaseUseCase(
                        tvSeriesIdsInWatchList = tvSeriesIdsInWatchList,
                        onSuccess = { },
                        onFailure = { emitUiEvent(BaseUiEvent.ShowSnackbar(it)) })
                }
        }
    }


    private fun emitUiEvent(event: BaseUiEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun getLanguageIsoCode(): Flow<String> {
        return settingUseCase.getLanguageIsoCodeUseCase()
    }

}