package com.prmto.mova_movieapp.feature_upcoming.data.remote.mapper

import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.mapper.toMovie
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie

fun MovieDto.toUpComingMovie(): UpcomingMovie {
    return UpcomingMovie(
        movie = this.toMovie(),
        isAddedToRemind = false
    )
}