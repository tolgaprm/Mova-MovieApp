package com.prmto.mova_movieapp.feature_upcoming.domain.use_case

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.prmto.mova_movieapp.core.data.remote.dto.genre.Genre
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.util.DateFormatUtils
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpcomingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Calendar
import javax.inject.Inject

class GetUpcomingMovieUseCase @Inject constructor(
    private val repository: UpcomingRepository,
    private val movieGenreListUseCase: GetMovieGenreListUseCase,
) {
    private val calendar = Calendar.getInstance()
    operator fun invoke(language: String, scope: CoroutineScope): Flow<PagingData<UpcomingMovie>> {
        val languageLower = language.lowercase()
        return combine(
            repository.getUpComingMovies(
                language = languageLower
            ).cachedIn(scope),
            movieGenreListUseCase(languageLower),
            repository.getAllUpcomingRemind()
        ) { pagingData: PagingData<UpcomingMovie>, genres: List<Genre>, upcomingRemind ->
            pagingData.map { upComingMovie ->
                upComingMovie.copy(
                    movie = upComingMovie.movie.copy(
                        genresBySeparatedByComma = HandleUtils.convertGenreListToStringSeparatedByCommas(
                            movieGenreList = genres,
                            genreIds = upComingMovie.movie.genreIds
                        ),
                        voteCountByString = HandleUtils.convertingVoteCountToString(upComingMovie.movie.voteCount)
                    ),
                    isAddedToRemind = upcomingRemind.any { it.movieId == upComingMovie.movie.id }
                )
            }.filter { upcomingMovie ->
                upcomingMovie.movie.releaseDate?.let { releaseDate ->
                    isAfterReleaseDate(releaseDate)
                } ?: false
            }
        }
    }


    private fun isAfterReleaseDate(releaseDate: String): Boolean {
        val movaDate = DateFormatUtils.convertToDateFromReleaseDate(releaseDate)

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        return movaDate.year >= currentYear &&
                movaDate.month.value >= currentMonth &&
                movaDate.dayOfMonth >= currentDay
    }

}