package com.prmto.mova_movieapp.feature_explore.domain.use_case.movie

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.core.domain.util.combineMovieAndGenreMapOneGenre
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMovieUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {

    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>> {
        return combineMovieAndGenreMapOneGenre(
            movieGenreResourceFlow = getMovieGenreListUseCase(language = language),
            moviePagingDataFlow = exploreRepository.discoverMovie(
                language = language,
                filterBottomState = filterBottomState
            )
        )
    }
}