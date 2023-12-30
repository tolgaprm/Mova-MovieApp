package com.prmto.upcoming_data.remote.mapper

import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.remote.mapper.movie.toMovie
import com.prmto.upcoming_domain.model.UpcomingMovie

fun MovieDto.toUpComingMovie(): UpcomingMovie {
    return UpcomingMovie(
        movie = this.toMovie(),
        isAddedToRemind = false
    )
}