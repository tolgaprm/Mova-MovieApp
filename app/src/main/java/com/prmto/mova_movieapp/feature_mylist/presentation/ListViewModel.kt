package com.prmto.mova_movieapp.feature_mylist.presentation

import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.use_case.database.LocalDatabaseUseCases
import com.prmto.core_ui.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.core_ui.util.UiEvent
import com.prmto.my_list_ui.ChipType
import com.prmto.my_list_ui.ListEvent
import com.prmto.my_list_ui.ListState
import com.prmto.my_list_ui.isFavoriteList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
) : BaseViewModelWithUiEvent<UiEvent>() {

    private val mutableState = MutableStateFlow(ListState())
    val state = combine(
        mutableState,
        localDatabaseUseCases.getFavoriteMoviesUseCase(),
        localDatabaseUseCases.getFavoriteTvSeriesUseCase(),
        localDatabaseUseCases.getMoviesInWatchListUseCase(),
        localDatabaseUseCases.getTvSeriesInWatchListUseCase()
    ) { listState, favoriteMovies, favoriteTvSeries, moviesInWatchList, tvSeriesInWatchList ->
        when (listState.chipType) {
            ChipType.MOVIE -> {
                if (listState.selectedTab.isFavoriteList()) {
                    updateListMovieAndLoading(movieList = favoriteMovies)
                } else {
                    updateListMovieAndLoading(movieList = moviesInWatchList)
                }
            }

            ChipType.TVSERIES -> {
                if (listState.selectedTab.isFavoriteList()) {
                    updateListTvSeriesAndLoading(tvSeriesList = favoriteTvSeries)
                } else {
                    updateListTvSeriesAndLoading(tvSeriesList = tvSeriesInWatchList)
                }
            }
        }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000),
        ListState()
    )

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.SelectedTab -> {
                mutableState.update { it.copy(selectedTab = event.listTab) }
            }

            is ListEvent.UpdateListType -> {
                mutableState.update { it.copy(chipType = event.chipType) }
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
        addConsumableViewEvent(UiEvent.NavigateTo(directions))
    }

    private fun updateListMovieAndLoading(movieList: List<Movie>): ListState {
        return mutableState.updateAndGet {
            it.copy(
                movieList = movieList,
                isLoading = false
            )
        }
    }

    private fun updateListTvSeriesAndLoading(tvSeriesList: List<TvSeries>): ListState {
        return mutableState.updateAndGet {
            it.copy(
                tvSeriesList = tvSeriesList,
                isLoading = false
            )
        }
    }
}