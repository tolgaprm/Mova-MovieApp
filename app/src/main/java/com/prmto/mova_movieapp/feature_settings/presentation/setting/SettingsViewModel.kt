package com.prmto.mova_movieapp.feature_settings.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.use_case.database.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.domain.use_case.firebase.movie.GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.movie.GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.tv.GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.tv.GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
import com.prmto.mova_movieapp.core.presentation.util.BaseUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_settings.domain.use_case.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase,
    private val firebaseCoreUseCases: FirebaseCoreUseCases,
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val getFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase: GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase,
    private val getMovieWatchListFromLocalDatabaseThenUpdateToFirebase: GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase,
    private val getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase: GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase,
    private val getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase: GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

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
            _isLoading.value = true
            getMovieFavoriteFromLocalDatabaseThenUpdateFirebase()
            getMovieWatchListItemFromLocalDatabaseThenUpdateFirebase()
            getTvSeriesFavoriteFromLocalDatabaseThenUpdateFirebase()
            getTvSeriesWatchListItemFromLocalDatabaseThenUpdateFirebase()
            delay(2000)
            signOut()
            withContext(Dispatchers.IO) {
                localDatabaseUseCases.clearAllDatabaseUseCase()
            }
            _isLoading.value = false
        }
    }

    private fun signOut() {
        firebaseCoreUseCases.signOutUseCase()
        _isSignedIn.value = false
        emitUiEvent(BaseUiEvent.ShowSnackbar(UiText.StringResource(R.string.successfully_log_out)))
    }

    private fun getMovieFavoriteFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            getFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase(
                onSuccess = { return@getFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase },
                onFailure = { emitUiEvent(BaseUiEvent.ShowSnackbar(it)) }
            )
        }
    }

    private fun getMovieWatchListItemFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            getMovieWatchListFromLocalDatabaseThenUpdateToFirebase(
                onSuccess = { return@getMovieWatchListFromLocalDatabaseThenUpdateToFirebase },
                onFailure = { emitUiEvent(BaseUiEvent.ShowSnackbar(it)) }
            )
        }
    }

    private fun getTvSeriesFavoriteFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase(
                onSuccess = { return@getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase },
                onFailure = { emitUiEvent(BaseUiEvent.ShowSnackbar(it)) }
            )
        }
    }

    private fun getTvSeriesWatchListItemFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase(
                onSuccess = { return@getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase },
                onFailure = { emitUiEvent(BaseUiEvent.ShowSnackbar(it)) })
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