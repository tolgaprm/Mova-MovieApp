package com.prmto.mova_movieapp.feature_upcoming.domain.use_case

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetUpcomingMovieUseCase @Inject constructor(
    private val repository: UpComingRepository,
    private val movieGenreListUseCase: GetMovieGenreListUseCase
) {

    operator fun invoke(language: String): Flow<PagingData<Movie>> {
        val languageLower = language.lowercase()
        return combine(
            repository.getUpComingMovies(
                language = languageLower
            ),
            movieGenreListUseCase.invoke(languageLower)
        ) { pagingData: PagingData<Movie>, genres: List<Genre> ->
            pagingData.map { movie ->
                movie.copy(
                    genresBySeparatedByComma = HandleUtils.convertGenreListToStringSeparatedByCommas(
                        movieGenreList = genres,
                        genreIds = movie.genreIds
                    ),
                    voteCountByString = HandleUtils.convertingVoteCountToString(movie.voteCount),
                    releaseDate = HandleUtils.convertToYearFromDate(movie.releaseDate ?: "")
                )
            }
        }

    }
}