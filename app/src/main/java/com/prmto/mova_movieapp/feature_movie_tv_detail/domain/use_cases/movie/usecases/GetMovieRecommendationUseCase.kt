package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.movie.usecases

import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.util.GenreDomainUtils
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.movie.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMovieRecommendationUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    operator fun invoke(
        language: String,
        movieId: Int
    ): Flow<List<Movie>> {
        val languageLower = language.lowercase()

        return combine(
            movieDetailRepository.getRecommendationsForMovie(
                movieId = movieId,
                language = languageLower
            ),
            getMovieGenreListUseCase(language = languageLower)
        ) { movies, genres ->
            movies.map { movie ->
                movie.copy(
                    genreByOne = GenreDomainUtils.handleConvertingGenreListToOneGenreString(
                        genreList = genres,
                        genreIds = movie.genreIds
                    )
                )
            }
        }
    }
}