package com.prmto.mova_movieapp.presentation.home

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.domain.models.GenreList
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import com.prmto.mova_movieapp.util.Constants.IS_SHOWS_SEE_ALL_PAGE
import com.prmto.mova_movieapp.util.Constants.LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID
import com.prmto.mova_movieapp.util.Constants.NOW_PLAYING_TEXT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val networkConnectivityObserver: ConnectivityObserver,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> get() = _language

    private val _showSnackBarNoInternetConnectivity = MutableSharedFlow<String>()
    val showSnackBarNoInternetConnectivity: SharedFlow<String> get() = _showSnackBarNoInternetConnectivity


    val isShowsRecyclerViewSeeAllSection =
        savedStateHandle.getStateFlow(IS_SHOWS_SEE_ALL_PAGE, false)

    val latestShowsRecyclerViewSeeAllSectionToolBarText = savedStateHandle.getStateFlow(
        LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID,
        NOW_PLAYING_TEXT_ID
    )


    fun observeNetworkConnectivity() = networkConnectivityObserver.observe()


    fun showSnackbar() {
        viewModelScope.launch {
            _showSnackBarNoInternetConnectivity.emit("No Internet Connection")
        }
    }


    fun setShowsRecyclerViewSeeAllSection(value: Boolean) {
        savedStateHandle[IS_SHOWS_SEE_ALL_PAGE] = value
    }

    fun setLatestShowsRecyclerViewSeeAllSection(@StringRes toolbarTextId: Int) {
        savedStateHandle[LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID] = toolbarTextId
    }


    fun getLanguage(): Flow<String> {
        return homeUseCases.getLocaleUseCase()
    }

    fun setLanguage(language: String) {
        _language.value = language
    }

    suspend fun getMovieGenreList(): GenreList {
        return homeUseCases.getMovieGenreList(_language.value.lowercase())
    }

    suspend fun getTvGenreList(): GenreList {
        return homeUseCases.getTvGenreList(_language.value.lowercase())
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getNowPlayingMoviesUseCase(
            language = _language.value.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getPopularMoviesUseCase(
            language = _language.value.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getTopRatedMoviesUseCase(
            language = _language.value.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getPopularTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getPopularTvSeries(
            language = _language.value.lowercase()
        )
    }

    fun getTopRatedTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getTopRatedTvSeriesUseCase(
            language = _language.value.lowercase()
        )
    }


}