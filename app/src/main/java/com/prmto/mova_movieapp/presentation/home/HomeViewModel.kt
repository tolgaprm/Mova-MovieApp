package com.prmto.mova_movieapp.presentation.home

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.models.GenreList
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import com.prmto.mova_movieapp.util.Constants.IS_SHOWS_SEE_ALL_PAGE
import com.prmto.mova_movieapp.util.Constants.LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val networkConnectivityObserver: ConnectivityObserver,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _languageIsoCode = MutableStateFlow("")
    val languageIsoCode: StateFlow<String> get() = _languageIsoCode

    private val _countryIsoCode = MutableStateFlow("")
    val countryIsoCode: StateFlow<String> get() = _countryIsoCode


    val isShowsRecyclerViewSeeAllSection =
        savedStateHandle.getStateFlow(IS_SHOWS_SEE_ALL_PAGE, false)

    val latestShowsRecyclerViewSeeAllSectionToolBarText = savedStateHandle.getStateFlow(
        LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID,
        R.string.now_playing
    )

    fun observeNetworkConnectivity() = networkConnectivityObserver.observe()

    fun setShowsRecyclerViewSeeAllSection(value: Boolean) {
        savedStateHandle[IS_SHOWS_SEE_ALL_PAGE] = value
    }

    fun setLatestShowsRecyclerViewSeeAllSection(@StringRes toolbarTextId: Int) {
        savedStateHandle[LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID] = toolbarTextId
    }

    fun getLanguageIsoCode(): Flow<String> {
        return homeUseCases.getLanguageIsoCodeUseCase()
    }

    fun setLanguageIsoCode(languageIsoCode: String) {
        _languageIsoCode.value = languageIsoCode
        setLanguageIsoCodeInDataStore(languageIsoCode)
    }

    private fun setLanguageIsoCodeInDataStore(languageIsoCode: String) {
        viewModelScope.launch {
            homeUseCases.updateLanguageIsoCodeUseCase(languageIsoCode)
        }
    }

    fun setCountryIsoCode(countryIsoCode: String) {
        _countryIsoCode.value = countryIsoCode
    }

    suspend fun getMovieGenreList(): GenreList {
        return homeUseCases.getMovieGenreList(_languageIsoCode.value.lowercase())
    }

    suspend fun getTvGenreList(): GenreList {
        return homeUseCases.getTvGenreList(_languageIsoCode.value.lowercase())
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getNowPlayingMoviesUseCase(
            language = _languageIsoCode.value.lowercase(),
            region = _countryIsoCode.value
        ).cachedIn(viewModelScope)
    }

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getPopularMoviesUseCase(
            language = _languageIsoCode.value.lowercase(),
            region = _countryIsoCode.value
        ).cachedIn(viewModelScope)
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getTopRatedMoviesUseCase(
            language = _languageIsoCode.value.lowercase(),
            region = _countryIsoCode.value
        ).cachedIn(viewModelScope)
    }

    fun getPopularTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getPopularTvSeries(
            language = _languageIsoCode.value.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getTopRatedTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getTopRatedTvSeriesUseCase(
            language = _languageIsoCode.value.lowercase()
        ).cachedIn(viewModelScope)
    }


}