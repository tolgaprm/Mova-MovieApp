package com.prmto.mova_movieapp.core.presentation.detail_bottom_sheet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.use_case.database.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.domain.use_case.firebase.FirebaseCoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailBottomSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firebaseCoreUseCases: FirebaseCoreUseCases,
    private val localDatabaseUseCases: LocalDatabaseUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DetailBottomSheetState())
    val state: StateFlow<DetailBottomSheetState> = _state.asStateFlow()


    private val _uiEvent = MutableSharedFlow<DetailBottomUiEvent>()
    val uiEvent: SharedFlow<DetailBottomUiEvent> = _uiEvent.asSharedFlow()


    init {
        savedStateHandle.get<Movie>("Movie")?.let { movie ->
            _state.value = DetailBottomSheetState(movie = movie)
            updateDoesAddFavoriteState(
                idThatCheck = movie.id,
                addedFavoriteIds = localDatabaseUseCases.getFavoriteMovieIdsUseCase()
            )
            updateDoesAddWatchListState(
                idThatCheck = movie.id,
                addedMovieWatchListIds = localDatabaseUseCases.getMovieWatchListItemIdsUseCase()
            )
        }
        savedStateHandle.get<TvSeries>("TvSeries")?.let { tvSeries ->
            _state.value = DetailBottomSheetState(tvSeries = tvSeries)
            updateDoesAddFavoriteState(
                idThatCheck = tvSeries.id,
                addedFavoriteIds = localDatabaseUseCases.getFavoriteTvSeriesIdsUseCase()
            )
            updateDoesAddWatchListState(
                idThatCheck = tvSeries.id,
                addedMovieWatchListIds = localDatabaseUseCases.getTvSeriesWatchListItemIdsUseCase()
            )
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