package com.prmto.mova_movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import com.prmto.mova_movieapp.presentation.home.event.AdapterLoadStateEvent
import com.prmto.mova_movieapp.presentation.home.event.HomeEvent
import com.prmto.mova_movieapp.presentation.home.state.HomeState
import com.prmto.mova_movieapp.presentation.home.state.PagingAdapterLoadState
import com.prmto.mova_movieapp.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val networkConnectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = _homeState

    private val _adapterLoadState =
        MutableStateFlow<PagingAdapterLoadState>(PagingAdapterLoadState())
    val adapterLoadState: StateFlow<PagingAdapterLoadState> get() = _adapterLoadState

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            launch {
                homeUseCases.getLanguageIsoCodeUseCase().collect { languageIsoCode ->
                    _homeState.value = _homeState.value.copy(
                        languageIsoCode = languageIsoCode
                    )
                }
            }
            launch {
                val movieGenreList =
                    homeUseCases.getMovieGenreList(homeState.value.languageIsoCode).genres
                _homeState.value = _homeState.value.copy(
                    movieGenreList = movieGenreList
                )
            }
            launch {
                val tvGenreList =
                    homeUseCases.getTvGenreList(homeState.value.languageIsoCode).genres
                _homeState.value = _homeState.value.copy(
                    tvGenreList = tvGenreList
                )
            }
            launch {
                networkConnectivityObserver.observe().collectLatest {
                    if (it == ConnectivityObserver.Status.Unavaliable || it == ConnectivityObserver.Status.Lost) {
                        emitErrorForShowSnackBar(UiText.StringResource(R.string.internet_error))
                    } else if (it == ConnectivityObserver.Status.Avaliable) {
                        _eventFlow.emit(HomeUiEvent.RetryAllAdapters)
                    }
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

    fun onAdapterLoadStateEvent(event: AdapterLoadStateEvent) {
        when (event) {
            is AdapterLoadStateEvent.PagingError -> {
                _adapterLoadState.value = PagingAdapterLoadState(
                    error = event.uiText
                )
                emitErrorForShowSnackBar(event.uiText)
            }

            is AdapterLoadStateEvent.NowPlayingLoading -> {
                _adapterLoadState.update {
                    it.copy(nowPlayingState = it.nowPlayingState.copy(isLoading = true))
                }
            }
            is AdapterLoadStateEvent.NowPlayingNotLoading -> {
                _adapterLoadState.update {
                    it.copy(nowPlayingState = it.nowPlayingState.copy(isLoading = false))
                }
            }

            is AdapterLoadStateEvent.PopularMoviesLoading -> {
                _adapterLoadState.update {
                    it.copy(popularMoviesState = it.popularMoviesState.copy(isLoading = true))
                }
            }
            is AdapterLoadStateEvent.PopularMoviesNotLoading -> {
                _adapterLoadState.update {
                    it.copy(popularMoviesState = it.popularMoviesState.copy(isLoading = false))
                }
            }

            is AdapterLoadStateEvent.PopularTvSeriesLoading -> {
                _adapterLoadState.update {
                    it.copy(popularTvSeriesState = it.popularTvSeriesState.copy(isLoading = true))
                }
            }
            is AdapterLoadStateEvent.PopularTvSeriesNotLoading -> {
                _adapterLoadState.update {
                    it.copy(popularTvSeriesState = it.popularTvSeriesState.copy(isLoading = false))
                }
            }
            is AdapterLoadStateEvent.TopRatedMoviesLoading -> {
                _adapterLoadState.update {
                    it.copy(topRatedMoviesState = it.topRatedMoviesState.copy(isLoading = true))
                }
            }
            is AdapterLoadStateEvent.TopRatedMoviesNotLoading -> {
                _adapterLoadState.update {
                    it.copy(topRatedMoviesState = it.topRatedMoviesState.copy(isLoading = false))
                }
            }

            is AdapterLoadStateEvent.TopRatedTvSeriesLoading -> {
                _adapterLoadState.update {
                    it.copy(topRatedTvSeriesState = it.topRatedTvSeriesState.copy(isLoading = true))
                }
            }
            is AdapterLoadStateEvent.TopRatedTvSeriesNotLoading -> {
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
        _homeState.value = _homeState.value.copy(
            isShowsSeeAllPage = false
        )
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
        return homeUseCases.getPopularTvSeries(
            language = homeState.value.languageIsoCode
        ).cachedIn(viewModelScope)
    }

    fun getTopRatedTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getTopRatedTvSeriesUseCase(
            language = homeState.value.languageIsoCode
        ).cachedIn(viewModelScope)
    }

    sealed class HomeUiEvent {
        data class NavigateTo(val directions: NavDirections) : HomeUiEvent()
        data class ShowSnackbar(val uiText: UiText) : HomeUiEvent()
        object RetryAllAdapters : HomeUiEvent()
    }
}