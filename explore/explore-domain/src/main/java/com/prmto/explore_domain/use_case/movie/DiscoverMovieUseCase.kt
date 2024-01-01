package com.prmto.explore_domain.use_case.movie

import androidx.paging.PagingData
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.use_case.movie.GetMovieGenreListUseCase
import com.prmto.core_domain.util.combineMovieAndGenreMapOneGenre
import com.prmto.explore_domain.model.FilterBottomState
import com.prmto.explore_domain.repository.ExploreRepository
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