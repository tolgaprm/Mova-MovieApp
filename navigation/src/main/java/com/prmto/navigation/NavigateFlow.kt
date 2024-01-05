package com.prmto.navigation

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries

sealed interface NavigateFlow {
    object HomeFlow : NavigateFlow
    object AuthFlow : NavigateFlow

    data class BottomSheetDetailFlow(val movie: Movie?, val tvSeries: TvSeries?) : NavigateFlow

    data class DetailFlow(val movieId: Int = 0, val tvSeriesId: Int = 0) :
        NavigateFlow

    data class PersonDetailFlow(val personId: Int) : NavigateFlow
}