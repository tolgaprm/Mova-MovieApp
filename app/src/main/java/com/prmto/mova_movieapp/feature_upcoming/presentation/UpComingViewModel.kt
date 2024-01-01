package com.prmto.mova_movieapp.feature_upcoming.presentation

import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.database.entity.movie.UpcomingRemindEntity
import com.prmto.mova_movieapp.notification.alarmManager.UpComingAlarmItem
import com.prmto.mova_movieapp.notification.alarmManager.UpComingAlarmScheduler
import com.prmto.upcoming_domain.repository.UpcomingRepository
import com.prmto.upcoming_domain.use_case.GetUpcomingMovieUseCase
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
) : com.prmto.core_ui.base.viewModel.BaseViewModel() {

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

    fun getUpComingMovies() =
        getUpcomingMovieUseCase(state.value.languageIsoCode, scope = viewModelScope)
}