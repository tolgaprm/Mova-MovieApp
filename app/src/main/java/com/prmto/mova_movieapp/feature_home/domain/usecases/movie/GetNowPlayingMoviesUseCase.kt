package com.prmto.mova_movieapp.feature_home.domain.usecases.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.core.util.Constants.DEFAULT_LANGUAGE
import com.prmto.mova_movieapp.core.util.Constants.DEFAULT_REGION
import com.prmto.mova_movieapp.feature_home.domain.repository.movie.HomeMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val homeMovieRepository: HomeMovieRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {
    operator fun invoke(
        language: String = DEFAULT_LANGUAGE,
        region: String = DEFAULT_REGION
    ): Flow<PagingData<Movie>> {

        val languageLower = language.lowercase()

        return combine(
            homeMovieRepository.getNowPlayingMovies(language = languageLower, region = region),
            getMovieGenreListUseCase(language = languageLower)
        ) { pagingData, genres ->
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