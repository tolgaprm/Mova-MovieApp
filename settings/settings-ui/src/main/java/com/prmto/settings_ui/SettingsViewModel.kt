package com.prmto.settings_ui

import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.use_case.database.LocalDatabaseUseCases
import com.prmto.core_domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.core_domain.use_case.firebase.movie.GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase
import com.prmto.core_domain.use_case.firebase.movie.GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase
import com.prmto.core_domain.use_case.firebase.tv.GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase
import com.prmto.core_domain.use_case.firebase.tv.GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase
import com.prmto.core_domain.util.UiText
import com.prmto.core_ui.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.core_ui.util.UiEvent
import com.prmto.settings_domain.use_case.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
) : BaseViewModelWithUiEvent<UiEvent>() {

    private val mutableState = MutableStateFlow(SettingsUiState())
    val state = mutableState.asStateFlow()

    init {
        isUserSignIn()
    }

    private fun isUserSignIn() {
        val isUserSignIn = firebaseCoreUseCases.isUserSignInUseCase()
        mutableState.update { it.copy(isSignedIn = isUserSignIn) }
    }

    fun getUiMode(): Flow<Int?> {
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
            mutableState.update { it.copy(isLoading = true) }
            getMovieFavoriteFromLocalDatabaseThenUpdateFirebase()
            getMovieWatchListItemFromLocalDatabaseThenUpdateFirebase()
            getTvSeriesFavoriteFromLocalDatabaseThenUpdateFirebase()
            getTvSeriesWatchListItemFromLocalDatabaseThenUpdateFirebase()
            delay(2000)
            signOut()
            withContext(Dispatchers.IO) {
                localDatabaseUseCases.clearAllDatabaseUseCase()
            }
            mutableState.update { it.copy(isLoading = false) }
        }
    }

    private fun signOut() {
        firebaseCoreUseCases.signOutUseCase()
        mutableState.update { it.copy(isSignedIn = false) }
        addConsumableViewEvent(UiEvent.ShowSnackbar(UiText.StringResource(R.string.successfully_log_out)))
    }

    private fun getMovieFavoriteFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = { getFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase() },
                onSuccessCallback = { return@handleResourceWithCallbacks },
                onErrorCallback = { addConsumableViewEvent(UiEvent.ShowSnackbar(it)) }
            )
        }
    }

    private fun getMovieWatchListItemFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = { getMovieWatchListFromLocalDatabaseThenUpdateToFirebase() },
                onSuccessCallback = { return@handleResourceWithCallbacks },
                onErrorCallback = { addConsumableViewEvent(UiEvent.ShowSnackbar(it)) }
            )
        }
    }

    private fun getTvSeriesFavoriteFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = { getFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase() },
                onSuccessCallback = { return@handleResourceWithCallbacks },
                onErrorCallback = { addConsumableViewEvent(UiEvent.ShowSnackbar(it)) }
            )
        }
    }

    private fun getTvSeriesWatchListItemFromLocalDatabaseThenUpdateFirebase() {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = { getTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase() },
                onSuccessCallback = { return@handleResourceWithCallbacks },
                onErrorCallback = { addConsumableViewEvent(UiEvent.ShowSnackbar(it)) }
            )
        }
    }

    fun getLanguageIsoCode(): Flow<String> {
        return settingUseCase.getLanguageIsoCodeUseCase()
    }
}