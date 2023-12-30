package com.prmto.mova_movieapp.feature_explore.domain.model

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries

sealed interface MultiSearch {
    data class MovieItem(val movie: Movie) : MultiSearch
    data class TvSeriesItem(val tvSeries: TvSeries) : MultiSearch
    data class PersonItem(val person: PersonSearch) : MultiSearch
}