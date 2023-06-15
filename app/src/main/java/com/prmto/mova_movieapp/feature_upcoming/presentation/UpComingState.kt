package com.prmto.mova_movieapp.feature_upcoming.presentation

import androidx.paging.PagingData
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie

data class UpComingState(
    val isLoading: Boolean = false,
    val error: String = "",
    val upComingMovieState: PagingData<UpcomingMovie> = PagingData.empty(),
    val languageIsoCode: String = "",
)
