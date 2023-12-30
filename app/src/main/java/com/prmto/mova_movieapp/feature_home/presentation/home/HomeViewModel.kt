package com.prmto.mova_movieapp.feature_home.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.presentation.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.feature_home.domain.usecases.HomeUseCases
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeEvent
import com.prmto.mova_movieapp.feature_home.presentation.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : BaseViewModelWithUiEvent<UiEvent>() {

    private val _homeState = MutableStateFlow(HomeState())

    val homeState: StateFlow<HomeState> = combine(
        _homeState,
        homeUseCases.getLanguageIsoCodeUseCase()
    ) { state, languageIsoCode ->
        _homeState.updateAndGet {
            it.copy(
                languageIsoCode = languageIsoCode
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, HomeState())

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ClickSeeAllText -> {
                _homeState.value = _homeState.value.copy(
                    isShowsSeeAllPage = true,
                    seeAllPageToolBarText = event.seeAllPageToolBarText
                )
            }


            is HomeEvent.NavigateToDetailBottomSheet -> {
                addConsumableViewEvent(UiEvent.NavigateTo(event.directions))
            }

            is HomeEvent.UpdateCountryIsoCode -> {
                _homeState.value = homeState.value.copy(
                    countryIsoCode = event.countryIsoCode
                )
            }
        }
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getNowPlayingMoviesUseCase(
            language = homeState.value.languageIsoCode,
            region = homeState.value.countryIsoCode
        ).cachedIn(viewModelScope)
    }

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getPopularMoviesUseCase(
            language = homeState.value.languageIsoCode,
            region = homeState.value.countryIsoCode
        ).cachedIn(viewModelScope)
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getTopRatedMoviesUseCase(
            language = homeState.value.languageIsoCode,
            region = homeState.value.countryIsoCode
        ).cachedIn(viewModelScope)
    }

    fun getPopularTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getPopularTvSeriesUseCase(
            language = homeState.value.languageIsoCode
        ).cachedIn(viewModelScope)
    }

    fun getTopRatedTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getTopRatedTvSeriesUseCase(
            language = homeState.value.languageIsoCode
        ).cachedIn(viewModelScope)
    }
}