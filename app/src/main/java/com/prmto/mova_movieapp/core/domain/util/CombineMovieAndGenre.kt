package com.prmto.mova_movieapp.core.domain.util

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.data.util.HandleUtils
import com.prmto.mova_movieapp.core.domain.models.genre.Genre
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
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
                genresBySeparatedByComma = HandleUtils.getGenresBySeparatedByComma(
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
                genreByOne = HandleUtils.handleConvertingGenreListToOneGenreString(
                    genreList = movieGenreList,
                    genreIds = movie.genreIds
                )
            )
        }
    }
}