package com.prmto.mova_movieapp.feature_home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import com.prmto.mova_movieapp.feature_home.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_home.domain.use_cases.HomeUseCases
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeAdapterLoadStateEvent
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeEvent
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeUiEvent
import com.prmto.mova_movieapp.feature_home.presentation.home.state.HomePagingAdapterLoadState
import com.prmto.mova_movieapp.feature_home.presentation.home.state.HomeState
import com.prmto.mova_movieapp.feature_home.presentation.home.state.PagingAdapterLoadStateItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = _homeState

    private val _adapterLoadState = MutableStateFlow(HomePagingAdapterLoadState())
    val adapterLoadState: StateFlow<HomePagingAdapterLoadState> get() = _adapterLoadState

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.d(throwable.toString())
    }


    init {
        viewModelScope.launch(handler) {
            launch {
                homeUseCases.getLanguageIsoCodeUseCase().collect { languageIsoCode ->
                    _homeState.value = _homeState.value.copy(
                        languageIsoCode = languageIsoCode
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ClickSeeAllText -> {
                _homeState.value = _homeState.value.copy(
                    isShowsSeeAllPage = true,
                    seeAllPageToolBarText = event.seeAllPageToolBarText
                )
            }
            is HomeEvent.NavigateUpFromSeeAllSection -> hideSeeAllPage()
            is HomeEvent.OnBackPressed -> hideSeeAllPage()
            is HomeEvent.NavigateToDetailBottomSheet -> {
                viewModelScope.launch {
                    _eventFlow.emit(HomeUiEvent.NavigateTo(event.directions))
                }
            }
            is HomeEvent.UpdateCountryIsoCode -> {
                _homeState.value = homeState.value.copy(
                    countryIsoCode = event.countryIsoCode
                )
            }
        }
    }

    fun onAdapterLoadStateEvent(event: HomeAdapterLoadStateEvent) {
        when (event) {
            is HomeAdapterLoadStateEvent.PagingError -> {
                _adapterLoadState.value = HomePagingAdapterLoadState(
                    error = event.uiText,
                    nowPlayingState = PagingAdapterLoadStateItem(isLoading = false),
                    popularMoviesState = PagingAdapterLoadStateItem(isLoading = false),
                    popularTvSeriesState = PagingAdapterLoadStateItem(isLoading = false),
                    topRatedMoviesState = PagingAdapterLoadStateItem(isLoading = false),
                    topRatedTvSeriesState = PagingAdapterLoadStateItem(isLoading = false)
                )
                emitErrorForShowSnackBar(event.uiText)
            }

            is HomeAdapterLoadStateEvent.NowPlayingLoading -> {
                _adapterLoadState.update {
                    it.copy(nowPlayingState = it.nowPlayingState.copy(isLoading = true))
                }
            }
            is HomeAdapterLoadStateEvent.NowPlayingNotLoading -> {
                _adapterLoadState.update {
                    it.copy(nowPlayingState = it.nowPlayingState.copy(isLoading = false))
                }
            }

            is HomeAdapterLoadStateEvent.PopularMoviesLoading -> {
                _adapterLoadState.update {
                    it.copy(popularMoviesState = it.popularMoviesState.copy(isLoading = true))
                }
            }
            is HomeAdapterLoadStateEvent.PopularMoviesNotLoading -> {
                _adapterLoadState.update {
                    it.copy(popularMoviesState = it.popularMoviesState.copy(isLoading = false))
                }
            }

            is HomeAdapterLoadStateEvent.PopularTvSeriesLoading -> {
                _adapterLoadState.update {
                    it.copy(popularTvSeriesState = it.popularTvSeriesState.copy(isLoading = true))
                }
            }
            is HomeAdapterLoadStateEvent.PopularTvSeriesNotLoading -> {
                _adapterLoadState.update {
                    it.copy(popularTvSeriesState = it.popularTvSeriesState.copy(isLoading = false))
                }
            }
            is HomeAdapterLoadStateEvent.TopRatedMoviesLoading -> {
                _adapterLoadState.update {
                    it.copy(topRatedMoviesState = it.topRatedMoviesState.copy(isLoading = true))
                }
            }
            is HomeAdapterLoadStateEvent.TopRatedMoviesNotLoading -> {
                _adapterLoadState.update {
                    it.copy(topRatedMoviesState = it.topRatedMoviesState.copy(isLoading = false))
                }
            }

            is HomeAdapterLoadStateEvent.TopRatedTvSeriesLoading -> {
                _adapterLoadState.update {
                    it.copy(topRatedTvSeriesState = it.topRatedTvSeriesState.copy(isLoading = true))
                }
            }
            is HomeAdapterLoadStateEvent.TopRatedTvSeriesNotLoading -> {
                _adapterLoadState.update {
                    it.copy(topRatedTvSeriesState = it.topRatedTvSeriesState.copy(isLoading = false))
                }
            }
        }
    }

    private fun emitErrorForShowSnackBar(uiText: UiText) {
        viewModelScope.launch {
            _eventFlow.emit(HomeUiEvent.ShowSnackbar(uiText))
        }
    }

    private fun hideSeeAllPage() {
        _homeState.value = _homeState.value.copy(isShowsSeeAllPage = false)
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