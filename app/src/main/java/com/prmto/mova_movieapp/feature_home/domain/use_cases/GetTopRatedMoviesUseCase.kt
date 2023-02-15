package com.prmto.mova_movieapp.feature_home.domain.use_cases

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    operator fun invoke(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        val languageLower = language.lowercase()

        return combine(
            homeRepository.getTopRatedMovies(language = languageLower, region = region),
            getMovieGenreListUseCase(language = languageLower)
        ) { pagingData, genres ->
            pagingData.map { movie ->
                movie.copy(
                    genreByOne = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = genres,
                        genreIds = movie.genreIds
                    ),
                    voteCountByString = HandleUtils.convertingVoteCountToString(movie.voteCount),
                    releaseDate = HandleUtils.convertToYearFromDate(movie.releaseDate ?: "")
                )
            }
        }
    }
}