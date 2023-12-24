package com.prmto.mova_movieapp.feature_explore.domain.use_case.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class DiscoverMovieUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {

    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>> {

        return combine(
            exploreRepository.discoverMovie(language, filterBottomState),
            getMovieGenreListUseCase(language)
        ) { pagingData, movieGenreList ->
            pagingData.map { movie ->
                movie.copy(
                    genreByOne = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = movieGenreList,
                        genreIds = movie.genreIds
                    ),
                    releaseDate = HandleUtils.convertToYearFromDate(movie.releaseDate ?: ""),
                    voteCountByString = HandleUtils.convertingVoteCountToString(movie.voteCount)
                )
            }
        }
    }
}