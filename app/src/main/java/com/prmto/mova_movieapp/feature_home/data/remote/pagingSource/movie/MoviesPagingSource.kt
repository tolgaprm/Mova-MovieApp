package com.prmto.mova_movieapp.feature_home.data.remote.pagingSource.movie

import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto
import com.prmto.mova_movieapp.core.data.remote.mapper.movie.toMovie
import com.prmto.mova_movieapp.core.data.util.paging.BasePagingSource
import com.prmto.mova_movieapp.core.domain.models.Movie
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val fetchMovie: suspend (page: Int) -> List<MovieDto>
) : BasePagingSource<Movie>() {
    override suspend fun fetchData(page: Int): List<Movie> {
        return fetchMovie(page).map { it.toMovie() }
    }
}

