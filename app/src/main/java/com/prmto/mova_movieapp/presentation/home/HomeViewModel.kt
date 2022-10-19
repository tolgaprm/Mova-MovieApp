package com.prmto.mova_movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.domain.models.GenreList
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> get() = _language


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