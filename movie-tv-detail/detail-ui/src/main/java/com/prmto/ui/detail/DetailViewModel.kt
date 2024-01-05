package com.prmto.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.repository.DataStoreOperations
import com.prmto.core_domain.use_case.database.LocalDatabaseUseCases
import com.prmto.core_domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.core_domain.util.Constants.DEFAULT_LANGUAGE
import com.prmto.core_ui.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.domain.models.detail.movie.toMovie
import com.prmto.domain.models.detail.tv.toTvSeries
import com.prmto.domain.models.detail.video.Videos
import com.prmto.domain.use_cases.DetailUseCases
import com.prmto.domain.util.Constants.DETAIL_DEFAULT_ID
import com.prmto.navigation.NavigateFlow
import com.prmto.ui.detail.event.DetailEvent
import com.prmto.ui.detail.event.DetailUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases,
    private val dataStoreOperations: DataStoreOperations,
    private val firebaseCoreUseCases: FirebaseCoreUseCases,
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModelWithUiEvent<DetailUiEvent>() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    private val _videos = MutableStateFlow<Videos?>(null)
    val videos: StateFlow<Videos?> = _videos.asStateFlow()

    private var languageIsoCode = DEFAULT_LANGUAGE

    init {
        getLanguageIsoCode()
    }

    private fun getLanguageIsoCode() {
        viewModelScope.launch {
            languageIsoCode = dataStoreOperations.getLanguageIsoCode().first()
        }
        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
            .movieId.let { movieId ->
                if (movieId == DETAIL_DEFAULT_ID) return@let
                _detailState.update {
                    it.copy(
                        movieId = movieId,
                        tvId = null
                    )
                }
                getMovieDetail(movieId = movieId)
            }

        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
            .tvId.let { tvId ->
                if (tvId == DETAIL_DEFAULT_ID) return@let
                _detailState.update {
                    it.copy(
                        movieId = null,
                        tvId = tvId
                    )
                }
                getTvDetail(tvId = tvId)
            }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.IntentToImdbWebSite -> {
                addConsumableViewEvent(
                    DetailUiEvent.IntentToImdbWebSite(
                        addLanguageQueryToTmdbUrl(
                            event.url
                        )
                    )
                )
            }

            is DetailEvent.ClickToDirectorName -> {
                val action = NavigateFlow.PersonDetailFlow(event.directorId)

                addConsumableViewEvent(DetailUiEvent.NavigateTo(action))
            }

            is DetailEvent.ClickActorName -> {
                val action = NavigateFlow.PersonDetailFlow(event.actorId)
                addConsumableViewEvent(DetailUiEvent.NavigateTo(action))
            }

            is DetailEvent.ClickedAddWatchList -> {
                val doesAddWatchList = detailState.value.doesAddWatchList
                _detailState.update {
                    it.copy(
                        doesAddWatchList = !doesAddWatchList
                    )
                }
                addWatchListIfUserSignIn(
                    onAddTvSeries = {
                        localDatabaseUseCases.toggleTvSeriesForWatchListItemUseCase(
                            tvSeries = detailState.value.tvDetail?.toTvSeries()
                                ?: return@addWatchListIfUserSignIn,
                            doesAddWatchList = doesAddWatchList
                        )
                    },
                    onAddMovie = {
                        localDatabaseUseCases.toggleMovieForWatchListUseCase(
                            movie = detailState.value.movieDetail?.toMovie()
                                ?: return@addWatchListIfUserSignIn,
                            doesAddWatchList = doesAddWatchList
                        )
                    }
                )
            }

            is DetailEvent.ClickedAddFavoriteList -> {
                val doesAddFavoriteList = detailState.value.doesAddFavorite
                _detailState.update {
                    it.copy(
                        doesAddFavorite = !doesAddFavoriteList
                    )
                }
                addFavoriteListIfUserSignIn(
                    onAddTvSeries = {
                        localDatabaseUseCases.toggleTvSeriesForFavoriteListUseCase(
                            tvSeries = detailState.value.tvDetail?.toTvSeries()
                                ?: return@addFavoriteListIfUserSignIn,
                            doesAddFavoriteList = doesAddFavoriteList
                        )
                    },
                    onAddMovie = {
                        localDatabaseUseCases.toggleMovieForFavoriteListUseCase(
                            movie = detailState.value.movieDetail?.toMovie()
                                ?: return@addFavoriteListIfUserSignIn,
                            doesAddFavoriteList = doesAddFavoriteList
                        )
                    }
                )
            }

            is DetailEvent.SelectedTab -> {
                _detailState.update { it.copy(selectedTab = event.selectedTab) }
                if (detailState.value.isSelectedTrailerTab()) {
                    if (detailState.value.isNotNullTvDetail()) {
                        getTvVideos(tvId = detailState.value.tvId!!)
                    } else {
                        getMovieVideos(movieId = detailState.value.movieId!!)
                    }
                }
            }

            is DetailEvent.ClickRecommendationItemClick -> {
                event.tvSeries?.let {
                    val action = NavigateFlow.BottomSheetDetailFlow(
                        tvSeries = it,
                        movie = null
                    )
                    addConsumableViewEvent(DetailUiEvent.NavigateTo(action))
                }
                event.movie?.let {
                    val action = NavigateFlow.BottomSheetDetailFlow(
                        tvSeries = null,
                        movie = it
                    )
                    addConsumableViewEvent(DetailUiEvent.NavigateTo(action))
                }
            }
        }
    }

    private fun addFavoriteListIfUserSignIn(
        onAddTvSeries: suspend () -> Unit,
        onAddMovie: suspend () -> Unit
    ) {
        viewModelScope.launch {
            if (firebaseCoreUseCases.isUserSignInUseCase()) {
                if (detailState.value.isNotNullTvDetail()) {
                    onAddTvSeries()
                } else {
                    onAddMovie()
                }
            } else {
                addConsumableViewEvent(DetailUiEvent.ShowAlertDialog)
            }
        }
    }

    private fun addWatchListIfUserSignIn(
        onAddTvSeries: suspend () -> Unit,
        onAddMovie: suspend () -> Unit
    ) {
        viewModelScope.launch {
            if (firebaseCoreUseCases.isUserSignInUseCase()) {
                if (detailState.value.isNotNullTvDetail()) {
                    onAddTvSeries()
                } else {
                    onAddMovie()
                }
            } else {
                addConsumableViewEvent(DetailUiEvent.ShowAlertDialog)
            }
        }
    }

    private fun addLanguageQueryToTmdbUrl(tmdbUrl: String): String {
        return tmdbUrl.plus("?language=${languageIsoCode}")
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _detailState.value = _detailState.value.copy(isLoading = true)

            handleResourceWithCallbacks(
                resourceSupplier = {
                    detailUseCases.movieDetailUseCase(
                        language = languageIsoCode,
                        movieId = movieId
                    )
                },
                onErrorCallback = { uiText ->
                    _detailState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(DetailUiEvent.showSnackbarErrorMessage(uiText))
                },
                onSuccessCallback = { movieDetail ->
                    _detailState.update {
                        it.copy(
                            movieDetail = movieDetail,
                            tvDetail = null,
                            tvId = null,
                            isLoading = false,
                            doesAddFavorite = movieDetail.isFavorite,
                            doesAddWatchList = movieDetail.isWatchList
                        )
                    }
                    getMovieRecommendations(movieId = movieId)
                }
            )
        }
    }

    private fun getTvDetail(tvId: Int) {
        viewModelScope.launch {
            _detailState.update { it.copy(isLoading = true) }

            handleResourceWithCallbacks(
                resourceSupplier = {
                    detailUseCases.tvDetailUseCase(
                        language = languageIsoCode, tvId = tvId
                    )
                },
                onErrorCallback = { uiText ->
                    _detailState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(DetailUiEvent.showSnackbarErrorMessage(uiText))
                },
                onSuccessCallback = { tvDetail ->
                    _detailState.value = _detailState.value.copy(
                        tvDetail = tvDetail,
                        movieDetail = null,
                        movieId = null,
                        isLoading = false,
                        doesAddFavorite = tvDetail.isFavorite,
                        doesAddWatchList = tvDetail.isWatchList
                    )
                    getTvRecommendations(tvId = tvId)
                }
            )
        }
    }

    private fun getMovieRecommendations(movieId: Int) {
        viewModelScope.launch {
            detailUseCases.getMovieRecommendationUseCase(
                movieId = movieId, language = languageIsoCode
            ).collect { movies ->
                _detailState.update {
                    it.copy(
                        movieRecommendation = movies
                    )
                }
            }
        }
    }

    private fun getTvRecommendations(tvId: Int) {
        viewModelScope.launch {
            detailUseCases.getTvRecommendationUseCase(
                tvId = tvId, language = languageIsoCode
            ).collect { tvSeries ->
                _detailState.update {
                    it.copy(
                        tvRecommendation = tvSeries
                    )
                }
            }
        }
    }

    private fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            updateVideosLoading(isLoading = true)
            handleResourceWithCallbacks(
                resourceSupplier = {
                    detailUseCases.getMovieVideosUseCase(
                        movieId = movieId, language = languageIsoCode
                    )
                },
                onErrorCallback = { uiText ->
                    updateVideosLoading(isLoading = false)
                    addConsumableViewEvent(DetailUiEvent.showSnackbarErrorMessage(uiText))
                },
                onSuccessCallback = { videos ->
                    updateVideosLoading(isLoading = false)
                    _videos.value = videos
                }
            )
        }
    }

    private fun getTvVideos(tvId: Int) {
        viewModelScope.launch {
            updateVideosLoading(isLoading = true)
            handleResourceWithCallbacks(
                resourceSupplier = {
                    detailUseCases.getTvVideosUseCase(
                        tvId = tvId, language = languageIsoCode
                    )
                },
                onErrorCallback = { uiText ->
                    updateVideosLoading(isLoading = false)
                    addConsumableViewEvent(DetailUiEvent.showSnackbarErrorMessage(uiText))
                },
                onSuccessCallback = { videos ->
                    updateVideosLoading(isLoading = false)
                    _videos.value = videos
                }
            )
        }
    }

    private fun updateVideosLoading(isLoading: Boolean) {
        _detailState.update { it.copy(videosLoading = isLoading) }
    }
}