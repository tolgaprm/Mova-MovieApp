package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.yield
import javax.inject.Inject

class GetMovieRecommendationUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    suspend operator fun invoke(
        language: String,
        movieId: Int
    ): Flow<List<Movie>> {

        val languageLower = language.lowercase()

        return combine(
            repository.getRecommendationsForMovie(
                movieId = movieId,
                language = languageLower
            ),
            getMovieGenreListUseCase(language = languageLower)
        ) { movies, genres ->
            yield()
            movies.map { movie ->
                movie.copy(
                    genresBySeparatedByComma = HandleUtils.convertGenreListToStringSeparatedByCommas(
                        movieGenreList = genres,
                        movie = movie
                    ),
                    voteCountByString = HandleUtils.convertingVoteCountToString(movie.voteCount),
                    releaseDate = HandleUtils.convertToYearFromDate(movie.releaseDate ?: "")
                )
            }
        }
    }
}