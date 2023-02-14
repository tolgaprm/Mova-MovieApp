package com.prmto.mova_movieapp.core.presentation.detail_bottom_sheet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.core.domain.use_case.FirebaseCoreUseCases
import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import com.prmto.mova_movieapp.feature_home.domain.models.TvSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailBottomSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DetailBottomSheetState())
    val state: StateFlow<DetailBottomSheetState> = _state.asStateFlow()


    private val _uiEvent = MutableSharedFlow<DetailBottomUiEvent>()
    val uiEvent: SharedFlow<DetailBottomUiEvent> = _uiEvent.asSharedFlow()


    init {
        savedStateHandle.get<Movie>("Movie")?.let { movie ->
            _state.value = DetailBottomSheetState(movie = movie)
        }
        savedStateHandle.get<TvSeries>("TvSeries")?.let { tvSeries ->
            _state.value = DetailBottomSheetState(tvSeries = tvSeries)
        }
    }

    fun onEvent(event: DetailBottomSheetEvent) {
        when (event) {
            is DetailBottomSheetEvent.Close -> {
                emitUiEvent(DetailBottomUiEvent.PopBackStack)
            }
            is DetailBottomSheetEvent.NavigateToDetailFragment -> {
                navigateToDetailFragment(movieId = getMovieId(), tvId = getTvSeriesId())
            }
            is DetailBottomSheetEvent.Share -> {

            }
            is DetailBottomSheetEvent.ClickedAddFavoriteList -> {
                if (isUserSignIn()) {
                    getMovieId()?.let { movieId ->
                        addMovieToFavoriteList(movieId = movieId)
                    }
                    getTvSeriesId()?.let { tvId ->
                        addTvSeriesToFavoriteList(tvId = tvId)
                    }
                } else {
                    emitUiEvent(DetailBottomUiEvent.ShowAlertDialog)
                }
            }
            is DetailBottomSheetEvent.ClickedAddWatchList -> {
                if (isUserSignIn()) {
                    getMovieId()?.let { movieId ->
                        addMovieToWatchList(movieId = movieId)
                    }
                    getTvSeriesId()?.let { tvId ->
                        addTvSeriesToWatchList(tvId = tvId)
                    }
                } else {
                    emitUiEvent(DetailBottomUiEvent.ShowAlertDialog)
                }
            }
            is DetailBottomSheetEvent.NavigateToLoginFragment -> {
                emitUiEvent(DetailBottomUiEvent.NavigateTo(DetailBottomSheetDirections.actionDetailBottomSheetToLoginFragment()))
            }
        }
    }

    private fun getMovieId(): Int? {
        return state.value.movie?.id
    }

    private fun getTvSeriesId(): Int? {
        return state.value.tvSeries?.id
    }

    private fun isUserSignIn(): Boolean {
        return firebaseCoreUseCases.isUserSignInUseCase()
    }

    private fun addMovieToFavoriteList(movieId: Int) {
        // TODO
    }

    private fun addTvSeriesToFavoriteList(tvId: Int) {
        // TODO
    }

    private fun addMovieToWatchList(movieId: Int) {
        // TODO
    }

    private fun addTvSeriesToWatchList(tvId: Int) {
        // TODO
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