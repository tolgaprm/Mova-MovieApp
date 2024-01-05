package com.prmto.upcoming_ui

import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_ui.base.viewModel.BaseViewModel
import com.prmto.upcoming_domain.model.UpcomingMovie
import com.prmto.upcoming_domain.repository.UpcomingRepository
import com.prmto.upcoming_domain.use_case.GetUpcomingMovieUseCase
import com.prmto.upcoming_ui.alarmManager.UpComingAlarmItem
import com.prmto.upcoming_ui.alarmManager.UpComingAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : BaseViewModel() {

    private val mutableState = MutableStateFlow(UpComingState())
    val state: StateFlow<UpComingState> = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            val language = async {
                languageIsoCodeUseCase().first()
            }
            mutableState.update { it.copy(languageIsoCode = language.await()) }
        }
    }

    fun onEvent(event: UpComingEvent) {
        when (event) {
            UpComingEvent.Loading -> {
                mutableState.update { it.copy(isLoading = true) }
            }

            UpComingEvent.NotLoading -> {
                mutableState.update { it.copy(isLoading = false) }
            }

            is UpComingEvent.Error -> {
                mutableState.update { it.copy(error = event.message) }
            }

            is UpComingEvent.OnClickRemindMe -> {
                viewModelScope.launch {
                    toggleRemindMe(
                        isAddedToRemind = event.upcomingMovie.isAddedToRemind,
                        upcomingMovie = event.upcomingMovie
                    )
                }
            }
        }
    }

    private fun toggleRemindMe(
        isAddedToRemind: Boolean,
        upcomingMovie: UpcomingMovie
    ) {
        val upcomingAlarmItem = UpComingAlarmItem(
            movieId = upcomingMovie.movie.id,
            movieTitle = upcomingMovie.movie.title,
            movieReleaseDate = upcomingMovie.movie.fullReleaseDate ?: ""
        )

        viewModelScope.launch {
            if (isAddedToRemind) {
                upcomingRepository.deleteUpcomingRemind(upcomingMovie)
                upComingAlarmScheduler.cancelAlarm(upcomingAlarmItem)
            } else {
                upcomingRepository.insertUpcomingRemind(upcomingMovie)
                upComingAlarmScheduler.scheduleAlarm(upcomingAlarmItem)
            }
        }
    }

    fun getUpComingMovies() =
        getUpcomingMovieUseCase(state.value.languageIsoCode, scope = viewModelScope)
}