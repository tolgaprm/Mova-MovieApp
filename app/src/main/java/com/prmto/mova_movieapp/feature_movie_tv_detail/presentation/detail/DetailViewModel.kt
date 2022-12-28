package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants.DEFAULT_LANGUAGE
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.DetailUseCases
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailUiEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.util.Constants.DETAIL_DEFAULT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases,
    private val dataStoreOperations: DataStoreOperations,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    private val languageIsoCode = MutableStateFlow(DEFAULT_LANGUAGE)

    private val _eventUiFlow = MutableSharedFlow<DetailUiEvent>()
    val eventUiFlow: SharedFlow<DetailUiEvent> = _eventUiFlow.asSharedFlow()

    init {
        getLanguageIsoCode()
    }

    fun getLanguageIsoCode() {
        viewModelScope.launch {
            languageIsoCode.value = dataStoreOperations.getLanguageIsoCode().first()
        }
        savedStateHandle.get<Int>("movieId")?.let { movieId ->
            if (movieId != DETAIL_DEFAULT_ID) {
                getMovieDetail(movieId = movieId)
            }
        }
        savedStateHandle.get<Int>("tvId")?.let { tvId ->
            if (tvId != DETAIL_DEFAULT_ID) {
                getTvDetail(tvId = tvId)
            }
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.IntentToImdbWebSite -> {
                emitUiEventFlow(DetailUiEvent.IntentToImdbWebSite(addLanguageQueryToTmdbUrl(event.url)))
            }
            is DetailEvent.OnBackPressed -> {
                emitUiEventFlow(DetailUiEvent.PopBackStack)
            }
            is DetailEvent.ClickToDirectorName -> {
                viewModelScope.launch {
                    emitUiEventFlow(
                        DetailUiEvent.NavigateTo(
                            DetailFragmentDirections.actionDetailFragmentToPersonDetailFragment(
                                event.directorId
                            )
                        )
                    )
                }
            }
            is DetailEvent.ClickActorName -> {
                emitUiEventFlow(
                    DetailUiEvent.NavigateTo(
                        DetailFragmentDirections.actionDetailFragmentToPersonDetailFragment(event.actorId)
                    )
                )
            }
        }
    }

    private fun emitUiEventFlow(detailUiEvent: DetailUiEvent) {
        viewModelScope.launch {
            _eventUiFlow.emit(detailUiEvent)
        }
    }

    private fun addLanguageQueryToTmdbUrl(tmdbUrl: String): String {
        return tmdbUrl.plus("?language=${languageIsoCode.value}")
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _detailState.value = _detailState.value.copy(loading = true)
            detailUseCases.movieDetailUseCase(
                language = languageIsoCode.value,
                movieId = movieId
            ).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _detailState.update { it.copy(loading = false) }
                        _eventUiFlow.emit(
                            DetailUiEvent.ShowSnackbar(
                                resource.uiText ?: UiText.unknownError()
                            )
                        )
                    }
                    is Resource.Success -> {
                        _detailState.value = DetailState(movieDetail = resource.data)
                    }
                }
            }
        }
    }

    private fun getTvDetail(tvId: Int) {
        viewModelScope.launch {
            _detailState.update { it.copy(loading = true) }
            detailUseCases.tvDetailUseCase(
                language = languageIsoCode.value,
                tvId = tvId
            ).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _detailState.update {
                            it.copy(loading = false)
                        }
                        _eventUiFlow.emit(
                            DetailUiEvent.ShowSnackbar(
                                resource.uiText ?: UiText.unknownError()
                            )
                        )
                    }
                    is Resource.Success -> {
                        _detailState.value = DetailState(tvDetail = resource.data)
                    }
                }
            }
        }
    }
}