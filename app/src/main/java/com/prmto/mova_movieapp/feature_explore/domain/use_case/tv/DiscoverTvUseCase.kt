package com.prmto.mova_movieapp.feature_explore.domain.use_case.tv

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.use_case.tv.GetTvGenreListUseCase
import com.prmto.mova_movieapp.core.domain.util.combineTvAndGenreMapOneGenre
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
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