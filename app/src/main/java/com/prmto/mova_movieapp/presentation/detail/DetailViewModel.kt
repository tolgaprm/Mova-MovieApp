package com.prmto.mova_movieapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.domain.use_case.DetailUseCases
import com.prmto.mova_movieapp.presentation.detail.event.DetailEvent
import com.prmto.mova_movieapp.presentation.detail.event.DetailUiEvent
import com.prmto.mova_movieapp.presentation.util.UiText
import com.prmto.mova_movieapp.util.Constants.DEFAULT_LANGUAGE
import com.prmto.mova_movieapp.util.Constants.DETAIL_DEFAULT_ID
import com.prmto.mova_movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases,
    private val dataStoreOperations: DataStoreOperations
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    private val languageIsoCode = MutableStateFlow(DEFAULT_LANGUAGE)

    private val _eventUiFlow = MutableSharedFlow<DetailUiEvent>()
    val eventUiFlow: SharedFlow<DetailUiEvent> = _eventUiFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            languageIsoCode.value = dataStoreOperations.getLanguageIsoCode().first()
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.UpdateMovieId -> {
                _detailState.update {
                    it.copy(
                        movieId = event.movieId,
                        tvId = DETAIL_DEFAULT_ID
                    )
                }
            }
            is DetailEvent.UpdateTvSeriesId -> {
                _detailState.update {
                    it.copy(
                        movieId = DETAIL_DEFAULT_ID,
                        tvId = event.tvSeriesId
                    )
                }
            }
            is DetailEvent.IntentToImdbWebSite -> {
                emitUiEventFlow(DetailUiEvent.IntentToImdbWebSite(addLanguageQueryToTmdbUrl(event.url)))
            }
            is DetailEvent.OnBackPressed -> {
                emitUiEventFlow(DetailUiEvent.NavigateUp)
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

    fun getMovieDetail() {
        viewModelScope.launch {
            _detailState.value = _detailState.value.copy(loading = true)
            detailUseCases.movieDetailUseCase(
                language = languageIsoCode.value,
                movieId = detailState.value.movieId
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

    fun getTvDetail() {
        viewModelScope.launch {
            _detailState.update { it.copy(loading = true) }
            detailUseCases.tvDetailUseCase(
                language = languageIsoCode.value,
                tvId = detailState.value.tvId
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
                        _detailState.value = DetailState(tvDetail = resource.data)
                    }
                }
            }
        }
    }
}