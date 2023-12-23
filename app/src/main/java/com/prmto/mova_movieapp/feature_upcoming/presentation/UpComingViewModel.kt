package com.prmto.mova_movieapp.feature_upcoming.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.feature_upcoming.alarm_manager.UpComingAlarmItem
import com.prmto.mova_movieapp.feature_upcoming.domain.alarmManager.UpComingAlarmScheduler
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpcomingRepository
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
    private val getUpcomingMovieUseCase: GetUpcomingMovieUseCase,
    private val upcomingRepository: UpcomingRepository,
    private val languageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    private val upComingAlarmScheduler: UpComingAlarmScheduler
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

            is UpComingEvent.OnClickRemindMe -> {
                viewModelScope.launch {
                    toggleRemindMe(
                        isAddedToRemind = event.isAddedToRemind,
                        upcomingRemind = event.upcomingRemindEntity
                    )
                }
            }
        }
    }

    private fun toggleRemindMe(
        isAddedToRemind: Boolean,
        upcomingRemind: UpcomingRemindEntity
    ) {
        val upcomingRemindEntity = UpComingAlarmItem(
            movieId = upcomingRemind.movieId,
            movieTitle = upcomingRemind.movieTitle,
            movieReleaseDate = upcomingRemind.movieReleaseDate
        )

        viewModelScope.launch {
            if (isAddedToRemind) {
                upcomingRepository.deleteUpcomingRemind(upcomingRemind)
                upComingAlarmScheduler.cancelAlarm(upcomingRemindEntity)
            } else {
                upcomingRepository.insertUpcomingRemind(upcomingRemind)
                upComingAlarmScheduler.scheduleAlarm(upcomingRemindEntity)
            }
        }
    }

    private fun getUpComingMovies(languageCode: String) {
        viewModelScope.launch {
            getUpcomingMovieUseCase(languageCode, scope = viewModelScope)
                .cachedIn(viewModelScope)
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