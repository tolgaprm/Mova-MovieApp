package com.prmto.explore_domain.use_case.tv

import androidx.paging.PagingData
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.core_domain.util.combineTvAndGenreMapOneGenre
import com.prmto.explore_domain.model.FilterBottomState
import com.prmto.explore_domain.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverTvUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val tvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        return combineTvAndGenreMapOneGenre(
            tvGenreResourceFlow = tvGenreListUseCase(language = language),
            tvPagingDataFlow = exploreRepository.discoverTv(
                language = language,
                filterBottomState = filterBottomState
            )
        )
    }
}