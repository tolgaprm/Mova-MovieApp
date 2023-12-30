package com.prmto.core_domain.util

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.core_domain.models.genre.Genre
import com.prmto.core_domain.models.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun combineMovieAndGenreReturnFlow(
    movieGenreResourceFlow: Flow<List<Genre>>,
    moviePagingDataFlow: Flow<PagingData<Movie>>
): Flow<PagingData<Movie>> {
    return combine(
        moviePagingDataFlow,
        movieGenreResourceFlow,
    ) { moviePagingData, movieGenreList ->
        moviePagingData.map { movie ->
            movie.copy(
                genresBySeparatedByComma = GenreDomainUtils.getGenresBySeparatedByComma(
                    movie.genreIds,
                    movieGenreList
                )
            )
        }
    }
}

fun combineMovieAndGenreMapOneGenre(
    movieGenreResourceFlow: Flow<List<Genre>>,
    moviePagingDataFlow: Flow<PagingData<Movie>>
): Flow<PagingData<Movie>> {
    return combine(
        moviePagingDataFlow,
        movieGenreResourceFlow
    ) { moviePagingData, movieGenreList ->
        moviePagingData.map { movie ->
            movie.copy(
                genreByOne = GenreDomainUtils.handleConvertingGenreListToOneGenreString(
                    genreList = movieGenreList,
                    genreIds = movie.genreIds
                )
            )
        }
    }
}