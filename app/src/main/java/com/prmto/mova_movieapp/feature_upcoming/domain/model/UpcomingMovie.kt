package com.prmto.mova_movieapp.feature_upcoming.domain.model

import com.prmto.core_domain.models.movie.Movie

data class UpcomingMovie(
    val movie: Movie,
    val isAddedToRemind: Boolean
)
