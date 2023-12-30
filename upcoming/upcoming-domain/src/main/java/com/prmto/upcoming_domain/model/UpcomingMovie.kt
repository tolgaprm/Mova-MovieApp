package com.prmto.upcoming_domain.model

import com.prmto.core_domain.models.movie.Movie

data class UpcomingMovie(
    val movie: Movie,
    val isAddedToRemind: Boolean
)