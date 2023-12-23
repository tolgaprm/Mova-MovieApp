package com.prmto.mova_movieapp.feature_home.data.movie

import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.mapper.toMovie
import com.prmto.mova_movieapp.core.data.paging.BasePagingSource
import com.prmto.mova_movieapp.core.domain.models.Movie
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val fetchMovie: suspend (page: Int) -> List<MovieDto>
) : BasePagingSource<Movie>() {
    override suspend fun fetchData(page: Int): List<Movie> {
        return fetchMovie(page).map { it.toMovie() }
    }
}

