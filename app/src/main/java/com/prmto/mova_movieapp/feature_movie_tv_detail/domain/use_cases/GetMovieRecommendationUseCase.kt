package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMovieRecommendationUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    operator fun invoke(
        language: String,
        movieId: Int
    ): Flow<PagingData<Movie>> {
        val languageLower = language.lowercase()

        return combine(
            repository.getRecommendationsForMovie(movieId = movieId, language = languageLower),
            getMovieGenreListUseCase(language = languageLower)
        ) { pagingData, genres ->
            pagingData.map { movie ->
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