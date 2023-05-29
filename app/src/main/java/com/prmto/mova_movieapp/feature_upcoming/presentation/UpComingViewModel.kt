package com.prmto.mova_movieapp.feature_upcoming.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.feature_upcoming.domain.use_case.GetUpcomingMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpComingViewModel @Inject constructor(
    private val upComingMovieUseCase: GetUpcomingMovieUseCase,
    private val languageIsoCodeUseCase: GetLanguageIsoCodeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UpComingState())
    val state: StateFlow<UpComingState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val language = async {
                languageIsoCodeUseCase().first()
            }
            getUpComingMovies(language.await())
        }
    }

    fun onEvent(event: UpComingEvent) {
        when (event) {
            UpComingEvent.Loading -> {
                _state.update { it.copy(isLoading = true) }
            }

            UpComingEvent.NotLoading -> {
                _state.update { it.copy(isLoading = false) }
            }

            is UpComingEvent.Error -> {
                _state.update { it.copy(error = event.message) }
            }

            is UpComingEvent.ClickOnInfoIcon -> {

            }

            is UpComingEvent.NavigateToMovieDetailScreen -> {

            }
        }
    }

    private fun getUpComingMovies(languageCode: String) {
        viewModelScope.launch {
            upComingMovieUseCase(languageCode).cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _state.update {
                        it.copy(
                            upComingMovieState = pagingData
                        )
                    }
                }
        }
    }
}