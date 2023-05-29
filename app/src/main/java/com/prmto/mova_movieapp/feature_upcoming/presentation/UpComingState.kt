package com.prmto.mova_movieapp.feature_upcoming.presentation

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.Movie

data class UpComingState(
    val isLoading: Boolean = false,
    val error: String = "",
    val upComingMovieState: PagingData<Movie> = PagingData.empty(),
    val languageIsoCode: String = "",
)
