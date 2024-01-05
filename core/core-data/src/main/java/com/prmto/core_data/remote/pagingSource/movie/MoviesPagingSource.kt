package com.prmto.core_data.remote.pagingSource.movie

import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.remote.mapper.movie.toMovie
import com.prmto.core_data.util.paging.BasePagingSource
import com.prmto.core_domain.models.movie.Movie
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val fetchMovie: suspend (page: Int) -> List<MovieDto>
) : BasePagingSource<Movie>() {
    override suspend fun fetchData(page: Int): List<Movie> {
        return fetchMovie(page).map { it.toMovie() }
    }
}

