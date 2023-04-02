package com.prmto.mova_movieapp.feature_mylist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.use_case.database.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.presentation.util.BaseUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state: StateFlow<ListState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<BaseUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getMoviesAndTvSeries()
    }


    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.SelectedTab -> {
                _state.update { it.copy(selectedTab = event.listTab) }
                getMoviesAndTvSeries()
            }
            is ListEvent.UpdateListType -> {
                _state.update { it.copy(chipType = event.chipType) }
                getMoviesAndTvSeries()
            }
            is ListEvent.ClickedMovieItem -> {
                navigateToDetailBottomSheet(movie = event.movie)
            }
            is ListEvent.ClickedTvSeriesItem -> {
                navigateToDetailBottomSheet(tvSeries = event.tvSeries)
            }
        }
    }

    private fun navigateToDetailBottomSheet(
        movie: Movie? = null,
        tvSeries: TvSeries? = null,
    ) {
        val directions = ListFragmentDirections.actionMyListFragmentToDetailBottomSheet(
            movie,
            tvSeries
        )
        viewModelScope.launch {
            _eventFlow.emit(
                BaseUiEvent.NavigateTo(directions)
            )
        }
    }

    private fun getMoviesAndTvSeries() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val currentState = state.value
            when (currentState.chipType) {
                ChipType.MOVIE -> {
                    if (currentState.selectedTab.isFavoriteList()) {
                        localDatabaseUseCases.getFavoriteMoviesUseCase()
                            .collectLatest { movies ->
                                updateListMovieAndLoading(movieList = movies)
                            }
                    } else {
                        localDatabaseUseCases.getMoviesInWatchListUseCase()
                            .collectLatest { movies ->
                                updateListMovieAndLoading(movieList = movies)
                            }
                    }
                }
                ChipType.TVSERIES -> {
                    if (currentState.selectedTab.isFavoriteList()) {
                        localDatabaseUseCases.getFavoriteTvSeriesUseCase()
                            .collectLatest { tvSeries ->
                                updateListTvSeriesAndLoading(tvSeriesList = tvSeries)
                            }
                    } else {
                        localDatabaseUseCases.getTvSeriesInWatchListUseCase()
                            .collectLatest { tvSeries ->
                                updateListTvSeriesAndLoading(tvSeriesList = tvSeries)
                            }
                    }
                }
            }
        }
    }

    private fun updateListMovieAndLoading(movieList: List<Movie>) {
        _state.update {
            it.copy(
                movieList = movieList,
                isLoading = false
            )
        }
    }

    private fun updateListTvSeriesAndLoading(tvSeriesList: List<TvSeries>) {
        _state.update {
            it.copy(
                tvSeriesList = tvSeriesList,
                isLoading = false
            )
        }
    }
}