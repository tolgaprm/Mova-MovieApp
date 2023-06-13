package com.prmto.mova_movieapp.feature_upcoming.domain.model

import com.prmto.mova_movieapp.core.domain.models.Movie

data class UpcomingMovie(
    val movie: Movie,
    val isAddedToRemind: Boolean
)
