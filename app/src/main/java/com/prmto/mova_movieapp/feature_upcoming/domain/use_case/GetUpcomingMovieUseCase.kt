package com.prmto.mova_movieapp.feature_upcoming.domain.use_case

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Calendar
import javax.inject.Inject

class GetUpcomingMovieUseCase @Inject constructor(
    private val repository: UpComingRepository,
    private val movieGenreListUseCase: GetMovieGenreListUseCase
) {
    private val calendar = Calendar.getInstance()
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
                    voteCountByString = HandleUtils.convertingVoteCountToString(movie.voteCount)
                )
            }.filter { movie ->
                movie.releaseDate?.let { releaseDate ->
                    isAfterReleaseDate(releaseDate)
                } ?: false
            }
        }
    }


    private fun isAfterReleaseDate(releaseDate: String): Boolean {
        val releaseYear = releaseDate.split("-")[0].toInt()
        val releaseMonth = releaseDate.split("-")[1].toInt() - 1
        val releaseDay = releaseDate.split("-")[2].toInt()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        return releaseYear >= currentYear &&
                releaseMonth >= currentMonth &&
                releaseDay >= currentDay
    }

}