package com.prmto.mova_movieapp.feature_explore.domain.use_case

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.use_case.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform
import javax.inject.Inject

class DiscoverMovieUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {

    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>> {

        return combineTransform(
            exploreRepository.discoverMovie(language, filterBottomState),
            getMovieGenreListUseCase(language)
        ) { pagingData, movieGenreList ->
            pagingData.map {
                it.copy(
                    genresBySeparatedByComma = HandleUtils.convertGenreListToStringSeparatedByCommas(
                        movieGenreList = movieGenreList,
                        it
                    )
                )
            }
        }
    }
}