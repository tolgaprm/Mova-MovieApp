package com.prmto.my_list_ui

import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.use_case.database.LocalDatabaseUseCases
import com.prmto.core_ui.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.core_ui.util.UiEvent
import com.prmto.navigation.NavigateFlow
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
        val directions = NavigateFlow.BottomSheetDetailFlow(
            movie = movie,
            tvSeries = tvSeries
        )
        addConsumableViewEvent(UiEvent.NavigateToFlow(directions))
    }

    private fun updateListMovieAndLoading(movieList: List<Movie>): ListState {
        return mutableState.updateAndGet {
            it.copy(
                movieList = movieList,
                isLoading = false,
                hasAnyMovieInList = movieList.isNotEmpty(),
                errorMessage = R.string.not_movies_in_your_list
            )
        }
    }

    private fun updateListTvSeriesAndLoading(tvSeriesList: List<TvSeries>): ListState {
        return mutableState.updateAndGet {
            it.copy(
                tvSeriesList = tvSeriesList,
                isLoading = false,
                hasAnyMovieInList = tvSeriesList.isNotEmpty(),
                errorMessage = R.string.not_tv_in_your_list
            )
        }
    }
}