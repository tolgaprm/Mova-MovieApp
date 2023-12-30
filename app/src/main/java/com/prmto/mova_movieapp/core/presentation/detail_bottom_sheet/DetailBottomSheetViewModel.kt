package com.prmto.mova_movieapp.core.presentation.detail_bottom_sheet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.use_case.database.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.domain.use_case.firebase.FirebaseCoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailBottomSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firebaseCoreUseCases: FirebaseCoreUseCases,
    private val localDatabaseUseCases: LocalDatabaseUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DetailBottomSheetState())
    val state: StateFlow<DetailBottomSheetState> = combine(
        localDatabaseUseCases.getFavoriteMovieIdsUseCase(),
        localDatabaseUseCases.getMovieWatchListItemIdsUseCase(),
        localDatabaseUseCases.getFavoriteTvSeriesIdsUseCase(),
        localDatabaseUseCases.getTvSeriesWatchListItemIdsUseCase()
    ) { favoriteMovieIds, movieWatchListIds, favoriteTvIds, tvWatchListIds ->
        _state.updateAndGet {
            it.copy(
                doesAddFavorite = if (it.movie != null) {
                    favoriteMovieIds.contains(it.movie.id)
                } else {
                    favoriteTvIds.contains(it.tvSeries!!.id)
                },
                doesAddWatchList = if (it.movie != null) {
                    movieWatchListIds.contains(it.movie.id)
                } else {
                    tvWatchListIds.contains(it.tvSeries!!.id)
                },
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DetailBottomSheetState()
    )

    private val _uiEvent = MutableSharedFlow<DetailBottomUiEvent>()
    val uiEvent: SharedFlow<DetailBottomUiEvent> = _uiEvent.asSharedFlow()

    init {
        DetailBottomSheetArgs.fromSavedStateHandle(savedStateHandle).movie?.let { movie ->
            _state.value = DetailBottomSheetState(movie = movie)
        }

        DetailBottomSheetArgs.fromSavedStateHandle(savedStateHandle).tvSeries?.let { tvSeries ->
            _state.value = DetailBottomSheetState(tvSeries = tvSeries)
        }
    }

    private fun updateDoesAddFavoriteState(
        idThatCheck: Int,
        addedFavoriteIds: Flow<List<Int>>
    ) {
        viewModelScope.launch {
            addedFavoriteIds.collectLatest { favoriteMovieIds ->
                _state.update { it.copy(doesAddFavorite = favoriteMovieIds.any { id -> id == idThatCheck }) }
            }
        }
    }

    private fun updateDoesAddWatchListState(
        idThatCheck: Int,
        addedMovieWatchListIds: Flow<List<Int>>
    ) {
        viewModelScope.launch {
            addedMovieWatchListIds.collectLatest { movieWatchListItemIds ->
                _state.update { it.copy(doesAddWatchList = movieWatchListItemIds.any { id -> id == idThatCheck }) }
            }
        }
    }

    fun onEvent(event: DetailBottomSheetEvent) {
        when (event) {
            is DetailBottomSheetEvent.Close -> {
                emitUiEvent(DetailBottomUiEvent.PopBackStack)
            }

            is DetailBottomSheetEvent.NavigateToDetailFragment -> {
                navigateToDetailFragment(movieId = getMovie()?.id, tvId = getTvSeries()?.id)
            }

            is DetailBottomSheetEvent.Share -> {

            }

            is DetailBottomSheetEvent.ClickedAddFavoriteList -> {
                if (isUserSignIn()) {
                    getMovie()?.let { movie ->
                        toggleMovieForFavoriteList(movie = movie)
                    }
                    getTvSeries()?.let { tvSeries ->
                        toggleTvSeriesForFavoriteList(tvSeries = tvSeries)
                    }
                } else {
                    emitUiEvent(DetailBottomUiEvent.ShowAlertDialog)
                }
            }

            is DetailBottomSheetEvent.ClickedAddWatchList -> {
                if (isUserSignIn()) {
                    getMovie()?.let { movie ->
                        toggleMovieForWatchList(movie = movie)
                    }
                    getTvSeries()?.let { tvSeries ->
                        toggleTvSeriesForWatchList(tvSeries = tvSeries)
                    }
                } else {
                    emitUiEvent(DetailBottomUiEvent.ShowAlertDialog)
                }
            }
        }
    }

    private fun getMovie(): Movie? {
        return state.value.movie
    }

    private fun getTvSeries(): TvSeries? {
        return state.value.tvSeries
    }

    private fun isUserSignIn(): Boolean {
        return firebaseCoreUseCases.isUserSignInUseCase()
    }

    private fun toggleMovieForFavoriteList(movie: Movie) {
        viewModelScope.launch {
            localDatabaseUseCases.toggleMovieForFavoriteListUseCase(
                movie = movie, doesAddFavoriteList = state.value.doesAddFavorite
            )
        }
    }

    private fun toggleMovieForWatchList(movie: Movie) {
        viewModelScope.launch {
            localDatabaseUseCases.toggleMovieForWatchListUseCase(
                movie = movie, doesAddWatchList = state.value.doesAddWatchList
            )
        }
    }

    private fun toggleTvSeriesForFavoriteList(tvSeries: TvSeries) {
        viewModelScope.launch {
            localDatabaseUseCases.toggleTvSeriesForFavoriteListUseCase(
                tvSeries = tvSeries,
                doesAddFavoriteList = state.value.doesAddFavorite
            )
        }
    }

    private fun toggleTvSeriesForWatchList(tvSeries: TvSeries) {
        viewModelScope.launch {
            localDatabaseUseCases.toggleTvSeriesForWatchListItemUseCase(
                tvSeries = tvSeries,
                doesAddWatchList = state.value.doesAddWatchList
            )
        }
    }

    private fun navigateToDetailFragment(movieId: Int? = null, tvId: Int? = null) {
        val action = DetailBottomSheetDirections.actionDetailBottomSheetToDetailFragment()
        movieId?.let {
            action.movieId = movieId
            action.tvId = 0
        }
        tvId?.let {
            action.tvId = tvId
            action.movieId = 0
        }
        emitUiEvent(DetailBottomUiEvent.NavigateTo(action))
    }

    private fun emitUiEvent(uiEvent: DetailBottomUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(uiEvent)
        }
    }
}