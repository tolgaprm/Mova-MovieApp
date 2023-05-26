package com.prmto.mova_movieapp.feature_home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.core.domain.repository.isAvaliable
import com.prmto.mova_movieapp.core.presentation.util.BaseUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_home.domain.use_cases.HomeUseCases
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeAdapterLoadStateEvent
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeEvent
import com.prmto.mova_movieapp.feature_home.presentation.home.state.HomePagingAdapterLoadState
import com.prmto.mova_movieapp.feature_home.presentation.home.state.HomeState
import com.prmto.mova_movieapp.feature_home.presentation.home.state.PagingAdapterLoadStateItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val observeNetwork: ConnectivityObserver
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = _homeState

    private val _adapterLoadState = MutableStateFlow(HomePagingAdapterLoadState())
    val adapterLoadState: StateFlow<HomePagingAdapterLoadState> get() = _adapterLoadState

    private val _networkState = MutableStateFlow(ConnectivityObserver.Status.Unavaliable)
    val networkState: StateFlow<ConnectivityObserver.Status> = _networkState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<BaseUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.d(throwable.toString())
    }


    init {
        viewModelScope.launch(handler) {
            collectNetworkState()
            collectLanguageIsoCode()
        }
    }

    private fun collectNetworkState() {
        viewModelScope.launch {
            observeNetwork.observe().collectLatest { status ->
                _networkState.value = status
            }
        }
    }

    private fun collectLanguageIsoCode() {
        viewModelScope.launch {
            homeUseCases.getLanguageIsoCodeUseCase().collect { languageIsoCode ->
                _homeState.value = _homeState.value.copy(
                    languageIsoCode = languageIsoCode
                )
            }
        }
    }

    fun isNetworkAvaliable(): Boolean {
        return networkState.value.isAvaliable()
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
                    _eventFlow.emit(BaseUiEvent.NavigateTo(event.directions))
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
            _eventFlow.emit(BaseUiEvent.ShowSnackbar(uiText))
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